package com.netease.vcloud.qa.service.tc.data;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;


public class MD5Util {
    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyyMMdd");

    public static String getMD5(byte[] bytes) {
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        char str[] = new char[16 * 2];
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            md.update(bytes);
            byte tmp[] = md.digest();
            int k = 0;
            for (int i = 0; i < 16; i++) {
                byte byte0 = tmp[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return new String(str);
    }

    public static String getMD5(String value) {
        String result = "";
        try {
            result = getMD5(value.getBytes("UTF-8"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    private static final char[] HEX_DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    public static String generateSignature(String secret, long timestamp) throws NoSuchAlgorithmException {
        String value = secret + timestamp;

        MessageDigest messageDigest = MessageDigest.getInstance("md5");
        messageDigest.update(value.getBytes());
        byte[] bb = messageDigest.digest();

        String s = getFormattedText(bb);
        return s;
    }

    public static String getFormattedText(byte[] bytes) {
        int len = bytes.length;
        StringBuilder buf = new StringBuilder(len * 2);
        for (int j = 0; j < len; j++) {
            buf.append(HEX_DIGITS[(bytes[j] >> 4) & 0x0f]);
            buf.append(HEX_DIGITS[bytes[j] & 0x0f]);
        }
        return buf.toString();
    }

//    public static void main(String[] args) {
//        Date today = new Date();
//        String sign = getMD5("ptstest" + SIMPLE_DATE_FORMAT.format(today) + "0142b4c5d102431592e010732d86ad51");
//        System.out.println(sign);
//    }


}

