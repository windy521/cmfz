package com.baizhi.zjj.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import java.io.Serializable;

/**
 * (Ug)实体类
 * user guru关系表
 *
 * @author pmc
 * @since 2020-01-06 18:49:58
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ug implements Serializable {
    private static final long serialVersionUID = 103859045025573880L;
    @Id
    private String id;
    
    private String userid;
    
    private String guruid;


}