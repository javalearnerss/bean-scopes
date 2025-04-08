package com.learn.bean.scopes.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import java.util.UUID;

@Component
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserContext {

    private static Logger logger = LoggerFactory.getLogger(UserContext.class);

    private String id;
    private String userID;
    private String name;

    public UserContext() {
        this.id = UUID.randomUUID().toString();
        logger.info("UserContext bean is created, Bean ID : {}", id);
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserID() {
        return userID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "UserContext{" +
                "Bean ID='" + id + '\'' +
                ", User ID='" + userID + '\'' +
                ", User Name='" + name + '\'' +
                '}';
    }
}