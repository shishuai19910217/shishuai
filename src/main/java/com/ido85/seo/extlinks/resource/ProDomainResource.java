package com.ido85.seo.extlinks.resource;

import javax.inject.Named;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ido85.seo.extlinks.domain.ExtProDomain;

@Named
public interface ProDomainResource extends JpaRepository<ExtProDomain, Long> {
	
}
