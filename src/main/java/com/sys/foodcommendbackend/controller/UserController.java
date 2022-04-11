package com.sys.foodcommendbackend.controller;


import com.sys.foodcommendbackend.entity.User;
import com.sys.foodcommendbackend.service.UserService;
import com.sys.foodcommendbackend.tool.Resp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author LuoRuiJie
 * @since 2022-04-09
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/test")
    public String test(){
        return "部署成功!";
    }


    /**
     * @Author LuoRuiJie
     * @Description 用户注册
     * @Date
     * @Param User
     * @return Resp
     * 测通
     **/
    @PostMapping("/create")
    public Resp create(@RequestBody User user) {
        //根据需求，账号和昵称都不允许重复
        if (userService.checkAccountUnique(user.getAccount())) {
            User targetUser = userService.saveWithHashPassword(user);
            String token = userService.createToken(targetUser);
            return Resp.ok(token);
        } else {
            return Resp.err("此用户已经存在");
        }
    }


    /**
     * @Author LuoRuiJie
     * @Description 用户登录
     * @Date
     * @Param User
     * @return Resp
     * 测通
     **/
    @PostMapping("/login")
    public Resp login(@RequestBody User user) {
        Assert.notNull(user.getAccount(), "账户为空");
        Assert.notNull(user.getPassword(), "密码为空");
        User targetUser = userService.findUser(user);
        if (targetUser == null) {
            return Resp.err("用户名不存在");
        }
        if (userService.checkPassword(targetUser, user.getPassword())) {
            String token = userService.createToken(targetUser);
            return Resp.ok(token);
        } else {
            return Resp.err("密码错误");
        }
    }


    /**
     * @Author LuoRuiJie
     * @Description 根据id删除用户
     * @Date
     * @Param int
     * @return Resp
     **/
    @PostMapping("/deleteUserById/{id}")
    public Resp deleteUserById(@PathVariable int id){
        if (userService.removeById(id)){
            return Resp.ok("删除成功");
        }
        return Resp.err("删除失败");
    }


    /**
     * @Author LuoRuiJie
     * @Description 修改用户信息
     * @Date
     * @Param User
     * @return Resp
     **/
    @PostMapping("/modifyUserInfo")
    public Resp modifyUserInfo(@RequestBody User user){
        if (userService.updateById(user)){
            return Resp.ok("用户信息修改成功");
        }
        return Resp.err("用户信息修改失败");
    }

}
