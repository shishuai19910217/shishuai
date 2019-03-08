package com.ido85.log.utils;

import com.ido85.frame.spring.InstanceFactory;
import com.ido85.log.BusinessLog;
import com.ido85.log.BusinessLogExporter;
import com.ido85.log.application.BusinessLogApplication;
import com.ido85.log.model.DefaultBusinessLog;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * 日志导出器默认实现
 * User: zjzhai
 * Date: 3/5/14
 * Time: 10:05 AM
 */
@Named("businessLogExporter")
public class BusinessLogExporterImpl implements BusinessLogExporter {

    @Inject
    private BusinessLogApplication businessLogApplication;

    @Override
    public void export(BusinessLog businessLog) {
        getBusinessLogApplication().save(DefaultBusinessLog.createBy(businessLog));
    }

    private BusinessLogApplication getBusinessLogApplication() {
        if (null == businessLogApplication) {
            businessLogApplication = InstanceFactory.getInstance(BusinessLogApplication.class);
        }
        return businessLogApplication;
    }

}
