/**
 * 
 */
package com.ido85.log.resources;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ido85.log.model.DefaultBusinessLog;

/**
 * @author rongxj
 *
 */
public interface DefaultLogRepository extends JpaRepository<DefaultBusinessLog, String> {

}
