package com.cch.cz;

import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.AuthSchemes;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.*;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.*;

public class Test {
    /* Http连接池只需要创建一个，因此采用单例模式的饿汉模式 */
    private static PoolingHttpClientConnectionManager httpPoolManager = null;
    /* 连接池最大生成连接数200 */
    private static int Pool_MaxTotal = 0;
    /* 连接池默认路由最大连接数,默认为20 */
    private static int Pool_MaxRoute = 0;
    /* 请求超时时间 */
    private static int Request_TimeOut = 0;

    /* 连接池参数配置 */
    static {
  /*连接池总的最大生成连接数500 */
        Pool_MaxTotal = 500;

  /* 连接池每个路由的最大连接数,默认为20 */
        Pool_MaxRoute = 100;

  /* 请求超时时间 60 * 1000 */
        Request_TimeOut = 60000;

  /* 创建连接池 */
        if (httpPoolManager == null) {
   /* 初始化连世界管理器 */
            httpPoolManager = new PoolingHttpClientConnectionManager();

            // 连接池总的最大生成连接数
            httpPoolManager.setMaxTotal(Pool_MaxTotal);

            // 设置每个route最大连接数
            httpPoolManager.setDefaultMaxPerRoute(Pool_MaxRoute);
        }
    }

    /* 定义日志管理器 */
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    /* HttpClient */
    private CloseableHttpClient httpClient = null;
    /*文件地址*/
    private String fileUrl;
    /*判断返回的是字符串还是数据流*/
    private boolean isString = true;

    /**
     * 创建HttpClient
     */
    public HttpClient getHttpClient() {
        CookieStore cookieStore = new BasicCookieStore();

        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();

        RequestConfig defaultRequestConfig = RequestConfig
                .custom()
                .setConnectTimeout(Request_TimeOut)
                .setConnectionRequestTimeout(Request_TimeOut)
                .setSocketTimeout(Request_TimeOut)
                .setCookieSpec(CookieSpecs.DEFAULT)
                .setExpectContinueEnabled(true)
                .setTargetPreferredAuthSchemes(
                        Arrays.asList(AuthSchemes.NTLM, AuthSchemes.DIGEST))
                .setProxyPreferredAuthSchemes(Arrays.asList(AuthSchemes.BASIC))
                .build();

        // 设置重定向策略
        LaxRedirectStrategy redirectStrategy = new LaxRedirectStrategy();

        // 创建httpClient
        CloseableHttpClient httpClient = HttpClients.custom()
                .setConnectionManager(httpPoolManager)
                .setDefaultCookieStore(cookieStore)
                .setDefaultCredentialsProvider(credentialsProvider)
                .setDefaultRequestConfig(defaultRequestConfig)
                .setRedirectStrategy(redirectStrategy)
                .build();

        this.httpClient = httpClient;

        return httpClient;
    }

    /**
     * form的enctype=application/x-www-form-urlencoded或text/plain的posten通讯
     *
     * @throws IOException
     * @throws ClientProtocolException
     */
    public Object httpPostString(String url, HashMap<String, String> postMap)
            throws ClientProtocolException, IOException {
        long beginTime = System.currentTimeMillis();
 
  /* 返回内容 */
        Object returnContent = null;
 
  /* 创建HttpPost */
        HttpPost httpPost = new HttpPost(url);
 
  /* 在header中添加token */
        httpPost.setHeader("token", "puji");
 
  /* 拼接Post传输参数 */
        UrlEncodedFormEntity encoderFormData = formatData(postMap);

        httpPost.setEntity(encoderFormData);
 
  /* 执行post请求 */
        CloseableHttpResponse httpResponse = (CloseableHttpResponse) getHttpClient()
                .execute(httpPost);
 
  /* 读取通讯返回的status code */
        int responseStatus = httpResponse.getStatusLine().getStatusCode();
 
  /* 若通讯正常 */
        if (responseStatus == HttpStatus.SC_OK) {
   /* 获取相应的消息实体 */
            HttpEntity responsehttpEntity = httpResponse.getEntity();
 
   /* 读取utf-8格式的返回内容 */
            returnContent = getResponseData(responsehttpEntity);

            logger.error("", returnContent);
        }
 
  /* 关闭响应实体 */
        httpResponse.close();
 
  /* 关闭httpClient连接 */
        closeHttpClient();

        long endTime = System.currentTimeMillis();
 
  /* post耗时时间 */
        logger.error(String.format("===== post cost time =[%d] =====", endTime
                - beginTime));

        return returnContent;
    }


