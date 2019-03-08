package com.ido85.seo.dashboard.resources;

import java.util.Date;
import java.util.List;

import javax.inject.Named;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ido85.seo.dashboard.domain.ProDomain;

@Named
public interface ProDomainResourced extends JpaRepository<ProDomain, Long> {
	
	
}
