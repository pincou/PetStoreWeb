package org.csu.mypetstore.controller;

import jakarta.servlet.Filter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class FilterConfig {
    @Bean
    public FilterRegistrationBean RegisterTest1(){

        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.setFilter(new Myfilter());//注册自定义过滤器
        bean.setName("filter");//过滤器名称
        bean.addUrlPatterns("/*");//过滤所有路径
        bean.setOrder(1);//优先级，最顶级
        return bean;
    }

}
