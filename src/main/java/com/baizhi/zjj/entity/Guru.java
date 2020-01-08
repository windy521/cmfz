package com.baizhi.zjj.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Guru implements Serializable {
    private static final long serialVersionUID = -65852096142886094L;
    @Id
    private String id;
    
    private String name;
    
    private String photo;
    
    private String status;
    
    private String nickName;

}