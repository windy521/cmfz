package com.baizhi.zjj.entity;


import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Album implements Serializable {
  @Id
  private String id;
  private String title;
  private String score;
  private String author;
  private String broadcast;
  private Integer count;
  private String description;
  private String status;
  private String cover;
  @JSONField(format = "yyyy-MM-dd")
  private Date createDate;

}
