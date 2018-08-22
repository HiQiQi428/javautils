package org.luncert.simpleutils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
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

    public static String randomString(int n) {
        Random random = new Random();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < n; i++) {
            int r = random.nextInt(62);
            if (r < 10) builder.append(r);
            else if (r < 36) builder.append((char)(r + 55));
            else builder.append((char)(r + 61));
        }
        return builder.toString();
    }
    
}