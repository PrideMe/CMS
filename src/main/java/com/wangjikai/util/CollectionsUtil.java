package com.wangjikai.util;

import java.util.Collections;
import java.util.List;

/**
 * Created by jikai_wang on 2018/2/26.
 * 集合操作-工具类
 */
public class CollectionsUtil {
    //判断两个基本类型的集合是否相等
    public static <T extends Comparable<T>> boolean compareList(List<T> a, List<T> b){
        if(a.size() != b.size())
            return false;
        Collections.sort(a);
        Collections.sort(b);
        for (int i = 0; i < a.size(); i++) {
            if (!a.get(i).equals(b.get(i)))
                return false;
        }
        return true;
    }
}
