package com.util.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class PhantomJS {
    public static void main(String[] args) {
        String BLANK = "  ";
        Process process = null;
        String currentPath = "/E:/company/";
        currentPath = currentPath +"phantomjs.exe";
        try {
            process = Runtime.getRuntime().exec(currentPath + BLANK
                    + "E:/company/ab.js" + BLANK
                    + "http://www.bjhd.gov.cn/" + BLANK
                    + "公开" + BLANK
                    + true + BLANK);
        }catch (IOException e){
            e.printStackTrace();
        }

        InputStream inputStream = process.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuffer sbf = new StringBuffer();
        String tmp = "";
        try {
            while ((tmp = reader.readLine()) != null)
            {
                sbf.append(tmp);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("data:image/jpeg;base64,"+sbf.toString());
    }
}