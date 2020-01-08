package com.baizhi.zjj.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import java.util.Date;
import java.io.Serializable;

/**
 * (Counter)实体类
 *
 * @author pmc
 * @since 2020-01-06 15:48:50
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Counter implements Serializable {
    private static final long serialVersionUID = -88749597929547653L;
    @Id
    private String id;
    
    private String title;
    
    private Integer count;
    
    private Date createDate;
    
    private String userId;
    
    private String courseId;


}