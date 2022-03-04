package com.service;

import com.constants.RoleType;
import com.entity.Account;
import com.google.common.base.Strings;
import com.repository.AccountRepository;
import com.request.OrderRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService extends BasicService<Account, Long> implements UserDetailsService {
    private AccountRepository userRepo;

    UserService(AccountRepository userRepo) {
        super(userRepo);
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Account account = getByLoginName(s);
        if (account == null) {
            throw new BadCredentialsException("登录名[" + s + "]不存在!");
        }
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();

        SimpleGrantedAuthority grantedAuthority = new SimpleGrantedAuthority(account.getAuthority());
        grantedAuthorities.add(grantedAuthority);
        return new User(s, account.getPassword(), grantedAuthorities);
    }

    public Account getByLoginName(String loginName) {
        Account account = userRepo.findByLoginName(loginName);
        return account;
    }

    public Page<Account> getAccountInfos(String loginName, RoleType roleType, int page, int size, List<OrderRequest> order) {
        Pageable pageable = getPageableBy(order, new PageRequest(page, size), new Account());
        RoleType[] roleTypes = getRoleTypes(roleType);
        Page<Account> result;
        if (!Strings.isNullOrEmpty(loginName)) {
            String loginNameLike = "%" + loginName + "%";
            result = userRepo.findByLoginNameIsLikeAndRoleTypeInAndDeleted(loginNameLike, roleTypes, false, pageable);
        } else {
            result = userRepo.findByRoleTypeInAndDeleted(roleTypes, false, pageable);
        }
        return result;
    }

    private RoleType[] getRoleTypes(RoleType roleType) {
        RoleType[] roleTypes;
        if (roleType == null) {
            roleTypes = new RoleType[]{RoleType.customer, RoleType.counselor, RoleType.supervisor};
        } else {
            roleTypes = new RoleType[]{roleType};
        }
        return roleTypes;
    }
}
