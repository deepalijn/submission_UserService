package com.deepali.submission.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.deepali.submission.model.User;
import com.deepali.submission.service.UserService;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

	@MockBean
	private UserService service;
	
	@Autowired
	private MockMvc mockMvc;
	 
	@Test
	public void getUsersEndPoint() throws Exception {
		
		List<User> expectedResult = Stream.of(new User(82,"Al","Castellino","acastellino29@rambler.ru","24.198.223.201",50.9603536,0.0), 
				new User(82,"Marty","Aldiss","maldiss2y@delicious.com","36.21.197.237",51.6891738,0.0)).collect(Collectors.toList());
		when(service.getUsers()).
			thenReturn(expectedResult);
		mockMvc.perform(get("/users")).andExpect(status().isOk()).andReturn();
	}
	
}
