package com.constants;

public enum RoleType {
    customer_service("客服"),expert("专家"),
    finance("财务"),producer("生产商"),customer("用户"),superAdmin("管理员");
    public String role;

    RoleType(String r) {
        this.role = r;
    }
}
