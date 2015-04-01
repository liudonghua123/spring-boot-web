package com.liudonghua.tutorials.spring_boot_web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.liudonghua.tutorials.spring_boot_web.models.User;
import com.liudonghua.tutorials.spring_boot_web.repositories.UserRepository;

@Controller
@RequestMapping("/")
public class UserController {

	@Autowired
	private UserRepository userRepository;

	@RequestMapping
	@ResponseBody
	public Iterable<User> getAll() {
		return userRepository.findAll();
	}

	@RequestMapping(value = "/{id}")
	@ResponseBody
	public User get(@PathVariable long id) {
		return userRepository.findOne(id);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public User add(@RequestBody User user) {
		return userRepository.save(user);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public String delete(@PathVariable long id) {
		try {
			User user = new User(id);
			userRepository.delete(user);
		} catch (Exception ex) {
			return "Error deleting the user:" + ex.toString();
		}
		return "User succesfully deleted!";
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PATCH)
	@ResponseBody
	public User update(@PathVariable long id, @RequestParam String email,
			@RequestParam String name) {
		User user = userRepository.findOne(id);
		user.setEmail(email);
		user.setName(name);
		return userRepository.save(user);
	}

	@RequestMapping("/get-by-email")
	@ResponseBody
	public String getByEmail(@RequestParam String email) {
		String userId;
		try {
			User user = userRepository.findByEmail(email);
			userId = String.valueOf(user.getId());
		} catch (Exception ex) {
			return "User not found";
		}
		return "The user id is: " + userId;
	}
}