package com.itheima.bean;

import java.io.Serializable;
import java.util.Date;

public class MessageOut implements Serializable{
    private static final long serialVersionUID = 1L;
    private Integer id;
    private Integer cid;
    private String cname;
    private String tittle;
    private String message;
    private Date createtime;
    private Date updatetime;
    private String description;

    @Override
    public String toString() {
        return "MessageOut{" +
                "id=" + id +
                ", cid=" + cid +
                ", cname='" + cname + '\'' +
                ", tittle='" + tittle + '\'' +
                ", message='" + message + '\'' +
                ", createtime=" + createtime +
                ", updatetime=" + updatetime +
                ", description='" + description + '\'' +
                '}';
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }
}
