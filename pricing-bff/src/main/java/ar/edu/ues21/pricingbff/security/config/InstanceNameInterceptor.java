package ar.edu.ues21.pricingbff.security.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class InstanceNameInterceptor implements HandlerInterceptor {

    @Value("${service.instance}")
    private String name;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        response.addHeader("X-WEB-BACKEND-INSTANCE", name);
        return true;
    }

}