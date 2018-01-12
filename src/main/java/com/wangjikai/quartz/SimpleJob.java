package com.wangjikai.quartz;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 22717 on 2017/12/20.
 * 一个简单任务
 */
@Component
public class SimpleJob {
    @Resource
    private JavaMailSender mailSender;

    public void out(){
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(simpleDateFormat.format(date)+"简单任务执行中···");

        String Url = "http://www.sojson.com/open/api/weather/json.shtml?city=济南";
        StringBuffer strBuf;
        try {
            //Url = "https://free-api.heweather.com/s6/weather/now?location="+ URLEncoder.encode("济南", "utf-8")+"&key=6e56fae9eb8e4e5f8fbbc13d1f15c292";
            Url = "http://www.sojson.com/open/api/weather/json.shtml?city="+ URLEncoder.encode("济南", "utf-8");
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        strBuf = new StringBuffer();
        try{
            URL url = new URL(Url);
            URLConnection conn = url.openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(),"utf-8"));//转码。
            String line = null;
            while ((line = reader.readLine()) != null)
                strBuf.append(line + " ");
            reader.close();
        }catch(MalformedURLException e) {
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }
        JSONObject jsonData = JSONObject.parseObject(strBuf.toString());
        Map<String, Object> weatherInfo = new HashMap<>();
        if ("200".equals(jsonData.getString("status"))){
            String city = jsonData.getString("city");
            weatherInfo.put("城市",city);
//            String shidu = jsonData.getJSONObject("data").getString("shidu");
//            String pm25 = jsonData.getJSONObject("data").getString("pm25");
//            String wendu = jsonData.getJSONObject("data").getString("wendu");
//            String quality = jsonData.getJSONObject("data").getString("quality");
            JSONArray jsonArray = jsonData.getJSONObject("data").getJSONArray("forecast");
            for (int i = 0; i < jsonArray.size(); i++) {
                String workDate = ((JSONObject)jsonArray.get(i)).getString("date");
                String high = ((JSONObject)jsonArray.get(i)).getString("high");
                String low = ((JSONObject)jsonArray.get(i)).getString("low");
                String fl = ((JSONObject)jsonArray.get(i)).getString("fl");
                String type = ((JSONObject)jsonArray.get(i)).getString("type");
                String aqi = ((JSONObject)jsonArray.get(i)).getString("aqi");
                String notice = ((JSONObject)jsonArray.get(i)).getString("notice");
                Map<String,String> map = new HashMap<>();
                map.put("温度",low+"到"+high);
                map.put("风力",fl);
                map.put("天气",type);
                map.put("AQI",aqi);
                map.put("建议",notice);
                weatherInfo.put(workDate,map);
            }
        }
//        weatherInfo.put("温度", jsonData.getJSONArray("HeWeather6").getJSONObject(0).getJSONObject("now").getString("fl") +"°C");
//        weatherInfo.put("天气", jsonData.getJSONArray("HeWeather6").getJSONObject(0).getJSONObject("now").getString("cond_txt"));
//        weatherInfo.put("风向",jsonData.getJSONArray("HeWeather6").getJSONObject(0).getJSONObject("now").getString("wind_dir"));
//        weatherInfo.put("风力",jsonData.getJSONArray("HeWeather6").getJSONObject(0).getJSONObject("now").getString("wind_sc"));
//        weatherInfo.put("风速",jsonData.getJSONArray("HeWeather6").getJSONObject(0).getJSONObject("now").getString("wind_spd")+"公里/小时");
        //return weatherInfo;
        SimpleMailMessage email = new SimpleMailMessage();
        email.setFrom("15314006321@163.com");
        email.setTo("549469611@qq.com");
        email.setSubject(simpleDateFormat.format(date));
        email.setText(weatherInfo.toString());
        mailSender.send(email);
    }

    public static void main(String[] args) {
        new SimpleJob().out();
    }
}
