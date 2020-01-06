package com.rkc.zds.controller;

import com.rkc.zds.dto.AuthorityDto;
import com.rkc.zds.dto.LoginDto;
import com.rkc.zds.dto.UserDto;
import com.rkc.zds.service.AuthenticationService;
import com.rkc.zds.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@RestController
@RequestMapping(value = "/api")
public class AuthenticationController {
	
    @Autowired
    private UserService userService;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationService authenticationService;
   
    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public UserDto authenticate(@RequestBody LoginDto loginDTO, HttpServletRequest request, HttpServletResponse response) throws Exception{
    	
    	// createDefaultAccount();
    	
    	return authenticationService.authenticate(loginDTO, request, response);
    }
    
    private void createDefaultAccount() {
        UserDto user = new UserDto();
        user.setLogin("admin");
        user.setUserName("admin");
        user.setFirstName("Admin");
        user.setLastName("Master");
        user.setPassword("ChangeIt");
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        user.setEnabled(1);
        userService.saveUser(user);
        
		AuthorityDto role = new AuthorityDto();
		role.setUserName(user.getLogin());
		role.setAuthority("ROLE_ADMIN");
		userService.saveAuthority(role);
	}

	@RequestMapping(value = "/logout",method = RequestMethod.GET)
    public void logout(){
        authenticationService.logout();
    }
}
