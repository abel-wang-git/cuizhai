package com.cch.cz;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.assertj.core.util.Lists;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.slf4j.LoggerFactory;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;

/**
 * Created by Administrator on 2019/1/30.
 */
public class Http {
    public static void main(String[] args) throws IOException {
        Set<String> loggers = new HashSet<>(Arrays.asList("org.apache.http", "groovyx.net.http"));

        for(String log:loggers) {
            Logger logger = (Logger) LoggerFactory.getLogger(log);
            logger.setLevel(Level.INFO);
            logger.setAdditive(false);
        }
        HttpClient client = buildHttpClient();
        HttpPost request = new HttpPost("http://nqueue.meritibms.cn/QueueingAsyncHandler.ashx");
        request.addHeader("connection","keep-alive");
        request.addHeader("timeout","30000");
        List<NameValuePair> params = Lists.newArrayList();
        params.add(new BasicNameValuePair("clientType", "1"));
        params.add(new BasicNameValuePair("keyword", "1222"));

        HttpResponse response= client.execute(request);
        System.out.println(response.getStatusLine());
        HttpEntity entity = response.getEntity();
        if(entity != null){
            //响应输入流
            InputStream is = entity.getContent();
            //转换为字符输入流
            BufferedReader br = new BufferedReader(new InputStreamReader(is, Consts.UTF_8));
            String line = null;
            while((line=br.readLine())!=null){
                System.out.println("响应"+line);
            }
            //关闭输入流
            is.close();
        }

        System.out.println("over");

    }
    static HttpClient buildHttpClient() {
        PoolingHttpClientConnectionManager connectionManager =
                new PoolingHttpClientConnectionManager(3, TimeUnit.MINUTES);
        connectionManager.setMaxTotal(4);
        connectionManager.setDefaultMaxPerRoute(2);

        HttpClientBuilder builder = HttpClients.custom()
                .setConnectionManager(connectionManager);

        return builder.build();
    }
}
