package com.rkc.zds.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.rkc.zds.dto.AuthorityDto;

public interface AuthorityRepository extends JpaRepository<AuthorityDto, Integer> {
	
	Page<AuthorityDto> findByUserName(Pageable pageable, String userName);
	
}
