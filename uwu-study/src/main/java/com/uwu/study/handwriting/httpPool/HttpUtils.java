package com.uwu.study.handwriting.httpPool;


import cn.hutool.log.Log;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpUtils {
    
    private static final Logger log = LoggerFactory.getLogger(HttpUtils.class);

    //httpGet请求
    public static String get(CloseableHttpClient httpClient,String url){
        HttpGet httpGet = new HttpGet(url);
        return doRequest(httpClient,url,httpGet);
    }
    public static String jsonPost(CloseableHttpClient httpClient,String url,String json){
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("Content-type","application/json");
        StringEntity entity = new StringEntity(json,"UTF-8");
        httpPost.setEntity(entity);
        return doRequest(httpClient,url,httpPost);
    }


    //统一的请求处理逻辑
    private static String doRequest(CloseableHttpClient httpClient, String url, HttpRequestBase  httpRequest) {
        try(CloseableHttpResponse response = httpClient.execute(httpRequest)){
            int code = response.getStatusLine().getStatusCode();
            HttpEntity responseEntity = response.getEntity();
            String responseBody = null;
            if(responseEntity != null){
                responseBody = EntityUtils.toString(responseEntity);
            }
            if(code != 200){
                log.error("http post error,url:{},code:{},result:{}",url,code,responseBody);
                return null;
            }
            return responseBody;
        } catch (Exception e){
            log.error("http post error, url:{}",url,e);
        }
        return null;
    }

}
