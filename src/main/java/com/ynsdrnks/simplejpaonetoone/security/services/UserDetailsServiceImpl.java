package com.ynsdrnks.simplejpaonetoone.security.services;


import com.ynsdrnks.simplejpaonetoone.security.domain.User;
import com.ynsdrnks.simplejpaonetoone.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static java.util.Collections.emptyList;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{

	@Autowired
	private UserRepository userRepository;

	public UserDetailsServiceImpl(UserRepository applicationUserRepository) {
		this.userRepository = applicationUserRepository;
	}
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	//	User user=userRepository.findByUsername(username);
		User user=userRepository.findByEmail(username);
		User_Details userDetails=null;
		if (user!=null) {
			userDetails=new User_Details();
			userDetails.setUser(user);
		}else {
			throw new UsernameNotFoundException("User not exist with this name : "+username);
		}
		return new org.springframework.security.core.userdetails.User(user.getEmail(),user.getPassword(),emptyList());

	}

}
