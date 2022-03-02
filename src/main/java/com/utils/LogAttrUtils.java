package com.utils;

import com.service.UserService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class LogAttrUtils {
    private static final ThreadLocal<Map<String, String>> attrs = new ThreadLocal<>();
    private static final Log LOGGER = LogFactory.getLog(LogAttrUtils.class);

    @Autowired
    private UserService userService;

    public static void initAttrs(HttpServletRequest request) {
        String sessionId = request.getSession().getId();
        Map<String, String> attrsMap = new HashMap<>();
        try {
            Principal principal = request.getUserPrincipal();
            if (principal != null) {
                attrsMap.put("userName", principal.getName());
            } else {
                attrsMap.put("userName", "@@@@ none @@@@@");
            }
        }catch (Exception e){
            LOGGER.warn("get currentUser userName warn",e);
        }
        attrsMap.put("sessionId", sessionId);
        attrs.set(attrsMap);
    }
    public static List<String> getAttrs() {
        Map<String, String> logAttrs = attrs.get();
        List<String> logAttrsStr = logAttrs.entrySet().stream().map(stringStringEntry ->
                stringStringEntry.getKey() + ":" + stringStringEntry.getValue()
        ).collect(Collectors.toList());
        return logAttrsStr;
    }
}