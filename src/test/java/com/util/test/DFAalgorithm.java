package com.util.test;

import java.io.*;
import java.util.*;

/**
 * Created by 22717 on 2017/11/29.
 * 实现DFA算法，对于敏感词、广告词的过滤
 * DFA算法模型
 */
public class DFAalgorithm {
    public static void main(String[] args) throws IOException {
        //敏感词词库
//        long beginTime1 = System.currentTimeMillis();
        Set<String> keyWordSet = new DFAalgorithm().getSensitivekeyWordSet();
        Map<Object,Object> sensitiveWordMap = toDFA(keyWordSet);   //敏感词词库模型
//        long endTime1 = System.currentTimeMillis();
//        System.out.println("构建词库耗时："+(endTime1 - beginTime1)+"毫秒");
        String txt = "新唐人建设性服务逼上梁山我的大小姐，曾庆红江泽民带中石九评共产党化说亏损着一洗脑班种小屌强。奸弟，武操你妈力解放台湾问题，毛泽东是伟红满堂大的，卖淫是不yuce合法的。援交也是发我去额";
        //System.out.println(ToAnalysis.parse(txt));
        System.out.println("原句：     "+txt);
        Set<String> strings = new HashSet<>();
        long beginTime = System.currentTimeMillis();
        for (int i = 0; i < txt.length() ; i++) {
            int sensitive = checkSensitiveWord(txt,sensitiveWordMap,i);
            if (sensitive>0){
                strings.add(txt.substring(i,i+sensitive));
                i = i + sensitive - 1;
            }
        }
        long endTime = System.currentTimeMillis();
        System.out.println("敏感词库:  "+sensitiveWordMap.size());
//        for (String s : keyWordSet) {
//            System.out.println(s);
//        }
        System.out.println("敏感词：   "+strings);
        System.out.println("耗时：     "+(endTime - beginTime)+"毫秒");
        Iterator<String> iterator = strings.iterator();  //迭代包含的敏感词
        String word = null;  //单个敏感词
        String replacrWord = "";  //代替词
        while (iterator.hasNext()){
            word = iterator.next();
            for (int i = 0; i < word.length(); i++) {
                replacrWord += "*";
            }
            txt = txt.replaceAll(word,replacrWord);
            replacrWord = "";
        }
        System.out.println(txt);
    }
    //转化为DFA模型。注意内存指针的变化
    private static Map<Object,Object> toDFA(Set<String> keyWordSet){
        Map<Object,Object> sensitiveWordMap = new HashMap<>(keyWordSet.size()+10); //初始化敏感词容器，减少扩容操作

        String keyWord = null;  //单个敏感词
        Map<Object,Object> nowMap = null;
        Map<Object,Object> newWordMap = null;
        Iterator<String> iterator = keyWordSet.iterator(); //迭代keyWordSet
        while(iterator.hasNext()){
            keyWord = iterator.next();    //单个敏感词
            nowMap = sensitiveWordMap;
            for(int i = 0 ; i < keyWord.length() ; i++){  //敏感词拆分成单个字符
                char keyWordChar = keyWord.charAt(i);     //单个敏感字
                Object wordMap = nowMap.get(keyWordChar); //获取

                if(wordMap != null){    //如果存在该key，直接赋值
                    nowMap = (Map<Object,Object>) wordMap;
                }
                else{     //不存在则构建一个map，同时将isEnd设置为0，因为他不是最后一个
                    newWordMap = new HashMap<>();
                    newWordMap.put("isEnd", "0");     //不是最后一个
                    nowMap.put(keyWordChar, newWordMap);
                    nowMap = newWordMap;
                }

                if(i == keyWord.length() - 1){
                    nowMap.put("isEnd", "1");    //最后一个
                }
            }
        }
        return sensitiveWordMap;
    }

    //从一段文字中检测敏感词并显示
    private static int checkSensitiveWord(String txt,Map<Object,Object> sensitiveWordMap,int beginIndex){
        boolean flag = false;  //敏感词结束标识
        int matchCount = 0; //匹配数
        char word = 0;   //检测字符
        Map<Object,Object> nowMap = sensitiveWordMap;  //敏感词库
        for (int i = beginIndex; i < txt.length() ; i++) {
            word = txt.charAt(i);   //获取待检测文本中的单个字符
            nowMap = (Map<Object, Object>) nowMap.get(word);  //敏感词匹配
            if (nowMap != null) { //匹配成功.
                matchCount++;
                if ("1".equals(nowMap.get("isEnd"))){
                    flag = true;  //结束标识
                    if (1==2){
                        break;
                    }
                }
            }else {  //没有匹配
                break;
            }
        }
        if (matchCount < 2 || !flag){
            matchCount = 0;
        }
        return matchCount;
    }

    //从文件中加载敏感词库
    private Set<String> getSensitivekeyWordSet() throws IOException {
        Set<String> set = null;
        //通过类加载器获取
        ClassLoader classloader = getClass().getClassLoader();
        File file = new File(classloader.getResource("sensitive.txt").getFile());
        InputStreamReader reader = new InputStreamReader(new FileInputStream(file));
        if (file.isFile() && file.exists()){
            set = new HashSet<>();
            BufferedReader bufferedReader = new BufferedReader(reader);
            String txt = null;
            while ((txt = bufferedReader.readLine())!=null){
                set.add(txt);
            }
        }
        reader.close();
        return set;
    }
}