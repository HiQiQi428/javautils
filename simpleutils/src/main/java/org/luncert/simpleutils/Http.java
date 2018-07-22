package org.luncert.simpleutils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import net.sf.json.JSONObject;

public class Http {

    public static String get(URL url) throws IOException {
        URLConnection connection = url.openConnection();
        HttpURLConnection httpConnection = (HttpURLConnection)connection;

        httpConnection.setRequestMethod("GET");
        httpConnection.setRequestProperty("Accept-Charset", "utf-8");
        httpConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

        if (httpConnection.getResponseCode() >= 300) {
            throw new RuntimeException("HTTP reponse code: " + httpConnection.getResponseCode());
        }

        if (httpConnection.getContentLength() > 0) {
            StringBuilder rep = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()))) {
                String line = null;
                while ((line = reader.readLine()) != null) rep.append(line);
            }
            return rep.toString();
        }
        else return null;
    }

    public static String post(URL url, JSONObject data) throws IOException {
        URLConnection connection = url.openConnection();
        HttpURLConnection httpConnection = (HttpURLConnection)connection;

        httpConnection.setRequestMethod("POST");
        httpConnection.setRequestProperty("Accept-Charset", "utf-8");
        httpConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

        try (PrintWriter pw = new PrintWriter(httpConnection.getOutputStream())) {
            pw.append(data.toString()).flush();
        }

        if (httpConnection.getResponseCode() >= 300) {
            throw new RuntimeException("HTTP reponse code: " + httpConnection.getResponseCode());
        }
        
        if (httpConnection.getContentLength() > 0) {
            StringBuilder rep = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()))) {
                String line = null;
                while ((line = reader.readLine()) != null) rep.append(line);
            }
            return rep.toString();
        }
        else return null;
    }

}