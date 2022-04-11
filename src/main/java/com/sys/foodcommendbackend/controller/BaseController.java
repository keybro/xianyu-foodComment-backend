package com.sys.foodcommendbackend.controller;

import com.hznu.machine.crypto.jwt.entity.Claims;
import com.hznu.machine.crypto.jwt.entity.Jwts;
import com.sys.foodcommendbackend.service.impl.DCSProps;
import com.sys.foodcommendbackend.service.impl.DCSProps;
import org.bouncycastle.util.encoders.Hex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class BaseController {

    protected HttpServletRequest request;

    protected HttpServletResponse response;

    protected HttpSession session;

    @Autowired
    private DCSProps dcsProps;

    @ModelAttribute
    public void setBaseBizController(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
        this.session = request.getSession();
    }

    protected String getSenderId() {
        String token = request.getHeader("token");
        Claims claims = Jwts.parser().verifyJwt(token, Hex.decode(dcsProps.getJwtkey()));
        return claims.get("id").toString();
    }
}
