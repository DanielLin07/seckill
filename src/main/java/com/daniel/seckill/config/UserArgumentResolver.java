package com.daniel.seckill.config;

import com.daniel.seckill.model.User;
import com.daniel.seckill.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * User的方法参数处理器，当Controller方法中含有User类型的参数时则会执行
 *
 * @author DanielLin07
 * @date 2019/1/19 17:21
 */
@Configuration
public class UserArgumentResolver implements HandlerMethodArgumentResolver {

    @Autowired
    private UserService userService;

    /**
     * 当方法参数类型有User类型才会执行resolveArgument
     *
     * @param methodParameter 方法参数
     * @return 如果是User类型则返回true
     */
    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        Class<?> clazz = methodParameter.getParameterType();
        return clazz == User.class;
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        HttpServletRequest request = nativeWebRequest.getNativeRequest(HttpServletRequest.class);
        String token = getCookieValue(request, "token");
        return StringUtils.isNotBlank(token) ? userService.queryByToken(token) : null;
    }

    private String getCookieValue(HttpServletRequest request, String cookieKey) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(cookieKey)) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}
