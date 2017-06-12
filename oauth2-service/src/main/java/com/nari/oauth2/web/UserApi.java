package com.nari.oauth2.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * This guy is busy, nothing left.
 *
 * @author John Zhang
 */
@RestController
public class UserApi {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserApi.class);

    @Autowired
    private JdbcUserDetailsManager jdbcUserDetailsManager;

    @GetMapping("/user")
    public Principal user(Principal principal) {
        System.out.println("principal.getClass() = " + principal.getClass());
        return principal;
    }

    @PostMapping("/user")
    public ResponseEntity saveUser(@RequestBody UserInfo userInfo) {
        if (StringUtils.isEmpty(userInfo.getUsername()) || StringUtils.isEmpty(userInfo.getPassword())) {
            return ResponseEntity.badRequest().build();
        }
        try {
            UserDetails userInDb = jdbcUserDetailsManager.loadUserByUsername(userInfo.getUsername());
            if (userInDb != null) {
                Map<String, String> result = Collections.singletonMap("error", "用户名已存在");
                return ResponseEntity.badRequest().body(result);
            }
        } catch (UsernameNotFoundException e) {
            LOGGER.info("用户名：{}可以使用", userInfo.getUsername());
        }
        List<GrantedAuthority> authorityList = AuthorityUtils.createAuthorityList("ROLE_MEMBER");

        String encodePwd = new BCryptPasswordEncoder().encode(userInfo.getPassword());
        User userDetails = new User(userInfo.getUsername(), encodePwd, authorityList);
        jdbcUserDetailsManager.createUser(userDetails);
        return ResponseEntity.ok(userDetails);
    }

}
class UserInfo {
    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}