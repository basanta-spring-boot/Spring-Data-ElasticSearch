package com.basant.spring.data.elasticsearch.api.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.basant.spring.data.elasticsearch.api.model.User;
import com.basant.spring.data.elasticsearch.api.service.UserService;

@RestController
@RequestMapping("/elastic-search")
public class UserController {
	@Autowired
	private UserService service;

	@GetMapping("/getAll")
	public List<User> getAll() {
		List<User> users = new ArrayList<>();
		service.getAll().forEach(user -> users.add(user));
		return users;
	}

	@GetMapping("/findByName/{name}")
	public List<User> findUserByName(@PathVariable String name) {
		return service.findByName(name);
	}

	@GetMapping("/findByProfession")
	public List<User> findUserByProfession(@RequestParam("profession") String profession) {
		return service.findByProfession(profession);
	}

	@GetMapping("/filter/{input}")
	public List<User> filterRecord(@PathVariable String input) {
		return service.getAllWihFilter(input);
	}

	@GetMapping("/pagination/{salary}")
	public List<User> getUserBySalarywithPagination(@PathVariable long salary) {
		return service.getPaginationData(salary);
	}

	@GetMapping("/customSearch/{address}")
	public List<User> getUserBySalaryWithSearchQuery(@PathVariable String address) {
		return service.getCustomSearchdata(address);
	}
}
