package com.sys.foodcommendbackend.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.databind.ser.Serializers;
import com.sys.foodcommendbackend.entity.Answer;
import com.sys.foodcommendbackend.entity.Comment;
import com.sys.foodcommendbackend.service.AnswerService;
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
@RequestMapping("/answer")
public class AnswerController extends BaseController {

    @Autowired
    private AnswerService answerService;


    /**
     * @Author LuoRuiJie
     * @Description 创建一个评论的评论
     * @Date
     * @Param Answer
     * @return Resp
     **/
    @PostMapping("/createAnswer")
    public Resp createAnswer(@RequestBody Answer answer){
        int currentUserId = Integer.parseInt(getSenderId());
        answer.setCreaterId(currentUserId);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        String answerTime = format.format(date);
        answer.setAnswerTime(answerTime);
        if (answerService.save(answer)){
            return Resp.ok("发表评论成功");
        }
        return Resp.err("发表评论失败");
    }


    /**
     * @Author LuoRuiJie
     * @Description 根据id删除评论
     * @Date
     * @Param int
     * @return Resp
     **/
    @PostMapping("/deleteAnswerById/{id}")
    public Resp deleteAnswerById(@PathVariable int id){
        Answer answer = answerService.getById(id);
        Assert.notNull(answer,"该回答不存在");
        if (answerService.removeById(id)){
            return Resp.ok("删除成功");
        }
        return Resp.err("删除失败");
    }


    /**
     * @Author LuoRuiJie
     * @Description 用户修改评论的回复，传入的参数是Answer实体，必须要带有id
     * @Date
     * @Param Comment
     * @return Resp
     **/
    @PostMapping("/modifyAnswer")
    public Resp modifyComment(@RequestBody Answer answer){
        Answer targetAnswer = answerService.getById(answer.getAnswerId());
        Assert.notNull(targetAnswer,"评论不存在");
        if (answerService.updateById(answer)){
            return Resp.ok("修改成功");
        }
        return Resp.ok("修改失败");
    }


    /**
     * @Author LuoRuiJie
     * @Description 根据id获取单条评论回复的详细信息
     * @Date
     * @Param int
     * @return Resp
     **/
    @GetMapping("/getAnswerInfoById/{id}")
    public Resp getCommentInfoById(@PathVariable int id){
        Answer answer = answerService.getById(id);
        return Resp.ok(answer);
    }



    /**
     * @Author LuoRuiJie
     * @Description 分页获取所有评论的回复
     * @Date
     * @Param Map
     * @return Resp
     **/
    @GetMapping("/pageGetAllAnswer")
    public Resp pageGetAllComment(@RequestParam Map<String,String> params){
        int limit  = Integer.parseInt(params.get("limit"));
        int page = Integer.parseInt(params.get("currentPage"));
        Page<Answer> answerPage = answerService.page(new Page<>(page, limit));
        return Resp.ok(answerPage);
    }


    /**
     * @Author LuoRuiJie
     * @Description 分页获取当前用户的所有评论的回复，需要传入token
     * @Date
     * @Param Map
     * @return Resp
     **/
    @GetMapping("/pageGetUserAllAnswer")
    public Resp pageGetUserAllComment(@RequestParam Map<String,String> params){
        int limit  = Integer.parseInt(params.get("limit"));
        int page = Integer.parseInt(params.get("currentPage"));
        int CurrentUserId = Integer.parseInt(getSenderId());
        QueryWrapper<Answer> answerQueryWrapper = new QueryWrapper<Answer>().eq("creater_id", CurrentUserId);
        Page<Answer> answerPage = answerService.page(new Page<>(page, limit), answerQueryWrapper);
        return Resp.ok(answerPage);
    }


    /**
     * @Author LuoRuiJie
     * @Description 分页获取某评论对应的评论的回复
     * @Date
     * @Param Map
     * @return Resp
     **/
    @PostMapping("pageGetAllCommentByFoodId")
    public Resp pageGetAllCommentByFoodId(@RequestParam Map<String,String> params){
        int limit  = Integer.parseInt(params.get("limit"));
        int page = Integer.parseInt(params.get("currentPage"));
        int commentId = Integer.parseInt(params.get("belong_id"));
        QueryWrapper<Answer> AnswerQueryWrapper = new QueryWrapper<Answer>().eq("belong_id", commentId);
        Page<Answer> answerPage = answerService.page(new Page<>(page, limit), AnswerQueryWrapper);
        return Resp.ok(answerPage);
    }

}
