package com.psychoServer.handler;

import com.alibaba.fastjson.JSON;
import com.psychoServer.utils.LogAttrUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Aspect
@Component
public class LogAspect {

    private static final Log LOGGER = LogFactory.getLog(LogAspect.class);

    /**
     * 配置切入点,该方法无方法体,主要为方便同类中其他方法使用此处配置的切入点
     * 这句话是方法切入点
     * 1 execution (* com.my.blog.website.controller..*.*(..))
     * 2 execution ： 表示执行
     * 3 第一个*号 : 表示返回值类型， *可以是任意类型
     * 4 com.my.blog.website.controller : 代表扫描的包
     * 5 .. : 代表其底下的子包也进行拦截
     * 6 第二个*号 : 代表对哪个类进行拦截，*代表所有类
     * 7 第三个*号 : 代表方法  *代表任意方法
     * 8 (..) : 代表方法的参数有无都可以
     */
    @Pointcut("execution(* com.celebritiesGathering.controller..*.*(..))")
    public void log() {
    }


    /**
     * 前置通知等可以没有JoinPoint参数
     */
    @Before("log()")
    public void before(JoinPoint joinPoint) {
        ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attrs == null) {
            return;
        }
        HttpServletRequest request = attrs.getRequest();
        LogAttrUtils.initAttrs(request);
        List<String> logAttrs = LogAttrUtils.getAttrs();
        // 去除response，防止序列化失败
        List<Object> args = Arrays.stream(joinPoint.getArgs()).filter(t->{
            boolean res = t instanceof HttpServletResponse;
            return !res;
        }).collect(Collectors.toList());
        try {
            LOGGER.info(String.format("request: URL: %s, IP: %s, HTTP_METHOD: %s, params: %s, args: %s, logAttrs: %s, time: %s",
                    request.getServletPath(), request.getRemoteAddr(),
                    request.getMethod(),
                    JSON.toJSONString(request.getParameterMap()),
                    JSON.toJSON(args).toString(),logAttrs.toString(),
                    new Timestamp(System.currentTimeMillis())
            ));
        } catch (Exception e){
            LOGGER.info(String.format("request: URL: %s, IP: %s, HTTP_METHOD: %s, params: %s, args: %s, logAttrs: %s, time: %s",
                    request.getServletPath(), request.getRemoteAddr(),
                    request.getMethod(),
                    JSON.toJSONString(request.getParameterMap()),
                    "JSON.toJSON(args).toString()",logAttrs.toString(),
                    new Timestamp(System.currentTimeMillis())
            ));
        }

    }

    /**
     * 配置后置返回通知,使用在方法webLog()上注册的切入点
     */
    @AfterReturning(returning = "object", pointcut = "log()")
    public void after(Object object) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        if (object != null) {
            LOGGER.info(String.format("response: %s, time: %s" , JSON.toJSON(object).toString(), timestamp));
        } else {
            LOGGER.info(String.format("response: %s, time: %s" , "none", timestamp));
        }
    }
}
