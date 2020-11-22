package com.microservices.servicediscovery;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.shared.Application;
import com.netflix.discovery.shared.Applications;
import com.netflix.eureka.EurekaServerContextHolder;
import com.netflix.eureka.registry.PeerAwareInstanceRegistry;

@CrossOrigin(origins="*")
@RestController
class ServiceInstanceRestController {
	public List<Application> getApplications() {
		PeerAwareInstanceRegistry registry = EurekaServerContextHolder.getInstance().getServerContext().getRegistry();
	    Applications applications = registry.getApplications();
	    
	    return applications.getRegisteredApplications();
	}

    @RequestMapping(value="/status", method=RequestMethod.GET, produces="application/json")
	public ResponseEntity<String> getAll() {
    	List<Application> applications = this.getApplications();
		
		String json = "[";
	    for (Application app : applications) {
	    	for (InstanceInfo info : app.getInstances()) {
	            String name = info.getAppName();
	            String url = info.getHomePageUrl();
	            String status = info.getStatus().toString();
	            json += "{\"name\":\""+name+"\", \"url\":\""+url+"\", \"status\":\"" + status + "\"},";
	        }
	    }
	    
	    if (json.length() > 1) json = json.substring(0, json.length() - 1);
	    json += "]";
	    return new ResponseEntity<String>(json, HttpStatus.OK);
	}
}