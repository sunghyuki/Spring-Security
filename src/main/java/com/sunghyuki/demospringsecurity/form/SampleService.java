package com.sunghyuki.demospringsecurity.form;

import com.sunghyuki.demospringsecurity.account.Account;
import com.sunghyuki.demospringsecurity.account.AccountContext;
import org.springframework.stereotype.Service;

@Service
public class SampleService {

    public void dashboard() {
        Account account = AccountContext.getAccount();
        System.out.println("================");
        System.out.println(account.getUsername());
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        Object principal = authentication.getPrincipal();
//        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
//        Object credentials = authentication.getCredentials();
//        authentication.isAuthenticated();
    }
}
