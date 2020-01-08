package com.baizhi.zjj.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import java.util.Date;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Course implements Serializable {
    private static final long serialVersionUID = -69237395295081603L;
    @Id
    private String id;
    
    private String title;
    
    private String userId;
    
    private String type;
    @JSONField(format = "yyyy-MM-dd")
    private Date createDate;



}