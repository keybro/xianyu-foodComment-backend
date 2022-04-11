package com.sys.foodcommendbackend.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sys.foodcommendbackend.entity.Comment;
import com.sys.foodcommendbackend.service.CommentService;
import com.sys.foodcommendbackend.tool.Resp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author LuoRuiJie
 * @since 2022-04-09
 */
@RestController
@RequestMapping("/comment")
public class CommentController extends BaseController{

    @Autowired
    private CommentService commentService;

    /**
     * @Author LuoRuiJie
     * @Description 用户创建评论
     * @Date
     * @Param Comment
     * @return Resp
     *
     **/
    @PostMapping("/createComment")
    public Resp createComment(@RequestBody Comment comment){
        // 通过token获取当前请求用户的id
        int currentUserId = Integer.parseInt(getSenderId());
        comment.setCreaterId(currentUserId);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        String commentTime = format.format(date);
        comment.setCommentTime(commentTime);
        if (commentService.save(comment)){
            return Resp.ok("评论发表成功!");
        }
        return Resp.err("评论发表成功");
    }


    /**
     * @Author LuoRuiJie
     * @Description 根据id删除评论
     * @Date
     * @Param int
     * @return Resp
     **/
    @PostMapping("/deleteCommentById/{id}")
    public Resp deleteCommentById(@PathVariable int id){
        Comment comment = commentService.getById(id);
        Assert.notNull(comment,"找不到该评论");
        if (commentService.removeById(id)){
            return Resp.ok("删除评论成功");
        }
        return Resp.err("删除失败");

    }


    /**
     * @Author LuoRuiJie
     * @Description 用户修改评论，传入的参数是Comment实体，必须要带有id
     * @Date
     * @Param Comment
     * @return Resp
     **/
    @PostMapping("/modifyComment")
    public Resp modifyComment(@RequestBody Comment comment){
        Comment targetComment = commentService.getById(comment.getCommentId());
        Assert.notNull(targetComment,"评论不存在");
        if (commentService.updateById(comment)){
            return Resp.ok("修改成功");
        }
        return Resp.ok("修改失败");
    }


    /**
     * @Author LuoRuiJie
     * @Description 根据id获取单条评论的详细信息
     * @Date
     * @Param int
     * @return Resp
     **/
    @GetMapping("/getCommentInfoById/{id}")
    public Resp getCommentInfoById(@PathVariable int id){
        Comment comment = commentService.getById(id);
        return Resp.ok(comment);
    }



    /**
     * @Author LuoRuiJie
     * @Description 分页获取所有评论
     * @Date
     * @Param
     * @return
     **/
    @GetMapping("/pageGetAllComment")
    public Resp pageGetAllComment(@RequestParam Map<String,String> params){
        int limit  = Integer.parseInt(params.get("limit"));
        int page = Integer.parseInt(params.get("currentPage"));
        Page<Comment> commentPage = commentService.page(new Page<>(page, limit));
        return Resp.ok(commentPage);
    }


    /**
     * @Author LuoRuiJie
     * @Description 分页获取当前用户的所有评论，需要传入token
     * @Date
     * @Param Map
     * @return Resp
     **/
    @GetMapping("/pageGetUserAllComment")
    public Resp pageGetUserAllComment(@RequestParam Map<String,String> params){
        int limit  = Integer.parseInt(params.get("limit"));
        int page = Integer.parseInt(params.get("currentPage"));
        int CurrentUserId = Integer.parseInt(getSenderId());
        QueryWrapper<Comment> commentQueryWrapper = new QueryWrapper<Comment>().eq("creater_id", CurrentUserId);
        Page<Comment> commentPage = commentService.page(new Page<>(page, limit), commentQueryWrapper);
        return Resp.ok(commentPage);
    }


    /**
     * @Author LuoRuiJie
     * @Description 分页获取某种菜品对应的评论
     * @Date
     * @Param
     * @return
     **/
    @PostMapping("pageGetAllCommentByFoodId")
    public Resp pageGetAllCommentByFoodId(@RequestParam Map<String,String> params){
        int limit  = Integer.parseInt(params.get("limit"));
        int page = Integer.parseInt(params.get("currentPage"));
        int foodId = Integer.parseInt(params.get("comment_belong"));
        QueryWrapper<Comment> commentQueryWrapper = new QueryWrapper<Comment>().eq("comment_belong", foodId);
        Page<Comment> commentPage = commentService.page(new Page<>(page, limit), commentQueryWrapper);
        return Resp.ok(commentPage);
    }

}
