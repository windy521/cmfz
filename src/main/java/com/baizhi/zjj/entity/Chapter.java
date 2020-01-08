package com.baizhi.zjj.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import java.util.Date;
import java.io.Serializable;

/**
 * (Chapter)实体类
 *
 * @author pmc
 * @since 2019-12-27 19:54:21
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Chapter implements Serializable {
    private static final long serialVersionUID = 739789490664014099L;
    @Id
    private String id;
    
    private String title;
    
    private String url;
    
    private String size;
    
    private String time;
    @JSONField(format = "yyyy-MM-dd")
    private Date createTime;
    
    private String albumId;


}