package com.sunghyuki.demospringsecurity.account;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class AccountControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private AccountService accountService;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(wac)
                .apply(springSecurity())
                .build();
    }

    @Test
    @WithAnonymousUser
    public void index_anonymous() throws Exception {
        mockMvc.perform(get("/"))
               .andDo(print())
               .andExpect(status().isOk());
    }

    @Test
    @WithUser
    public void index_user() throws Exception {
        mockMvc.perform(get("/"))
               .andDo(print())
               .andExpect(status().isOk());
    }

    @Test
    @WithUser
    public void admin_user() throws Exception {
        mockMvc.perform(get("/admin"))
               .andDo(print())
               .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "admin", roles ="ADMIN")
    public void admin_admin() throws Exception {
        mockMvc.perform(get("/admin"))
               .andDo(print())
               .andExpect(status().isOk());
    }

    @Test
    @Transactional
    public void login_success() throws Exception {
        String username = "sunghyuki";
        String password = "123";
        Account user = this.createUser(username, password);
        mockMvc.perform(formLogin().user(user.getUsername()).password(password))
               .andExpect(authenticated());
    }

    @Test
    @Transactional
    public void login_success2() throws Exception {
        String username = "sunghyuki";
        String password = "123";
        Account user = this.createUser(username, password);
        mockMvc.perform(formLogin().user(user.getUsername()).password(password))
               .andExpect(authenticated());
    }

    @Test
    @Transactional
    public void login_fail() throws Exception {
        String username = "sunghyuki";
        String password = "123";
        Account user = this.createUser(username, password);
        mockMvc.perform(formLogin().user(user.getUsername()).password("12345"))
               .andExpect(unauthenticated());
    }

    private Account createUser(String username, String password) {
        Account account = new Account();
        account.setUsername(username);
        account.setPassword(password);
        account.setRole("USER");
        return accountService.createNew(account);
    }
}