package com.youcash.uc.service.impl;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication()
@ImportResource({ "classpath:dubbo/*.xml" })
@MapperScan("com.youcash.uc.mapper")
public class MainConfig {


    /**
     * HTTP最大连接数
     */
    @Value("${http.maxTotal}")
    private int maxTotal;
    /**
     * HTTP最大路由连接数
     */
    @Value("${http.defaultMaxPerRoute}")
    private int defaultMaxPerRoute;
    /**
     * 建立连接的超时时间
     */
    @Value("${http.connectTimeout}")
    private int connectTimeout;

    /**
     * 传递数据的超时时间
     */
    @Value("${http.readTimeout}")
    private int readTimeout;

    public static void main(String[] args) {
        SpringApplication.run(MainConfig.class, args);  
        try {  
            System.in.read();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }

    @Bean
    public RestTemplate getRestTemplate(RestTemplateBuilder builder) {
        PoolingHttpClientConnectionManager poolingHttpClientConnectionManager = new PoolingHttpClientConnectionManager();
        poolingHttpClientConnectionManager.setMaxTotal(maxTotal);
        poolingHttpClientConnectionManager.setDefaultMaxPerRoute(defaultMaxPerRoute);
        HttpClient httpClient = HttpClientBuilder.create().setConnectionManager(poolingHttpClientConnectionManager)
                .build();
        return builder.requestFactory(new HttpComponentsClientHttpRequestFactory(httpClient))
                .setConnectTimeout(connectTimeout).setReadTimeout(readTimeout).build();
    }
}
