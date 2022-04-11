package com.sys.foodcommendbackend.service.impl;

import com.sys.foodcommendbackend.entity.Comment;
import com.sys.foodcommendbackend.mapper.CommentMapper;
import com.sys.foodcommendbackend.service.CommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author LuoRuiJie
 * @since 2022-04-09
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

}
