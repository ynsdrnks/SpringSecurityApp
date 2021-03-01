package com.ynsdrnks.simplejpaonetoone.security.services;


import com.ynsdrnks.simplejpaonetoone.security.domain.User;
import com.ynsdrnks.simplejpaonetoone.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class User_DetailsService  implements UserDetailsService{

	@Autowired
	private UserRepository userRepository;
	
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
		return userDetails;

	}

}
