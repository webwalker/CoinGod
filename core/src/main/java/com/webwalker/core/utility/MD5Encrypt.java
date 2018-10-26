package com.webwalker.core.utility;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigInteger;
import java.security.MessageDigest;

/**
 * MD5加密算法
 */
public class MD5Encrypt {
    public static String encode(String source) {
        return encode(source, true);
    }

    public static String encode(String source, boolean isUpper) {
        String s = null;
        char lLower[] = {'a', 'b', 'c', 'd', 'e', 'f'};
        char lUpper[] = {'A', 'B', 'C', 'D', 'E', 'F'};
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                '0', '0', '0', '0', '0', '0'};
        if (isUpper) {
            for (int i = 10; i < 16; i++) {
                hexDigits[i] = lUpper[i - 10];
            }
        } else {
            for (int i = 10; i < 16; i++) {
                hexDigits[i] = lLower[i - 10];
            }
        }
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(source.getBytes());
            byte tmp[] = md.digest();
            char str[] = new char[16 * 2];
            int k = 0;
            for (int i = 0; i < 16; i++) {
                byte byte0 = tmp[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            s = new String(str);

        } catch (Exception e) {
            e.printStackTrace();

        }
        return s;
    }

    public static String encode(File file) {
        return encode(file, true);
    }

    /**
     * 获取文件的MD5
     *
     * @param file
     * @return
     */
    public static String encode(File file, boolean upper) {
        if (!file.isFile()) {
            return null;
        }
        MessageDigest digest = null;
        FileInputStream in = null;
        byte buffer[] = new byte[1024];
        int len;
        try {
            digest = MessageDigest.getInstance("MD5");
            in = new FileInputStream(file);
            while ((len = in.read(buffer, 0, 1024)) != -1) {
                digest.update(buffer, 0, len);
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        BigInteger bigInt = new BigInteger(1, digest.digest());
        String md5 = bigInt.toString(16);
        return upper ? md5.toUpperCase() : md5;
    }
}
