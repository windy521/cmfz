import com.alibaba.excel.EasyExcel;
import com.baizhi.zjj.CmfzStart;
import entity.ImageData;
import entity.DemoData;
import entity.DemoDataListener;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.UUID;

@SpringBootTest(classes = CmfzStart.class)
@RunWith(SpringRunner.class)
public class TestEasyExcel {
    @Test
    public void test01(){
        //String fileName = "D:\\后期项目\\day7-poiEasyExcel\\示例\\"+new Date().getTime()+".xlsx";
        String fileName = "E:\\"+new Date().getTime()+".xlsx";
        // write() 参数1:文件路径 参数2:实体类.class sheet()指定写入工作簿的名称 doWrite(List数据) 写入操作
        // 如需下载使用 参数1:outputSteam 参数2:实体类.class
        EasyExcel.write(fileName, DemoData.class) // 指定文件导出的路径及样式
               .sheet("测试")           // 指定导出到哪个sheet工作簿
               .doWrite(Arrays.asList(new DemoData(UUID.randomUUID().toString(),18,new Date(),"Rxx"),new DemoData(UUID.randomUUID().toString(),18,new Date(),"Rxx"),new DemoData(UUID.randomUUID().toString(),18,new Date(),"Rxx"),new DemoData(UUID.randomUUID().toString(),18,new Date(),"Rxx")));
                // 导出操作 准备数据

    }
    /*
        导入操作 readListener
     */
    @Test
    public void test02(){
        String url = "D:\\后期项目\\day7-poiEasyExcel\\示例\\1577950228687.xlsx";
        // readListener : 读取数据时的监听器  每次使用DemoDataListener都需要new  不要把DemoDataListener交给Spring工厂管理
        // 文件上传 : MFile url  文件上传  File file = new File();
        EasyExcel.read(url,DemoData.class,new DemoDataListener()).sheet().doRead();
    }
    @Test
    public void test03(){
        String fileName = "D:\\后期项目\\day7-poiEasyExcel\\示例\\"+new Date().getTime()+".xlsx";
        HashSet<String> hashSet = new HashSet<String>();
        // excludeColumnFiledNames 排除不需要显示的字段 hashset.add("属性名")
        hashSet.add("id");
        hashSet.add("age");
        EasyExcel.write(fileName,DemoData.class).excludeColumnFiledNames(hashSet).sheet().doWrite(Arrays.asList(new DemoData(UUID.randomUUID().toString(),null,new Date(),"Rxx"),new DemoData(UUID.randomUUID().toString(),18,new Date(),"Rxx"),new DemoData(UUID.randomUUID().toString(),18,new Date(),"Rxx"),new DemoData(UUID.randomUUID().toString(),18,new Date(),"Rxx")));
    }
    @Test
    public void test04() throws MalformedURLException {
        String fileName = "D:\\后期项目\\day7-poiEasyExcel\\示例\\"+new Date().getTime()+".xlsx";
        // write() 参数1:文件路径 参数2:实体类.class sheet()指定写入工作簿的名称 doWrite(List数据) 写入操作
        // 如需下载使用 参数1:outputSteam 参数2:实体类.class
        ImageData imageData = new ImageData("img/1577416488008_1573576796124_timg.jpg");
        EasyExcel.write(fileName, ImageData.class) // 指定文件导出的路径及样式
                .sheet("测试")           // 指定导出到哪个sheet工作簿
                .doWrite(Arrays.asList(imageData));
        // 导出操作 准备数据

    }
    
}
