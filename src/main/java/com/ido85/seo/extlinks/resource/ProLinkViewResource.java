package com.ido85.seo.extlinks.resource;

import java.util.Date;
import java.util.List;

import javax.inject.Named;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ido85.seo.extlinks.domain.ExtProLinksView;
import com.ido85.seo.extlinks.dto.ProjectExtDto;

@Named
public interface ProLinkViewResource extends JpaRepository<ExtProLinksView, Long> {
	
	
	
	
}
