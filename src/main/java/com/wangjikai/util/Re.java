package com.wangjikai.util;

/**
 * Created by 22717 on 2017/11/2.
 * 反射工具练习
 */
public class Re<T> {
    static String a;
    public static void main(String[] args) throws IllegalAccessException, InstantiationException {
//        Type type = new Re().getClass().getGenericSuperclass();
//        Type parameterizedType = ((ParameterizedType)type).getActualTypeArguments()[0];
//        System.out.println(parameterizedType.getTypeName());

//        Map<String,String> map = new ConcurrentHashMap<>();
//        map.put("msg","failed");
//        System.out.println(map);
//        map.put("msg","success");
//        System.out.println(map);

//        int a = 5;
//        System.out.println(a++ + ++a);

//        String classFile = "com7jd7". replaceAll("\\D", "/") + "MyClass.class";
//        System.out.println(classFile);
        boolean b = true ? false : true == true ? false : true;
        System.out.println(b);
    }
}

