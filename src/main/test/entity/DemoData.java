package entity;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/*
   @ExcelProperty(value = "ID") 声明表头信息
   @ExcelIgnore 导出数据时忽略该属性
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
// 不能使用链式调用方法
@ContentRowHeight(10) //内容行高
@HeadRowHeight(20)    //头部行高
@ColumnWidth(25)      //列宽
public class DemoData {
    @ExcelProperty(value = {"班级","ID"})
    private String id;
    @ExcelProperty(value = {"班级","年龄"})
    private Integer age;
    @ExcelProperty(value = {"班级","日期"})
    private Date date;
    @ExcelIgnore
    private String ignore;
}
