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
public class Article implements Serializable {
    private static final long serialVersionUID = -48159217211114552L;

    @Id
    private String id;
    
    private String title;
    
    private String img;
    
    private String content;
    @JSONField(format = "yyyy-MM-dd")
    private Date createDate;
    @JSONField(format = "yyyy-MM-dd")
    private Date publishDate;
    
    private String status;
    
    private String guruId;



}