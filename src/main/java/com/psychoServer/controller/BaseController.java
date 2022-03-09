package com.psychoServer.controller;

import com.psychoServer.entity.Account;
import com.psychoServer.request.OrderRequest;
import com.psychoServer.service.UserService;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class BaseController {
    @Value("${celebritiesGathering.upload.file.path}")
    String rootPath;

    public static final String Integer_MAX_VALUE = "" + Integer.MAX_VALUE;
    @Autowired
    protected UserService userService;

    public String getCurrentUsername() {
        String username;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }

        return username;
    }

    public Account getCurrentAccount() {
        String loginName = getCurrentUsername();
        Optional<Account> account = Optional.ofNullable(userService.getByLoginName(loginName));
        Account fakeAccount = userService.getById(0L);
        return account.orElse(fakeAccount);
    }

    public List<Sort.Order> getOrder(List<Field> fields, List<OrderRequest> order) {
        List<String> properties = fields.stream().map(Field::getName).collect(Collectors.toList());
        List<Sort.Order> orders = Lists.newArrayList();
        for (OrderRequest orderRequest : order) {
            if (!properties.contains(orderRequest.getProperty())) {
                continue;
            }
            orders.add(new Sort.Order(orderRequest.getDirection(), orderRequest.getProperty()));
        }
        return orders;
    }
}
