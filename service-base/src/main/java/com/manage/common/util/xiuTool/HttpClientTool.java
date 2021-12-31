package com.manage.common.util.xiuTool;


import lombok.extern.slf4j.Slf4j;
import org.apache.http.*;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

import javax.net.ssl.SSLContext;
import java.nio.charset.StandardCharsets;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author zhaodaowen
 * @see <a href="http://www.roadjava.com">乐之者java</a>
 */
@Slf4j
@Component
public class HttpClientTool {
    //创建一个安全的可以绕过SSL认证规定好的httpClient工厂，在使用时直接拿来创建httpClient就可以
    private static final HttpClientBuilder httpClientBuilder = HttpClients.custom();

    /**
     * 在这个里面对httpClient工厂进行绕过不安全的https请求的证书验证和初始化一些参数
     */
    static {
        /*
         一、绕过不安全的https请求的证书验证
         */
        Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.INSTANCE)
                .register("https", trustHttpsCertificates())
                .build();
        /*
          二、创建连接池
         */
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager(registry);
        cm.setMaxTotal(50); // 连接池最大有50个连接,<=20
        /*
            roadjava.com域名/ip+port 就是一个路由，
            http://www.roadjava.com/s/spsb/beanvalidation/
            http://www.roadjava.com/s/1.html
            https://www.baidu.com/一个域名，又是一个新的路由
         */
        cm.setDefaultMaxPerRoute(50); // 每个路由默认有多少连接,<=2
        /*
            // 连接池的最大连接数
            System.out.println("cm.getMaxTotal():"+cm.getMaxTotal());
            // 每一个路由的最大连接数
            System.out.println("cm.getDefaultMaxPerRoute():"+cm.getDefaultMaxPerRoute());
            PoolStats totalStats = cm.getTotalStats();
            // 连接池的最大连接数
            System.out.println("totalStats.getMax():"+totalStats.getMax());
            // 连接池里面有多少连接是被占用了
            System.out.println("totalStats.getLeased():"+totalStats.getLeased());
            // 连接池里面有多少连接是可用的
            System.out.println("totalStats.getAvailable():"+totalStats.getAvailable());
         */
        httpClientBuilder.setConnectionManager(cm);
        /*
        三、设置请求默认配置
         */
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(5000)
                .setSocketTimeout(3000)
                .setConnectionRequestTimeout(5000)
                .build();
        httpClientBuilder.setDefaultRequestConfig(requestConfig);
        /*
        四、设置默认的一些header
         */
        List<Header> defaultHeaders = new ArrayList<>();
        BasicHeader userAgentHeader = new BasicHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.141 Safari/537.36");
        defaultHeaders.add(userAgentHeader);
        httpClientBuilder.setDefaultHeaders(defaultHeaders);
    }

    /**
     * 构造安全连接工厂
     * @return SSLConnectionSocketFactory
     */
    private static ConnectionSocketFactory trustHttpsCertificates() {
        SSLContextBuilder sslContextBuilder = new SSLContextBuilder();
        try {
            sslContextBuilder.loadTrustMaterial(null, new TrustStrategy() {
                // 判断是否信任url
                @Override
                public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                    return true;
                }
            });
            SSLContext sslContext = sslContextBuilder.build();
            return new SSLConnectionSocketFactory(sslContext,
                    new String[]{"SSLv2Hello","SSLv3","TLSv1","TLSv1.1","TLSv1.2"}
                    ,null, NoopHostnameVerifier.INSTANCE);
        } catch (Exception e) {
            log.error("构造安全连接工厂失败",e);
            throw new RuntimeException("构造安全连接工厂失败");
        }
    }


    //对于get请求，如果有参数，需要使用URLEncoder对参数进行编码，之后再拼接到url中，
    // 因为在请求时，请求中会自动对url进行编码，防止请求中url因为参数乱码，导致请求失败
    //passwordParam = URLEncoder.encode(passwordParam,StandardCharsets.UTF_8.name());
    //urlStr = "http://localhost:8899/httpclient-demo/test1?userName=aaaa&password="+passwordParam;
    /**
     * 可以用来访问https的Get请求，获取String类型的方法，根据传入的url和规定的headers发起请求
     * 前提：访问的url中如果没有参数可以直接传入，
     * 如果有参数需要使用URLEncoder中的encode进行编码后在对url进行拼接
     * 默认使用utf-8格式
     * @param url 访问地址
     * @param headers 如果有需要传入规定的请求头信息，没有不传
     * @return 返回字符串
     */
    public static String httpsDoGet(String url,Map<String , String > headers){
        //1、先用处理过的httpClientBuilder工厂，创建绕过不安全https验证并初始化一些参数的httpClient对象
        CloseableHttpClient closeableHttpClient = httpClientBuilder.build();

        //创建httpGet请求对象
        HttpGet httpGet = new HttpGet(url);

        //如果需要自定义请求头，在这里设置请求头
        if (headers != null){
            Set<Map.Entry<String, String>> entries = headers.entrySet();
            for (Map.Entry<String,String> map: entries){
                httpGet.addHeader(map.getKey(),map.getValue());
            }
        }

        //设置执行结果的响应
        CloseableHttpResponse response = null;
        String result = null;
        try{
            //执行发送get请求，并返回结果
            response = closeableHttpClient.execute(httpGet);

            //获取结果的返回状态行
            StatusLine status = response.getStatusLine();
            //判断执行结果成功还是失败
            if (HttpStatus.SC_OK == status.getStatusCode()){
                //执行成功，对返回数据进行处理
                HttpEntity entity = response.getEntity();

                //这里使用EntityUtils工具类对执行结果HttpEntity进行出来，
                // 对于一般的都用EntityUtils.toString(entity, StandardCharsets.UTF_8)转换成String类型，
                // 像文件类型的使用EntityUtils.toByteArray(HttpEntity entity)转换成byte型用来处理
                result = EntityUtils.toString(entity, StandardCharsets.UTF_8);


            }

        }catch (Exception e){
            log.error("*****httpsDoGet*****error-----message:",e.getMessage());
            return null;
        }finally {
            HttpClientUtils.closeQuietly(response);
        }
        return result;
    }


    /**
     * 发送get请求
     * @param url 请求url,参数需经过URLEncode编码处理
     * @param headers 自定义请求头
     * @return 返回结果
     */
    public static String executeGet(String url, Map<String,String> headers) {
        CloseableHttpClient closeableHttpClient = httpClientBuilder.build();
        // 构造httpGet请求对象
        HttpGet httpGet = new HttpGet(url);
        // 自定义请求头设置
        if (headers != null) {
            Set<Map.Entry<String, String>> entries = headers.entrySet();
            for (Map.Entry<String, String> entry : entries) {
                httpGet.addHeader(new BasicHeader(entry.getKey(),entry.getValue()));
            }
        }
        // 可关闭的响应
        CloseableHttpResponse response = null;
        try {
            log.info("prepare to execute url:{}",url);
            response = closeableHttpClient.execute(httpGet);
            StatusLine statusLine = response.getStatusLine();
            if (HttpStatus.SC_OK == statusLine.getStatusCode()) {
                HttpEntity entity = response.getEntity();
                return EntityUtils.toString(entity, StandardCharsets.UTF_8);
            }else {
                log.error("响应失败,响应码:"+statusLine.getStatusCode());
            }
        }catch (Exception e) {
            log.error("executeGet error,url:{}",url,e);
        } finally {
            // 调用EntityUtils.consume(response.getEntity());来关闭response
            HttpClientUtils.closeQuietly(response);
        }
        return null;
    }

    /**
     * 发送表单类型的post请求
     * @param url 要请求的url
     * @param list 参数列表
     * @param headers 自定义头
     * @return 返回结果
     */
    public static String postForm(String url, List<NameValuePair> list, Map<String,String> headers) {
        CloseableHttpClient closeableHttpClient = httpClientBuilder.build();
        HttpPost httpPost = new HttpPost(url);
        if (headers != null) {
            Set<Map.Entry<String, String>> entries = headers.entrySet();
            for (Map.Entry<String, String> entry : entries) {
                httpPost.addHeader(new BasicHeader(entry.getKey(),entry.getValue()));
            }
        }
        // 确保请求头一定是form类型
        httpPost.addHeader("Content-Type","application/x-www-form-urlencoded; charset=utf-8");
        // 给post对象设置参数
        UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(list,Consts.UTF_8);
        httpPost.setEntity(formEntity);
        // 响应处理
        CloseableHttpResponse response = null;
        try {
            log.info("prepare to execute url:{}",httpPost.getRequestLine());
            response = closeableHttpClient.execute(httpPost);
            StatusLine statusLine = response.getStatusLine();
            if (HttpStatus.SC_OK == statusLine.getStatusCode()) {
                HttpEntity entity = response.getEntity();
                return EntityUtils.toString(entity, StandardCharsets.UTF_8);
            }else {
                log.error("响应失败,响应码:"+statusLine.getStatusCode());
            }
        }catch (Exception e) {
            log.error("executeGet error,url:{}",url,e);
        } finally {
            HttpClientUtils.closeQuietly(response);
        }
        return null;
    }

    /**
     * 发送json类型的post请求
     * @param url 请求url
     * @param body json字符串
     * @param headers 自定义header
     * @return 返回结果
     */
    public static String postJson(String url, String body, Map<String,String> headers) {
        CloseableHttpClient closeableHttpClient = httpClientBuilder.build();
        HttpPost httpPost = new HttpPost(url);
        // 设置请求头
        if (headers != null) {
            Set<Map.Entry<String, String>> entries = headers.entrySet();
            for (Map.Entry<String, String> entry : entries) {
                httpPost.addHeader(new BasicHeader(entry.getKey(),entry.getValue()));
            }
        }
        // 确保请求头是json类型
        httpPost.addHeader("Content-Type","application/json; charset=utf-8");
        /*
        设置请求体
         */
        StringEntity jsonEntity = new StringEntity(body, Consts.UTF_8);
        jsonEntity.setContentType("application/json; charset=utf-8");
        jsonEntity.setContentEncoding(Consts.UTF_8.name());
        httpPost.setEntity(jsonEntity);

        CloseableHttpResponse response = null;
        try {
            response = closeableHttpClient.execute(httpPost);
            StatusLine statusLine = response.getStatusLine();
            if (HttpStatus.SC_OK == statusLine.getStatusCode()) {
                HttpEntity entity = response.getEntity();
                return EntityUtils.toString(entity, StandardCharsets.UTF_8);
            }else {
                log.error("响应失败,响应码:"+statusLine.getStatusCode());
            }
        }catch (Exception e) {
            log.error("executeGet error,url:{}",url,e);
        } finally {
            HttpClientUtils.closeQuietly(response);
        }
        return null;
    }
}
