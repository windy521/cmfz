package com.baizhi.zjj.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Data
public class Log {
    private String id;
    private String thing;
    private String name;
    //@JsonFormat(pattern = "yyyy-MM-dd" ,timezone = "GMT+8")
    @JSONField(format = "yyyy-MM-dd")
    private Date date;
    private Boolean flag;
}