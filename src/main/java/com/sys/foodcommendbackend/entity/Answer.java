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
public class Answer implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "answer_id", type = IdType.AUTO)
    private Integer answerId;

    private Integer createrId;

    /**
     * 所属评论id
     */
    private Integer belongId;

    private String answerContent;

    private String answerPhoto;

    private String answerTime;

    public Integer getAnswerId() {
        return answerId;
    }

    public void setAnswerId(Integer answerId) {
        this.answerId = answerId;
    }
    public Integer getCreaterId() {
        return createrId;
    }

    public void setCreaterId(Integer createrId) {
        this.createrId = createrId;
    }
    public Integer getBelongId() {
        return belongId;
    }

    public void setBelongId(Integer belongId) {
        this.belongId = belongId;
    }
    public String getAnswerContent() {
        return answerContent;
    }

    public void setAnswerContent(String answerContent) {
        this.answerContent = answerContent;
    }
    public String getAnswerPhoto() {
        return answerPhoto;
    }

    public void setAnswerPhoto(String answerPhoto) {
        this.answerPhoto = answerPhoto;
    }
    public String getAnswerTime() {
        return answerTime;
    }

    public void setAnswerTime(String answerTime) {
        this.answerTime = answerTime;
    }

    @Override
    public String toString() {
        return "Answer{" +
            "answerId=" + answerId +
            ", createrId=" + createrId +
            ", belongId=" + belongId +
            ", answerContent=" + answerContent +
            ", answerPhoto=" + answerPhoto +
            ", answerTime=" + answerTime +
        "}";
    }
}
