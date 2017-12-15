package com.util.test;

import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.*;

public class NewDFA {
    private Set<String> getSensitiveWordSet(String fileName) throws IOException {
        Set<String> set = set = new HashSet<>();
        ClassLoader loader = NewDFA.class.getClassLoader();
        //通过类加载器获取
        InputStreamReader reader = new InputStreamReader(loader.getResourceAsStream(fileName),"UTF-8");
        BufferedReader bufferedReader = new BufferedReader(reader);
        String txt = null;
        while ((txt = bufferedReader.readLine())!=null){
            set.add(txt);
        }
        reader.close();
        return set;
    }

    public static Map<Object,Object> toDFA(Set<String> WordSet){
        Map<Object,Object> sensitiveWordMap = new HashMap<>(WordSet.size()); //初始化敏感词容器，减少扩容操作

        String keyWord = null;  //单个敏感词
        Map<Object,Object> nowMap = null;
        Map<Object,Object> newWordMap = null;
        Iterator<String> iterator = WordSet.iterator(); //迭代keyWordSet
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

    /**
     * 从一段文字中检测敏感词并显示
     * @param txt 需要检测的文字
     * @param sensitiveWordMap  敏感词库
     * @param beginIndex 开始字段
     * @return
     */
    public static int checkSensitiveWord(String txt,Map<Object,Object> sensitiveWordMap,int beginIndex){
        boolean flag = false;  //敏感词结束标识
        int matchCount = 0; //匹配数
        char word = 0;   //检测字符
        Map<Object,Object> nowMap = sensitiveWordMap;  //敏感词库
//        for (String s : txt) {
//            nowMap = (Map<Object, Object>) nowMap.get(s);  //敏感词匹配
//            if (nowMap != null) { //匹配成功.
//                matchCount++;
//                if ("1".equals(nowMap.get("isEnd"))){
//                    flag = true;  //结束标识
//                    break;
//                }
//            }else {  //没有匹配
//                break;
//            }
//        }
        for (int i = beginIndex; i < txt.length() ; i++) {
            word = txt.charAt(i);   //获取待检测文本中的单个字符
            nowMap = (Map<Object, Object>) nowMap.get(word);  //敏感词匹配
            if (nowMap != null) { //匹配成功.
                matchCount++;
                if ("1".equals(nowMap.get("isEnd"))){
                    flag = true;  //结束标识
                    break;
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

    //接受待检测文本的List
    public static int check(List<String> strings,Set<String> keyWordSet,int beginIndex){
        int matchCount = -1; //匹配数。不存在则为-1
        String word = null;   //检测字符
        //Map<Object,Object> nowMap = sensitiveWordMap;  //敏感词库
        for (int i = beginIndex; i < strings.size(); i++) {
            word = strings.get(i);   //获取待检测文本中的单个分词
            if (keyWordSet.contains(word)) {
                matchCount = i;
                break;
            }
        }
        return matchCount;
    }

    /**
     * description：对于普通网站，使用IK默认分词即可
     * 对于涉嫌黄赌毒的网站，需要加载自定义词典
     * 通过IKAnalyzer.cfg.xml把需要加载的字典包含进去即可加载
     * IK不支持词性分析，see ansj分词器
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        Set<String> keyWordSet = new NewDFA().getSensitiveWordSet("sensitive.txt");  //加载敏感词词库
        String txt = "将现场氛围带入了高潮射精";
        System.out.println("原句：     "+txt);
        Lexeme lex=null;
        List<String> list = null;

        StringReader sr=new StringReader(txt);
        IKSegmenter ik=new IKSegmenter(sr, true);
        list = new ArrayList<>();
        while((lex=ik.next())!=null){
            list.add(lex.getLexemeText());
        }
        System.out.println("分词结果："+list);

        Set<String> strings = new HashSet<>();
        long beginTime = System.nanoTime();
        for (int i = 0; i < list.size(); i++) {
            int sensitive = check(list,keyWordSet,i);
            if (sensitive!=-1) {
                strings.add(list.get(sensitive));
            }
        }
        long endTime = System.nanoTime();
        System.out.println("耗时：     "+(endTime - beginTime)/1000+"微妙");
        System.out.println(strings);
    }
}
