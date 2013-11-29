package com.zendaimoney.online.admin.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.zendaimoney.online.admin.entity.Staff;
import com.zendaimoney.online.admin.service.StaffService;

@Component
public class UserDetailsService implements
		org.springframework.security.core.userdetails.UserDetailsService {

	@Autowired
	private StaffService staffService;
	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		Staff staff = staffService.getStaff(username);
		if(null==staff){
			throw new UsernameNotFoundException("");
		}
		return staff;
	}

}
