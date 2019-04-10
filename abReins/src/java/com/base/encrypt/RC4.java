package com.base.encrypt;


/**
 * RC4加密算法java实现类
 * 提供了加密、解密等方法
 * 测试版本，不命名版本号，待通过联调测试后正式定版本
 * @author xianqin-bill
 *
 */

public class RC4 {
	
	/**
	 * 对byte数组进行解密，以字符串形式进行返回
	 * @param data 密文byte数组对象实例
	 * @param key 盐值
	 * @return 解密后的字符串对象实例
	 */
	public static String decry_RC4(byte[] data, String key) {  
        if (data == null || key == null) {  
            return null;  
        }  
        return asString(RC4Base(data, key));  
    }  
  
	/**
	 * 根据密文进行解密
	 * @param data 密文
	 * @param key 解密key
	 * @return 解密后的密文
	 */
    public static String decry_RC4(String data, String key) {  
        if (data == null || key == null) {  
            return null;  
        }  
        return new String(RC4Base(HexString2Bytes(data), key));  
    }  
  
    /**
     * 对字符串对象进行加密
     * @param data 待加密字符串对象实例
     * @param key 加密盐值
     * @return 加密后的byte数组对象实例
     */
    public static byte[] encry_RC4_byte(String data, String key) {  
        if (data == null || key == null) {  
            return null;  
        }  
        byte b_data[] = data.getBytes();  
        return RC4Base(b_data, key);  
    }  
  
    /**
     * 根据加密盐值将字符串对象实例进行加密
     * @param data 待加密字符串对象
     * @param key 加密盐值
     * @return 加密后的字符串对象实例
     */
    public static String encry_RC4_string(String data, String key) {  
        if (data == null || key == null) {  
            return null;  
        }  
        return toHexString(asString(encry_RC4_byte(data, key)));  
    }  
  
    /**
     * 将byte数组对象实例转换为字符对象实例
     * @param buf  byte数组对象实例
     * @return 字符对象实例
     */
    private static String asString(byte[] buf) {  
        StringBuffer strbuf = new StringBuffer(buf.length);  
        for (int i = 0; i < buf.length; i++) {  
            strbuf.append((char) buf[i]);  
        }  
        return strbuf.toString();  
    }  
  
    /**
     * 处理加密盐值信息，为加密进行准备
     * @param aKey
     * @return
     */
    private static byte[] initKey(String aKey) {  
        byte[] b_key = aKey.getBytes();  
        byte state[] = new byte[256];  
  
        for (int i = 0; i < 256; i++) {  
            state[i] = (byte) i;  
        }  
        int index1 = 0;  
        int index2 = 0;  
        if (b_key == null || b_key.length == 0) {  
            return null;  
        }  
        for (int i = 0; i < 256; i++) {  
            index2 = ((b_key[index1] & 0xff) + (state[i] & 0xff) + index2) & 0xff;  
            byte tmp = state[i];  
            state[i] = state[index2];  
            state[index2] = tmp;  
            index1 = (index1 + 1) % b_key.length;  
        }  
        return state;  
    }  
  
    /**
     * 将字符串转换为16进制字符串
     * @param s 待转换字符串
     * @return 16进制字符串
     */
    private static String toHexString(String s) {  
        String str = "";  
        for (int i = 0; i < s.length(); i++) {  
            int ch = (int) s.charAt(i);  
            String s4 = Integer.toHexString(ch & 0xFF);  
            if (s4.length() == 1) {  
                s4 = '0' + s4;  
            }  
            str = str + s4;  
        }  
        return str;// 0x表示十六进制  
    }  
  
    private static byte[] HexString2Bytes(String src) {  
        int size = src.length();  
        byte[] ret = new byte[size / 2];  
        byte[] tmp = src.getBytes();  
        for (int i = 0; i < size / 2; i++) {  
            ret[i] = uniteBytes(tmp[i * 2], tmp[i * 2 + 1]);  
        }  
        return ret;  
    }  
  
    private static byte uniteBytes(byte src0, byte src1) {  
        char _b0 = (char) Byte.decode("0x" + new String(new byte[] { src0 })).byteValue();  
        _b0 = (char) (_b0 << 4);  
        char _b1 = (char) Byte.decode("0x" + new String(new byte[] { src1 })).byteValue();  
        byte ret = (byte) (_b0 ^ _b1);  
        return ret;  
    }  
  
    private static byte[] RC4Base(byte[] input, String mKkey) {  
        int x = 0;  
        int y = 0;  
        byte key[] = initKey(mKkey);  
        int xorIndex;  
        byte[] result = new byte[input.length];  
  
        for (int i = 0; i < input.length; i++) {  
            x = (x + 1) & 0xff;  
            y = ((key[x] & 0xff) + y) & 0xff;  
            byte tmp = key[x];  
            key[x] = key[y];  
            key[y] = tmp;  
            xorIndex = ((key[x] & 0xff) + (key[y] & 0xff)) & 0xff;  
            result[i] = (byte) (input[i] ^ key[xorIndex]);  
        }  
        return result;  
    }  
  
    public static void main(String[] args) {  
        String inputStr = "做个好男人";  
        String str = encry_RC4_string(inputStr, "123456");  
        System.out.println(str);  
        System.out.println(decry_RC4(str, "123456"));  
    }  
}
