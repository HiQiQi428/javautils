package org.luncert.simpleutils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

public class CipherHelper {

    public static String hashcode(String raw) {
        MessageDigest md;
		try {
			md = MessageDigest.getInstance("MD5");
            byte[] input = raw.getBytes();
            md.update(input);
            byte[] md5Bytes = md.digest();
            BigInteger bigInteger = new BigInteger(1, md5Bytes);
            return bigInteger.toString(16);
		} catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
		}
    }

    public static String getUUID(int n) {
        String ret = "";
        int loopTime = n / 32 + ((n % 32 == 0) ? 0 : 1);
        for (int i = 0, rest = n;; i++, rest -= 32) {
            if (i < loopTime - 1)
                ret += UUID.randomUUID().toString().trim().replaceAll("-", "");
            else {
                ret += UUID.randomUUID().toString().trim().replaceAll("-", "").substring(0, rest);
                break;
            }
        }
        return ret;
    }
    
}