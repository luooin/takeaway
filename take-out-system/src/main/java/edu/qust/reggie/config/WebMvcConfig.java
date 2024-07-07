package edu.qust.reggie.config;

import edu.qust.reggie.common.JacksonObjectMapper;
import edu.qust.reggie.interceptor.LoginInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @version 1.0
 * @description WebMVC配置文件
 */

@Slf4j
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    /** 注册处理器拦截器 */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 创建登录拦截器对象
        HandlerInterceptor interceptor = new LoginInterceptor();

        // 白名单
        List<String> patterns = new ArrayList<String>();
        patterns.add("/employee/login");
        patterns.add("/employee/logout");
        patterns.add("/backend/**");
        patterns.add("/front/**");
        patterns.add("/user/sendMsg");
        patterns.add("/user/login");


        // 通过注册工具添加拦截器
        registry.addInterceptor(interceptor).addPathPatterns("/**").excludePathPatterns(patterns);

        log.info("注册处理器拦截器");
    }

    /** 设置静态资源映射（默认只能访问static/template下的静态资源）*/
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/backend/**").addResourceLocations("classpath:/backend/");
        registry.addResourceHandler("/front/**").addResourceLocations("classpath:/front/");
        log.info("设置静态资源映射");
    }

    /** MVC 框架的扩展消息转换器 */
    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        // 创建一个消息转换器对象
        MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();
        // 设置对象转换器，底层使用 JackSon 将 Java 对象转换成 JSON
        messageConverter.setObjectMapper(new JacksonObjectMapper());
        // 将消息转换器对象追加到 MVC 框架的转换器集合中（设置最开头，优先使用）
        converters.add(0, messageConverter);

        log.info("设置扩展消息转换器");

    }

    /**
     * 解决 Druid 日志报错：discard long time none received connection:xxx
     */
    @PostConstruct
    public void setProperties(){
        System.setProperty("druid.mysql.usePingMethod","false");
    }
}
