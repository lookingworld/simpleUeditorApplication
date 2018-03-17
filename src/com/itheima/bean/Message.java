package com.itheima.bean;

import java.io.Serializable;
import java.sql.Timestamp;

public class Message implements Serializable{
    private static final long serialVersionUID = 1L;
    private Integer  id;
    private Integer categoryid;
    private Timestamp createtime;
    private Timestamp updatetime;
    private String tittle;
    private String description;
    private String message;

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", categoryid=" + categoryid +
                ", createtime=" + createtime +
                ", updatetime=" + updatetime +
                ", tittle='" + tittle + '\'' +
                ", description='" + description + '\'' +
                ", message='" + message + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCategoryid() {
        return categoryid;
    }

    public void setCategoryid(Integer categoryid) {
        this.categoryid = categoryid;
    }

    public Timestamp getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Timestamp createtime) {
        this.createtime = createtime;
    }

    public Timestamp getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Timestamp updatetime) {
        this.updatetime = updatetime;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
