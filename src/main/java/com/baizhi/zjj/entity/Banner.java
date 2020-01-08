package com.baizhi.zjj.entity;


import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import java.util.Date;

/*
   @ExcelProperty(value = "ID") 声明表头信息
   @ExcelIgnore 导出数据时忽略该属性
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
// 不能使用链式调用方法
@ContentRowHeight(15) //内容行高
@HeadRowHeight(20)    //头部行高
@ColumnWidth(25)      //列宽
public class Banner {
    @Id
    @ExcelProperty(value = {"Banner","ID"})
    private String id;
    @ExcelProperty(value = {"Banner","标题"})
    private String title;
    @ExcelProperty(value = {"Banner","封面路径"})
    private String url;
    @ExcelProperty(value = {"Banner","超链接"})
    private String href;
    @ExcelProperty(value = {"Banner","创建日期"})
    @JSONField(format = "yyyy-MM-dd")
    private Date createDate;
    @ExcelProperty(value = {"Banner","描述"})
    private String description;
    @ExcelProperty(value = {"Banner","状态"})
    private String status;

/*    @ExcelIgnore
    private String ignore;*/
}
