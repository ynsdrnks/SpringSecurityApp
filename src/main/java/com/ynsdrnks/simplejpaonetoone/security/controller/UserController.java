package com.ynsdrnks.simplejpaonetoone.security.controller;


import com.ynsdrnks.simplejpaonetoone.security.domain.User;
import com.ynsdrnks.simplejpaonetoone.security.repository.UserRepository;
import com.ynsdrnks.simplejpaonetoone.util.ApiPaths;
import javassist.NotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.validation.Valid;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(ApiPaths.UserBasicCtrl.CTRL)
public class UserController {


	private UserRepository userRepository;
	
	private BCryptPasswordEncoder passwordEncoder;
	
	public UserController(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
		super();
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@RequestMapping(value = "/show-user/{email}/", method = RequestMethod.GET)
	public String UserShowPanel(@PathVariable String email, Map<String, Object> map) throws SQLException {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		map.put("adminname", auth.getName());
		map.put("title", "User Details");
		List<User> users=userRepository.getUserByEmail(email);
		if(users.size()>0) {
			if(auth.getName().equals(users.get(0).getEmail())) {
				map.put("user", users.get(0));
			}else {
				map.put("message", email+" invalid email.");
			}
		}else {
			map.put("message", email+" invalid email..");
		}
		return "user/user-details";
	}
	
	@RequestMapping(value = "/update-user/{email}/", method = RequestMethod.GET)
	public String UserUpdatePanel(@PathVariable String email, Map<String, Object> map) throws SQLException {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		map.put("adminname", auth.getName());
		map.put("title", "User Kayıt Güncelleme Sayfası");
		
		List<User> users=userRepository.getUserByEmail(email);
		if(users.size()>0) {
			if(true) {
				map.put("user", users.get(0));
				return "user/user-update-panel";
			}else {
				map.put("message", email+" email adresi size ait değildir.");
			}
		}else {
			
			map.put("message", email+" email adresine ait kayıt bulunamamıştır..");
		}
		return "user/user-details";
	}
	
	@RequestMapping(value = "/update-user/", method = RequestMethod.POST)
	public String UserUpdate(@Valid @ModelAttribute("user") User user, BindingResult result,
                             Map<String, Object> map)  throws SQLException {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		map.put("adminname", auth.getName());
		map.put("title", "User Update Page");
		
		List<User> users=userRepository.getUserByEmail(auth.getName());
		if(auth.getName().equals(users.get(0).getEmail())) {

			if (result.hasErrors()) {
				map.put("message", "Try again..");
				return "user/user-details";
			}
			user.setPassword(users.get(0).getPassword());
			map.put("message", "Successfully registered.");
			userRepository.save(user);
			return "redirect:/logout";
		}else {
			map.put("message", users.get(0).getEmail()+" invalid email.");
		}

		return "user/user-details";

	}
	
	
	@RequestMapping(value = "/update-user-password/{email}/", method = RequestMethod.GET)
	public String UserPasswordUpdatePanel(@PathVariable String email, Map<String, Object> map) throws SQLException {

		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		map.put("adminname", auth.getName());
		map.put("title", "User Detayları");
		List<User> users=userRepository.getUserByEmail(email);
		if(users.size()>0) {
			if(auth.getName().equals(users.get(0).getEmail())) {
				map.put("message", email+" Welcome.");
				map.put("user", users.get(0));
				return "user/user-password-update-panel";
			}else {
				map.put("message", email+" invalid email.");
			}
		}else {
			map.put("message", email+" invalid email..");
		}
		return "user/user-details";
	}
	
	@RequestMapping(value = "/update-user-password", method = RequestMethod.POST)
	public String UserPasswordUpdate(Map<String, Object> map, WebRequest request) throws SQLException {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		List<User> users=userRepository.getUserByEmail(auth.getName());
		map.put("user", users.get(0));
		map.put("adminname", auth.getName());
		map.put("title", "User Details");
		if(request.getParameter("oldpassword1").equals(request.getParameter("oldpassword2"))){
			boolean control=passwordEncoder.matches( request.getParameter("oldpassword1"),users.get(0).getPassword());
			if(control) {
				//changing password
				users.get(0).setPassword(passwordEncoder.encode(request.getParameter("password")));
				userRepository.save(users.get(0));
				map.put("message", " Successful..");
				return "redirect:/logout";
			}else {
				map.put("message", "Wrong password.");
			}
		}else {
			map.put("message", "Check password.");
		}
		return "user/user-details";
	}

	@RequestMapping(value = "delete-user/{userId}",method = RequestMethod.GET)
	public String deleteUser(@PathVariable("userId") int userId) throws NotFoundException {

		User user = userRepository.getOne(userId);
		if (user==null) throw new NotFoundException(user.getEmail());
		userRepository.delete(user);
		return "redirect:/user/users";
	}

	
	

}
