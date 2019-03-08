package com.ido85.log.application;

import java.util.List;

import com.ido85.log.model.DefaultBusinessLog;
import com.ido85.log.model.DefaultBusinessLogDTO;

/**
 * User: zjzhai
 * Date: 12/11/13
 * Time: 3:11 PM
 */
public interface BusinessLogApplication {

    public DefaultBusinessLogDTO getDefaultBusinessLog(String id);

    public void removeDefaultBusinessLog(String id);

    void save(DefaultBusinessLog log);

    public void removeDefaultBusinessLogs(String[] ids);

    public List<DefaultBusinessLogDTO> findAllDefaultBusinessLog();

//    public Page<DefaultBusinessLogDTO> pageQueryDefaultBusinessLog(DefaultBusinessLogDTO defaultBusinessLog, int currentPage, int pageSize);


}
