package com.sophon.schedule.api;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.HttpClients;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.*;

/**
 * TODO
 *
 * @Author jinmu
 * @Date 2024/2/1 22:13
 */
public class EsTest {

    public static void main(String[] args) {

        String filePath = "/Users/jinmu/Downloads/self/sophon-schedule/sophon-schedule-api/src/main/resources/2.txt";  // 替换为实际文件路径

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] s = line.split(",");
                String key = s[0].trim();
                String value = s[1].trim();
                createAlias("",value,key);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void createAlias(String indexMapping, String indexName,String alias){
        // Elasticsearch服務的URL和索引名稱
        String elasticsearchUrl = "http://192.168.100.100:9200";
//        String indexName = "your_index_name";

        // Elasticsearch索引的映射定義，這裡只是一個簡單的例子
//        String indexMapping = "{ \"mappings\": { \"properties\": { \"field1\": { \"type\": \"text\" }, \"field2\": { \"type\": \"keyword\" } } } }";

        // 建立索引的HTTP PUT請求URL
        String putIndexUrl = elasticsearchUrl + "/" + indexName+"/_alias/"+alias;

        // 創建HttpClient
        HttpClient httpClient = HttpClients.createDefault();

        try {
            // 創建HTTP PUT請求
            HttpPut httpPut = new HttpPut(putIndexUrl);

            // 設置請求頭部
            httpPut.setHeader("Content-Type", "application/json");

            // 設置請求內容
//            StringEntity entity = new StringEntity(indexMapping);
//            httpPut.setEntity(entity);

            // 發送HTTP PUT請求
            HttpResponse response = httpClient.execute(httpPut);

            // 讀取響應內容
            HttpEntity responseEntity = response.getEntity();
            BufferedReader reader = new BufferedReader(new InputStreamReader(responseEntity.getContent()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            // 關閉資源
            reader.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 關閉HttpClient
            httpClient.getConnectionManager().shutdown();
        }
    }
}
