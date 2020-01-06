package com.rkc.zds.controller;

import com.rkc.zds.api.exception.ResourceNotFoundException;
import com.rkc.zds.model.ProfileData;
import com.rkc.zds.dto.UserDto;
import com.rkc.zds.repository.UserRepository;
import com.rkc.zds.service.ProfileQueryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/profiles/{userName:.+}")
public class ProfileApi {
    private ProfileQueryService profileQueryService;
    private UserRepository userRepository;
    
    @Autowired
    public ProfileApi(ProfileQueryService profileQueryService, UserRepository userRepository) {
        this.profileQueryService = profileQueryService;
        this.userRepository = userRepository;
    }

    @GetMapping
    public ResponseEntity getProfile(@PathVariable("userName") String userName) {
    	
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		String userLogin = authentication.getName();

		Optional<UserDto> userDto = userRepository.findByUserName(userLogin);
		
		UserDto user = null;
		
		if(userDto.isPresent()) {
			user = userDto.get();
		}
		
    	return profileQueryService.findByUserName(userName, user)
            .map(this::profileResponse)
            .orElseThrow(ResourceNotFoundException::new);
    }

    private ResponseEntity profileResponse(ProfileData profile) {
        return ResponseEntity.ok(new HashMap<String, Object>() {{
            put("profile", profile);
        }});
    }

}