    /**
     * get通讯
     *
     * @throws IOException
     * @throws ClientProtocolException
     */
    public Object httpGet(String url) throws ClientProtocolException,
            IOException {
        long beginTime = System.currentTimeMillis();
 
  /* 返回内容 */
        Object returnContent = null;
 
  /* 创建HttpGet */
        HttpGet httpGet = new HttpGet(url);
 
  /* 执行请求 */
        CloseableHttpResponse httpResponse = (CloseableHttpResponse) getHttpClient()
                .execute(httpGet);

        // 获取响应状态码
        int statusCode = httpResponse.getStatusLine().getStatusCode();
 
  /* 通讯正常 */
        if (statusCode == HttpStatus.SC_OK) {
   /* 获得响应的消息实体 */
            HttpEntity responseHttpEntity = httpResponse.getEntity();
 
   /* 读取utf-8格式的返回内容 */
            returnContent = getResponseData((HttpEntity) responseHttpEntity);

            logger.error("", returnContent);
        }
 
  /* 关闭响应实体 */
        httpResponse.close();
 
  /* 关闭httpClient连接 */
        closeHttpClient();

        long endTime = System.currentTimeMillis();
 
  /* post耗时时间 */
        logger.error(String.format("===== post cost time =[%d] =====", endTime
                - beginTime));

        return returnContent;
    }

    /* 拼接Post传输参数,并设定为utf-8,只支持拼接String的字符，不支持流模式 */
    private UrlEncodedFormEntity formatData(HashMap<String, String> postMap)
            throws UnsupportedEncodingException {
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();

        if (postMap != null && !postMap.isEmpty()) {
            Iterator<Map.Entry<String, String>> iterator = postMap.entrySet()
                    .iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, String> map = iterator.next();
                String keyName = (String) map.getKey();
                String keyValue = (String) map.getValue();

                nvps.add(new BasicNameValuePair(keyName, keyValue));
            }
        }

        UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(nvps, "UTF-8");

        return uefEntity;
    }

    /* 判断返回的http实体是字符串还是流，还是文件 */
    private Object getResponseData(HttpEntity responseHttpEntity)
            throws ParseException, IOException {
        if (responseHttpEntity != null) {
            Header contentType = responseHttpEntity.getContentType();

            if (contentType != null) {
    /* 若是数据流 */
                if (contentType.getValue().indexOf("application/octet-stream") >= 0) {
                    isString = false;
                    InputStream inputStream = responseHttpEntity.getContent();
     
     /*数据流处理*/
                    File file = doInputStream(inputStream);
                    EntityUtils.consume(responseHttpEntity);

                    return file;
                } else {
     /* 对返回的内容进行编码 */
                    String responseContent = EntityUtils.toString(
                            responseHttpEntity, "UTF-8");
                    EntityUtils.consume(responseHttpEntity);

                    return responseContent;
                }
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    /*返回数据流处理*/
    private File doInputStream(InputStream inputStream) throws IOException {
        int size = 0;
        byte[] buffer = new byte[4096];

        if (fileUrl != null && fileUrl.length() > 0) {
            File file = new File(fileUrl);
            FileOutputStream fileOutputStream = new FileOutputStream(file);


            while ((size = inputStream.read(buffer)) != -1) {
                fileOutputStream.write(buffer, 0, size);
            }

            fileOutputStream.close();
            inputStream.close();

            return file;
        } else {
            while ((size = inputStream.read(buffer)) != -1) {
            }
            inputStream.close();
            return null;
        }
    }

    /*关闭客户端*/
    private void closeHttpClient() throws IOException {
        //httpClient.close();
    }

    /*设定文件地址*/
    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    /*判断返回的是字符串还是数据流*/
    public boolean isString() {
        return this.isString;
    }
}
 
