package com.psychoServer.repository;

import com.psychoServer.entity.Account;
import com.psychoServer.constants.RoleType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface AccountRepository extends PagingAndSortingRepository<Account, Long> {
    Page<Account> findByDeleted(boolean deleted, Pageable pageable);
    Page<Account> findByLoginNameIsLikeAndRoleTypeInAndDeleted(String loginName, RoleType[] roleTypes, boolean deleted, Pageable pageable);
    Page<Account> findByRoleTypeInAndDeleted(RoleType[] roleTypes,boolean deleted, Pageable pageable);
    Account findByLoginName(String loginName);
}
