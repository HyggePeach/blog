package com.wangjunblog.common.config;

import com.wangjunblog.common.filter.TokenFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author wangjun
 * @date 2024/7/17 21:57
 */
@Configuration
public class FilterConfig {
    @Bean
    public FilterRegistrationBean<TokenFilter> tokenFilter() {
        FilterRegistrationBean<TokenFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new TokenFilter());
        registrationBean.addUrlPatterns("/api/auth/me","/api/posts/*"); // 设置过滤器应用的 URL 模式
        return registrationBean;
    }
}
