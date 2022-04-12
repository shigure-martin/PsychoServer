package com.psychoServer.constants;

public enum RoleType {
    customer("用户"), counselor("咨询师"), supervisor("督导"), admin("管理员");
    public String role;

    RoleType(String r) {
        this.role = r;
    }
}
