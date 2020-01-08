package entity;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.ArrayList;
import java.util.List;

/*
    1. 创建一个Listener类 继承 AnalysisEventListener<实体类>
    2. 重写方法
    invoke : 读取每行数据后会执行的方法
    doAfterAllAnalysed: 所有数据读取完毕后执行的方法
 */
public class DemoDataListener extends AnalysisEventListener<DemoData> {
    List<DemoData> list = new ArrayList<DemoData>();
    @Override
    // DemoData 针对每行数据 进行的实体类封装
    public void invoke(DemoData demoData, AnalysisContext analysisContext) {
        list.add(demoData);
        // insert(demodata)
        System.out.println(demoData);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        System.out.println(list);
        // insert(list);
    }
}
