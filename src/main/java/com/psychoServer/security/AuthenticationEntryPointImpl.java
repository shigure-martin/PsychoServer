package com.psychoServer.security;

import com.psychoServer.response.ErrorResponse;
import com.psychoServer.utils.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    public static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationEntryPointImpl.class);

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException,
            ServletException {
        LOGGER.info(e.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), Integer.toString(HttpStatus.UNAUTHORIZED.value()));
        response.setStatus(401);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        PrintWriter writer = response.getWriter();
        writer.write(JsonUtil.toJson(errorResponse));
        writer.flush();
        writer.close();
    }
}

