package com.example.demo.commons.utils;

import java.security.MessageDigest;

/**
 * @author liwenji
 * @ClassName Md5Tools
 * @Description TODO
 * @date 2022/6/3 14:33
 * @Version 1.0
 */
public class Md5Tools {

    /***
     * MD5加码 生成32位md5码
     */
    public static String string32Md5(String inStr){
        MessageDigest md5 = null;
        try{
            md5 = MessageDigest.getInstance("MD5");
        }catch (Exception e){
            System.out.println(e.toString());
            e.printStackTrace();
            return "";
        }
        char[] charArray = inStr.toCharArray();
        byte[] byteArray = new byte[charArray.length];

        for (int i = 0; i < charArray.length; i++) {
            byteArray[i] = (byte) charArray[i];
        }
        byte[] md5Bytes = md5.digest(byteArray);
        StringBuilder hexValue = new StringBuilder ();
        for ( byte md5Byte : md5Bytes ) {
            int val = (( int ) md5Byte) & 0xff;
            if ( val < 16 ) {
                hexValue.append ("0");
            }
            hexValue.append (Integer.toHexString (val));
        }
        return hexValue.toString();

    }

    /**
     * 执行一次加密，俩次解密
     *
     * @param inStr
     * @return
     */
    public static String convertMd5(String inStr){

        char[] a = inStr.toCharArray();
        for (int i = 0; i < a.length; i++){
            a[i] = (char) (a[i] ^ 't');
        }
        String s = new String(a);
        return s;

    }

}
