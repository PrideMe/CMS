package com.wangjikai.util;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;

/**
 * Created by jikai_wang on 2018/1/5.
 * 提供根据IP地址获取大致地理位置信息的功能
 */
public class GeoIPUtil {
    public static void main(String[] args) throws IOException, GeoIp2Exception {
        String pathname = GeoIPUtil.class.getResource("/").getPath();
        File database = new File(pathname + "GeoLite2-City.mmdb");
        // 创建 DatabaseReader对象
        DatabaseReader reader = new DatabaseReader.Builder(database).build();
        InetAddress ipAddress = InetAddress.getByName("114.239.253.38");
        CityResponse response = reader.city(ipAddress);

        System.out.print(response.getContinent().getNames().get("zh-CN"));//大洲
        System.out.println(response.getCountry().getNames().get("zh-CN"));
        //System.out.println(response.getLeastSpecificSubdivision().getNames().get("zh-CN"));
        //System.out.println(response.getMostSpecificSubdivision().getNames().get("zh-CN"));
        System.out.println(response.getSubdivisions().get(0).getNames().get("zh-CN"));//
        System.out.println(response.getCity().getNames().get("zh-CN"));
        System.out.println(response.getLocation().getLongitude()+" "+response.getLocation().getLatitude());//
    }
}

//Continent大陆
