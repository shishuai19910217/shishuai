package com.ido85.log.utils;

import com.ido85.log.BusinessLog;
import com.ido85.log.BusinessLogExporter;

/**
 * 日志导出器默认实现
 * User: zjzhai
 * Date: 3/5/14
 * Time: 10:05 AM
 */
//@Named("businessLogExporter")
public class BusinessLogConsoleExporter implements BusinessLogExporter {


    @Override
    public void export(BusinessLog businessLog) {
        System.out.println(businessLog);
    }


}
