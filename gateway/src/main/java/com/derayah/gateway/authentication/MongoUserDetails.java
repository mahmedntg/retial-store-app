package com.derayah.gateway.authentication;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * MongoUserDetails.
 *
 * @author : Mo Sayed
 * @since : 5/24/2022
 */
@JsonDeserialize(as = MongoUserDetails.class)
public class MongoUserDetails implements UserDetails {

    private String username;
    private String password;
    private Integer active;
    private boolean isLocked;
    private boolean isExpired;
    private boolean isEnabled;

    private String registerDate;
    private List<GrantedAuthority> grantedAuthorities;

    public MongoUserDetails(User user) {
        List<GrantedAuthority> authorities = user.getRoles().stream()
            .map(role -> new SimpleGrantedAuthority(role.toString())).collect(Collectors.toList());
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.active = 1;
        this.isLocked = false;
        this.isExpired = false;
        this.isEnabled = true;
        this.registerDate = user.getRegisterDate();
        this.grantedAuthorities = authorities;
    }

    public MongoUserDetails(String username, String[] authorities) {
        this.username = username;
        this.grantedAuthorities = AuthorityUtils.createAuthorityList(authorities);
    }

    public MongoUserDetails() {
        super();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return active == 1;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !isLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !isExpired;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }

    public String getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(final String registerDate) {
        this.registerDate = registerDate;
    }
}
