package com.base.utils;

import java.util.UUID;

/**
 * 获取UUID
 * UUID(Universally Unique Identifier)全局唯一标识符,是指在一台机器上生成的数字，
 * 它保证对在同一时空中的所有机器都是唯一的。按照开放软件基金会(OSF)制定的标准计算，
 * 用到了以太网卡地址、纳秒级时间、芯片ID码和许多可能的数字。
 * 由以下几部分的组合：
 * 当前日期和时间(UUID的第一个部分与时间有关，如果你在生成一个UUID之后，过几秒又生成一个UUID，则第一个部分不同，其余相同)，
 * 时钟序列，全局唯一的IEEE机器识别号（如果有网卡，从网卡获得，没有网卡以其他方式获得），UUID的唯一缺陷在于生成的结果串会比较长
 * @author xianqin-bill
 */
public class UUIDGenerator {
	public UUIDGenerator() { 
    }
	
	 /** 
     * 获得一个UUID
     * 社管资产项目，可以用这个方法来生成数据库主键
     * @return String UUID 
     */ 
    public static String getUUID(){ 
        String s = UUID.randomUUID().toString(); 
        //去掉"_"符号 
        return s.substring(0,8)+s.substring(9,13)+s.substring(14,18)+s.substring(19,23)+s.substring(24); 
    } 
    /** 
     * 获得指定数目的UUID 
     * @param number int 需要获得的UUID数量 
     * @return String[] UUID数组 
     */ 
    public static String[] getUUID(int number){ 
        if(number < 1){ 
            return null; 
        } 
        String[] ss = new String[number]; 
        for(int i=0;i<number;i++){ 
            ss[i] = getUUID(); 
        } 
        return ss; 
    }
    
    public static void main(String[] gres){
    	System.out.println(UUIDGenerator.getUUID());
    }
}
