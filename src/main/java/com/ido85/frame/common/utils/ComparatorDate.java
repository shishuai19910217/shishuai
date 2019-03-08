package com.ido85.frame.common.utils;

import java.util.Comparator;
import java.util.Date;

public class ComparatorDate implements Comparator {  
	  
    public int compare(Object obj1, Object obj2) {  
        Date begin = (Date) obj1;  
        Date end = (Date) obj2;  
        if (end.after(begin)) {  
            return 1;  
        } else {  
            return -1;  
        }  
    }  
}  