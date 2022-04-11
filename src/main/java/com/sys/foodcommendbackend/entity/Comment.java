package com.sys.foodcommendbackend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author LuoRuiJie
 * @since 2022-04-09
 */
public class Comment implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "comment_id", type = IdType.AUTO)
    private Integer commentId;

    private Integer createrId;

    private Integer commentBelong;

    private String commentContent;

    /**
     * 评论图片，以，来分隔
     */
    private String commentPhotoes;

    private String commentTime;

    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }
    public Integer getCreaterId() {
        return createrId;
    }

    public void setCreaterId(Integer createrId) {
        this.createrId = createrId;
    }
    public Integer getCommentBelong() {
        return commentBelong;
    }

    public void setCommentBelong(Integer commentBelong) {
        this.commentBelong = commentBelong;
    }
    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }
    public String getCommentPhotoes() {
        return commentPhotoes;
    }

    public void setCommentPhotoes(String commentPhotoes) {
        this.commentPhotoes = commentPhotoes;
    }
    public String getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(String commentTime) {
        this.commentTime = commentTime;
    }

    @Override
    public String toString() {
        return "Comment{" +
            "commentId=" + commentId +
            ", createrId=" + createrId +
            ", commentBelong=" + commentBelong +
            ", commentContent=" + commentContent +
            ", commentPhotoes=" + commentPhotoes +
            ", commentTime=" + commentTime +
        "}";
    }
}
