package entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.converters.string.StringImageConverter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.File;
import java.io.InputStream;
import java.net.URL;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImageData {
    //private File file;
    //private InputStream inputStream;
    /**
     * 如果string类型 必须指定转换器，string默认转换成string
     * 绝对路径 继承StringImageConverter 重写 写入方法
     */
    @ExcelProperty(value = "图片",converter = ImageConverter.class)
    private String string;
    //private byte[] byteArray;
    //private URL url;
}
