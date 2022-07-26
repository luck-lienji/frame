package com.example.demo.commons.configs.security.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * @author liwenji
 * @ClassName UserInfo
 * @Description TODO，用户基础信息
 * @date 2022/5/27 16:26
 * @Version 1.0
 */
public class UserInfo implements UserDetails{
    private Long userId;
    private String account;
    private String username;
    private String password;
    private String email;
    private String phone;
    private ZonedDateTime birthday;
    private String userStatus;
    private Integer deleteFlag;
    private Integer expired;
    private Integer disabled;
    private List<Role> roles;
    private Set<GrantedAuthority> authorities;

    public Long getUserId () {
        return userId;
    }

    public void setUserId (Long userId) {
        this.userId = userId;
    }

    public String getAccount () {
        return account;
    }

    public void setAccount (String account) {
        this.account = account;
    }


    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword (String password) {
        this.password = password;
    }

    public String getEmail () {
        return email;
    }

    public void setEmail (String email) {
        this.email = email;
    }

    public String getPhone () {
        return phone;
    }

    public void setPhone (String phone) {
        this.phone = phone;
    }

    public ZonedDateTime getBirthday () {
        return birthday;
    }

    public void setBirthday (ZonedDateTime birthday) {
        this.birthday = birthday;
    }

    public String getUserStatus () {
        return userStatus;
    }

    public void setUserStatus (String userStatus) {
        this.userStatus = userStatus;
    }

    public Integer getDeleteFlag () {
        return deleteFlag;
    }

    public void setDeleteFlag (Integer deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public Integer getExpired () {
        return expired;
    }

    public void setExpired (Integer expired) {
        this.expired = expired;
    }

    public Integer getDisabled () {
        return disabled;
    }

    public void setDisabled (Integer disabled) {
        this.disabled = disabled;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public void setAuthorities (Set<GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities () {
        return this.authorities;
    }

    @Override
    public String getPassword () {
        return this.password;
    }

    @Override
    public String getUsername () {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired () {
        return true;
    }

    @Override
    public boolean isAccountNonLocked () {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired () {
        return true;
    }

    @Override
    public boolean isEnabled () {
        return true;
    }


    public UserInfo() {
    }

    public UserInfo(Long userId, String account, String username, String password, String email, String phone, ZonedDateTime birthday, String userStatus, Integer deleteFlag, Integer expired, Integer disabled, List<Role> roles, Set<GrantedAuthority> authorities) {
        this.userId = userId;
        this.account = account;
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.birthday = birthday;
        this.userStatus = userStatus;
        this.deleteFlag = deleteFlag;
        this.expired = expired;
        this.disabled = disabled;
        this.roles = roles;
        this.authorities = authorities;
    }

    @Override
    public String toString () {
        return "UserInfo{" +
                "userId=" + userId +
                ", account='" + account + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", birthday=" + birthday +
                ", userStatus='" + userStatus + '\'' +
                ", deleteFlag=" + deleteFlag +
                ", expired=" + expired +
                ", disabled=" + disabled +
                ", roles=" + roles +
                ", authorities=" + authorities +
                '}';
    }


}
