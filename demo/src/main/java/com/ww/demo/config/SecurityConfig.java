package com.ww.demo.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.OncePerRequestFilter;

@Configuration
public class SecurityConfig {

  @Value(value = "${api.key:aaface6d-f3a6-497c-8e16-8a5367cae05f}")
  private String API_KEY;

  @Bean
  public FilterRegistrationBean<SecurityFilter> versioningFilter() {

    FilterRegistrationBean<SecurityFilter> bean = new FilterRegistrationBean<SecurityFilter>();

    bean.setFilter(new SecurityFilter());
 
    bean.addUrlPatterns("/api/*");
  

    return bean;
  }

  private class SecurityFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

      String apiKey = StringUtils.isNotEmpty(request.getHeader("api_key")) ? request.getHeader("api_key") : request.getParameter("api_key");

      if(StringUtils.isEmpty(apiKey) || !API_KEY.equals(apiKey)) {
        
        response.sendError(HttpServletResponse.SC_FORBIDDEN, "Invalid token");
        return;
      }

      filterChain.doFilter(request, response);
    }
  }
}