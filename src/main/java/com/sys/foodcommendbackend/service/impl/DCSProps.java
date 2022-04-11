package com.sys.foodcommendbackend.service.impl;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties(prefix = "dcs")

public class DCSProps {
    private List<String> anonymous;

    private String jwtkey;

    public List<String> getAnonymous() {
        return anonymous;
    }

    public void setAnonymous(List<String> anonymous) {
        this.anonymous = anonymous;
    }

    public String getJwtkey() {
        return jwtkey;
    }

    public void setJwtkey(String jwtkey) {
        this.jwtkey = jwtkey;
    }
}
