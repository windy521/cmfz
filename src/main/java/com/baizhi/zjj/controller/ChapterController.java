package com.baizhi.zjj.controller;


import com.baizhi.zjj.entity.Chapter;
import com.baizhi.zjj.service.ChapterService;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.AudioHeader;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("chapter")
public class ChapterController {
    @Autowired
    private ChapterService chapterService;

    @RequestMapping("queryAllByPage")
    @ResponseBody
    public Map<String,Object> queryAllByPage(Integer page, Integer rows,String albumId){
        System.out.println("chapter----queryAllByPage");
        Map<String, Object> map = chapterService.queryAllByPage(page, rows,albumId);
        return map;
    }

    @RequestMapping("edit")
    @ResponseBody  // 返回值是void，相当于把空字符 响应ajax回client
    // 参数oper，代表增删改的具体操作类型，名称 固定（jqgrid控制封装好的参数）
    public Map<String, Object> edit(Chapter chapter, String oper,String[ ] id){
        Map<String, Object> map = new HashMap<>();
        System.out.println("...执行增删改操作...edit...");
        if ("add".equals(oper)){
            System.out.println("添加操作。。。执行");
            Chapter b = chapterService.insert(chapter);
            map.put("chapterId",b.getId());
        } else if ("edit".equals(oper)){
            System.out.println("修改操作。。。执行");
            chapterService.update(chapter);
            //map.put("chapterId",chapter.getId());
        } else {
            System.out.println("删除操作(可批量删除)。。。执行");
            //chapterService.deleteById(chapter.getId());
            int i = chapterService.deleteByIdList(Arrays.asList(id));
            System.out.println("删除了"+ i + "条数据");
        }
        return map;
    }

    @RequestMapping("uploadChapter")
    public void uploadChapter(MultipartFile url, String chapterId, HttpSession session) throws TagException, ReadOnlyFileException, CannotReadException, InvalidAudioFrameException, IOException {
        System.out.println("MultipartFile："+ url);
        // 获取真实路径
        String realPath = session.getServletContext().getRealPath("/upload/audio/");
        // 判断该文件夹是否存在
        File file = new File(realPath);
        if (!file.exists()) {
            // mkdirs() 多级创建
            file.mkdirs();
        }
        // 防止文件重名
        String originalFilename = url.getOriginalFilename();
        System.out.println("originalFilename = " + originalFilename);
        String newFileName = new Date().getTime() + "_" + originalFilename;
        try {
            url.transferTo(new File(realPath, newFileName));
            // new Chapter(); 设置id(找到原始数据) url(更新使用)
            // chapterDao.updateByPrimaryKeySetive(chapter);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //计算文件大小
        DecimalFormat df = new DecimalFormat("0.00");//保留两位小数
        String size = df.format((float)url.getSize() / 1024 / 1024);

        System.out.println("大小："+size+"MB");
        //计算音频时长
        String filePath = session.getServletContext().getRealPath("/upload/audio/"+newFileName);
        File file1 = new File(filePath);
        //获取音频时长 单位是秒      AudioFile类需要引入额外依赖 jaudiotagger
        AudioFile read = AudioFileIO.read(file1);
        AudioHeader header = read.getAudioHeader();
        int trackLength = header.getTrackLength();
        //获取分钟数
        Integer m=trackLength/60;
        //获取秒秒数
        Integer s=trackLength%60;
        System.out.println("时长："+m+"分"+s+"秒");

        if(originalFilename.equals("")){
            Chapter chapter = new Chapter();
            chapter.setId(chapterId);
            chapter.setUrl("/upload/audio/"+"默认音频.mp3");
            chapter.setSize("1.0MB");
            chapter.setTime("1分0秒");
            chapterService.update(chapter);
        }else {
            Chapter chapter = new Chapter();
            chapter.setId(chapterId);
            chapter.setUrl("/upload/audio/"+newFileName);
            chapter.setSize(size+"MB");
            chapter.setTime(m+"分"+s+"秒");
            chapterService.update(chapter);
        }

    }


    @RequestMapping("/downloadChapter")
    public void downloadChapter(String name, HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("下载："+ name);
        //通过名字 找到该文件
        //String realPath = request.getSession().getServletContext().getRealPath("/upload");
        String realPath = request.getSession().getServletContext().getRealPath(name);
        //String filePath = realPath + "/" + name;
        java.io.File file = new java.io.File(realPath);
        //  inline在线打开   attachment 附件形式
        response.setHeader("content-disposition","attachment;fileName="+ URLEncoder.encode(name,"utf-8"));
//----------------------------可不写-----------------------------------------------
        //获取文件后缀
        String extension = FilenameUtils.getExtension(name);
        String ext="."+extension;
        //通过文件后缀获取文件的类型
        String mimeType1 = request.getSession().getServletContext().getMimeType(ext);
        //把文件类型响应出去
        response.setContentType(mimeType1);

//----------------------------可不写------------------------------------------------

        //把文件响应回去
        ServletOutputStream outputStream = response.getOutputStream();
        //把文件转换为字节数组
        byte[] bytes = FileUtils.readFileToByteArray(file);
        outputStream.write(bytes);


    }


}
