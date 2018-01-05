package com.util.test;

import redis.clients.jedis.Jedis;

/**
 * Created by 22717 on 2017/11/22.
 * 线程测试
 */
public class ThreadTest extends Thread{
    public static void main(String[] args) {
//        ThreadTest test = new ThreadTest();
//        test.setName("线程名字");
//        test.start();
        //读取JSON串最方便的形式
//        JSONObject json = JSONObject
//                .parseObject("{'username' : '11111','clientid' : 1,'password' : '222222'}");
//        Map<String,Object> map = json;
//        for (Map.Entry<String, Object> stringObjectEntry : map.entrySet()) {
//            System.out.println(stringObjectEntry.getKey()+" = "+stringObjectEntry.getValue());
//            if (stringObjectEntry.getValue() instanceof Integer){
//                System.out.println(stringObjectEntry.getKey()+"类型是"+stringObjectEntry.getValue());
//            }
//        }
        //mongo连接与获取
//        MongoClient client = new MongoClient("127.0.0.1",27017);
//        MongoDatabase database = client.getDatabase("scan");
//        MongoCollection<Document> collection = database.getCollection("temp");
//        FindIterable<Document> iterable = collection.find();
//        for (Document document : iterable) {
//            System.out.println(document);
//        }
        //DOM4J 生成XML
//        Document document = DocumentHelper.createDocument();
//        Element element = document.addElement("root");
//        Element element1 = element.addElement("father");
//        element1.addAttribute("language","zh_CN");
//        Element son = element1.addElement("child");
//        son.setText("男");
//        FileOutputStream fileOutputStream = null;
//        try {
//            fileOutputStream = new FileOutputStream("E:\\王吉凯\\wjk.xml");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        try {
//            XMLWriter xmlWriter = new XMLWriter(fileOutputStream);
//            xmlWriter.write(document);
//            xmlWriter.flush();
//            xmlWriter.close();
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        //
        Jedis jedis = new Jedis("127.0.0.1",6379);
        jedis.echo("1111");
    }

    @Override
    public void run() {
        System.out.println(getName()+"开始工作");
    }
}