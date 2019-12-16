package com.deepali.submission.service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.deepali.submission.model.User;

@Service
public class UserService {
	
    private static final double longitute_London = -0.09184;
	private static final double latitude_London = 51.51279;
	private static String rest_url_users = "https://bpdts-test-app.herokuapp.com/users";
	private static String rest_url_users_location = "https://bpdts-test-app.herokuapp.com/city/London/users";
	
	public List<User> getUsers(){
		
		RestTemplate restTemplate = new RestTemplate();
		
		ResponseEntity<List<User>> response = restTemplate.exchange(
				  rest_url_users,
			      HttpMethod.GET,
			      null,
			      new ParameterizedTypeReference<List<User>>() {});
	    List<User> userList = response.getBody();
	  
	    
	    userList = userList.stream().filter(user -> checkDistance(user.getLatitude(), user.getLongitute()) <= 50)
	    			.collect(Collectors.toList());
	    
	    
	    ResponseEntity<List<User>> response_location = restTemplate.exchange(
	    		      rest_url_users_location,
			      HttpMethod.GET,
			      null,
			      new ParameterizedTypeReference<List<User>>() {});
	    
	    return Stream.of(userList, response_location.getBody())
                .flatMap(list -> list.stream())
                .collect(Collectors.toList());
		
	}
	
	//This function converts decimal degrees to radiant 
	private double degTorad(double deg) {
	  return (deg * Math.PI / 180.0);
	}
	
	//This function converts radiant to decimal degrees 
	private double radTodeg(double rad) {
	  return (rad * 180.0 / Math.PI);
	}
	
	private double checkDistance(double latitute, double logitude) {
		double theta = longitute_London - logitude;
		double dist = Math.sin(degTorad(latitude_London)) * Math.sin(degTorad(latitute)) 
				+ Math.cos(degTorad(latitude_London)) * Math.cos(degTorad(latitute)) * Math.cos(degTorad(theta));
		dist = Math.acos(dist);
		dist = radTodeg(dist);
		dist = dist * 60 * 1.1515;
		return dist;
	}
}
	
