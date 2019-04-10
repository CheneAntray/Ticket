package com.base.utils;

public class ChangeUtil {
	public static String conver16HexStr(byte [] b)  
    {  
        StringBuffer result = new StringBuffer();  
        for(int i = 0;i<b.length;i++)  
        {  
            if((b[i]&0xff)<0x10)   
                result.append("0");  
            result.append(Long.toString(b[i]&0xff, 16));  
        }  
        return result.toString().toUpperCase();  
    }  

}
