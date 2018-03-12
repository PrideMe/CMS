package com.wangjikai.util;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jikai_wang on 2018/3/2.
 * Http 与 Servlet工具类
 */
public class ServletUtil {
    /**
     * 循环遍历前台表单传入的值，并返回Map
     * @param request
     * @return
     */
    public static Map<String,String> parseForm(HttpServletRequest request){
        Map<String,String> parameters = new HashMap<>();
        if (request == null) {
            return parameters;
        }
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()){
            String parameterName = parameterNames.nextElement();
            parameters.put(parameterName,request.getParameter(parameterName));
        }
        return parameters;
    }
}
