package com.sys.foodcommendbackend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hznu.machine.crypto.jwt.entity.Claims;
import com.hznu.machine.crypto.jwt.entity.Jwts;
import com.sys.foodcommendbackend.entity.User;
import com.sys.foodcommendbackend.mapper.UserMapper;
import com.sys.foodcommendbackend.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Hex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author LuoRuiJie
 * @since 2022-04-09
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private DCSProps dcsProps;


    private void checkPWdRule(String password) {
        Assert.hasLength(password, "密码为空");
        Assert.isTrue(password.length() >= 6, "密码必须大于或等于6位");
    }

    /**
     * @Author LuoRuiJie
     * @Description //TODO 判断用户账号是否重复
     * @Date
     * @Param String
     * @return boolean
     **/
    @Override
    public boolean checkAccountUnique(String account) {
        User user = this.getOne(new QueryWrapper<User>().eq("account", account).last("LIMIT 1"));
        return user == null;
    }


    @Override
    public User saveWithHashPassword(User record) {
        String Password = record.getPassword();
        checkPWdRule(Password);
        try {
            String[] pwdAndSalt = this.hashPassword(Password);
            record.setPassword(pwdAndSalt[0]);
            record.setSalt(pwdAndSalt[1]);
            record.setType(record.getType());
            boolean res = this.saveOrUpdate(record);
            Assert.isTrue(res, "用户保存失败");
            return record;
        } catch (NoSuchAlgorithmException | IOException e) {
            // 内存操作不会发生异常，保险起见还是抛出运行时异常
            throw new RuntimeException(e);
        }
    }

    @Override
    public String createToken(User userInfo) {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, 60);
        Date expiresDate = calendar.getTime();

        Claims claims = Jwts.claims();
        claims.put("id", userInfo.getId());
        claims.setIssuedAt(date);
        claims.setExpiration(expiresDate);
        String jwt = Jwts.builder().setClaims(claims).signWith(Hex.decode(dcsProps.getJwtkey())).compact();
        return jwt;
    }

    private String[] hashPassword(String password) throws NoSuchAlgorithmException, IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        MessageDigest sm3Digest = MessageDigest.getInstance("SM3", new BouncyCastleProvider());
        SecureRandom secureRandom = new SecureRandom();
        byte[] saltBin = new byte[128];
        //生成一个128Byte长的随机数
        secureRandom.nextBytes(saltBin);
        //将其Hash为256Byte的字节串
        saltBin = sm3Digest.digest(saltBin);
        sm3Digest.reset();
        String salt = Hex.toHexString(saltBin);
        /*
         * 向缓存依次写入
         * 口令原文的字节串
         * 盐值字节串
         */
        buffer.write(password.getBytes(StandardCharsets.UTF_8));
        buffer.write(saltBin);
        buffer.close();

        byte[] passwordBin = sm3Digest.digest(buffer.toByteArray());
        password = Hex.toHexString(passwordBin);
        return new String[]{password, salt};
    }

    @Override
    public User findUser(User user) {
        return this.getOne(new QueryWrapper<User>().eq("account", user.getAccount()));
    }

    @Override
    public boolean checkPassword(User record, String password) {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        //解码
        byte[] targetPwdBin = Hex.decode(record.getPassword());
        byte[] targetSaltBin = Hex.decode(record.getSalt());

        try {
            buffer.write(password.getBytes(StandardCharsets.UTF_8));
            buffer.write(targetSaltBin);
            buffer.close();

            MessageDigest sm3Digest = MessageDigest.getInstance("SM3", new BouncyCastleProvider());
            byte[] cmpPwdHash = sm3Digest.digest(buffer.toByteArray());
            return Arrays.equals(cmpPwdHash, targetPwdBin);
        } catch (IOException | NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }


}
