package com.baizhi.zjj.controller;


import com.baizhi.zjj.dao.*;
import com.baizhi.zjj.entity.*;
import com.baizhi.zjj.service.*;
import com.baizhi.zjj.util.SmsUtil;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("web")
public class WebController {
    @Autowired
    private AlbumService albumService;
    @Autowired
    private AlbumDao albumDao;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private ArticleDao articleDao;
    @Autowired
    private BannerService bannerService;
    @Autowired
    private BannerDao bannerDao;
    @Autowired
    private ChapterService chapterService;
    @Autowired
    private ChapterDao chapterDao;
    @Autowired
    private GuruService guruService;
    @Autowired
    private GuruDao guruDao;
    @Autowired
    private UserService userService;
    @Autowired
    private UserDao userDao;

    @Autowired
    private CourseDao courseDao;
    @Autowired
    private CounterDao counterDao;
    @Autowired
    private UgDao ugDao;
    //springdata操作Redis
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    //1. 登陆接口
    @RequestMapping("login")
    public Map login(String phone, String password) {
        HashMap map = new HashMap<>();
        User u= new User();
        u.setPhone(phone);
        User user = userDao.selectOne(u);
        if (user == null){
            System.out.println("该手机号不存在");
            map.put("status",-200);
            map.put("message","该手机号不存在");
        }else {
            if(password.equals(user.getPassword())){
                System.out.println("登陆成功");
                map.put("status",200);
                map.put("message","登陆成功");
                map.put("user",user);
            }else{
                System.out.println("密码错误");
                map.put("status",-200);
                map.put("message","密码错误");
            }
        }
        return map;
    }
    //2. 发送验证码
    @RequestMapping("sendCode")
    public Map queryArticleDetail(String phone){
        HashMap map = new HashMap<>();
        try {
            //随机生成验证码
            Random r = new Random();
            int intCode = r.nextInt(9999)+1000;
            String strCode = String.valueOf(intCode);
            System.out.println("验证码为 = " + strCode);
            //调用阿里大于，发送验证码
            SmsUtil.send(phone,strCode);
            //把验证码存 Redis
            stringRedisTemplate.opsForValue().set(phone+"_"+strCode,strCode,365, TimeUnit.DAYS);
            map.put("status",200);
            map.put("message","发送成功");
            map.put("code",strCode);
        }catch (Exception e){
            e.printStackTrace();
            map.put("status",-200);
            map.put("message","error");
        }
        return map;
    }
    //3. 对比验证码
    @RequestMapping("checkCode")
    public Map checkCode(String phone,String code){
        HashMap map = new HashMap<>();
        try {
            //将前台发来的验证码与Redis中的验证码对比
            String redisCode = stringRedisTemplate.opsForValue().get(phone + "_" + code);
            if(code.equals(redisCode)){
                map.put("status",200);
                map.put("message","验证码正确");
            }else{
                map.put("status",-200);
                map.put("message","验证码错误");
            }

        }catch (Exception e){
            e.printStackTrace();
            map.put("status",-200);
            map.put("message","error");
        }
        return map;
    }

    //4. 补充个人信息接口
    @RequestMapping("addUser")
    public Map addUser(String password,String phone,String photo,String name,String nickName,String sex,String sign,String location ){
        HashMap map = new HashMap();
        try {
            User user = new User();
            String uuid = UUID.randomUUID().toString();
            user.setId(uuid);
            user.setPhone(phone);
            user.setPassword(password);
            user.setName(name);
            user.setNickName(nickName);
            user.setSex(sex);
            user.setSign(sign);
            user.setLocation(location);
            user.setRegistDate(new Date());
            userDao.insertSelective(user);
            User u = userDao.selectByPrimaryKey(uuid);
            map.put("status",200);
            map.put("user",u);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status",-200);
            map.put("message","error");
        }
        return map;
    }
    //5. 一级页面展示接口
    @RequestMapping("onePage")
    // type : all|wen|si
    public Map onePage(String uid,String type,String sub_type){//用户uid
        HashMap hashMap = new HashMap();
        try {
            if (type.equals("all")){
                List<Banner> banners = bannerDao.queryBannersByTime();
                List<Album> albums = albumDao.selectByRowBounds(null, new RowBounds(0, 5));
                List<Article> articles = articleDao.selectAll();
                hashMap.put("status",200);
                hashMap.put("head",banners);
                hashMap.put("albums",albums);
                hashMap.put("articles",articles);
            }else if (type.equals("wen")){
                List<Album> albums = albumDao.selectByRowBounds(null, new RowBounds(0, 5));
                hashMap.put("status",200);
                hashMap.put("albums",albums);
            }else {
                if (sub_type.equals("ssyj")){
                    //当前用户所关注的上师发布的文章

                    //查询当前用户关注的所有上师信息
                    List<Article> articleList = new ArrayList<>();

                    List<Ug> ugList = ugDao.select(new Ug(null, uid, null));
                    for (Ug u : ugList) {
                        String guruid = u.getGuruid();
                        Article article = new Article();
                        article.setGuruId(guruid);
                        List<Article> artList = articleDao.select(article);
                        for (Article art : artList) {
                            articleList.add(art);
                        }
                    }
                    System.out.println("articleList = " + articleList);
                    System.out.println("上师言教个数："+articleList.size());
                    hashMap.put("articles",articleList);
                    hashMap.put("status",200);


                }else if(sub_type.equals("xmfy")){
                    //查询所有文章
                    List<Article> articles = articleDao.selectAll();
                    hashMap.put("status",200);
                    hashMap.put("articles",articles);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            hashMap.put("status",-200);
            hashMap.put("message","error");
        }

        return hashMap;
    }

    //6. 文章详情接口
    @RequestMapping("queryArticleDetail")
    public Map queryArticleDetail(String uid,String id){
        HashMap map = new HashMap<>();
        try {
            Article article = articleDao.selectByPrimaryKey(id);
            map.put("article",article);
            map.put("status",200);
        }catch (Exception e){
            e.printStackTrace();
            map.put("status",-200);
            map.put("message","error");
        }
        return map;
    }

    // 7. 专辑详情接口
    @RequestMapping("queryAlbumDetail")
    public Map queryAlbumDetail(String uid,String id){
        HashMap map = new HashMap<>();
        try {
            Album album = albumDao.selectByPrimaryKey(id);

            Chapter chapter = new Chapter();
            chapter.setAlbumId(id);
            List<Chapter> chapterList = chapterDao.select(chapter);

            map.put("album",album);
            map.put("list",chapterList);
            map.put("status",200);
        }catch (Exception e){
            e.printStackTrace();
            map.put("status",-200);
            map.put("message","error");
        }
        return map;
    }

    // 8. 展示功课
    @RequestMapping("queryCourseByUserId")
    public Map queryCourseByUserId(String uid){
        HashMap map = new HashMap<>();
        try {
            Course course = new Course();
            course.setUserId(uid);
            List<Course> courseList = courseDao.select(course);
            map.put("option",courseList);
            map.put("status",200);
        }catch (Exception e){
            e.printStackTrace();
            map.put("status",-200);
            map.put("message","error");
        }

        return map;
    }

    //9. 添加功课
    @RequestMapping("addCourse")
    public Map addCourse(String uid,String title){
        HashMap map = new HashMap<>();
        try {
            Course course = new Course();
            course.setId(UUID.randomUUID().toString());
            course.setUserId(uid);
            course.setTitle(title);
            course.setCreateDate(new Date());
            courseDao.insert(course);
            //查询用户当前所有功课
            Course c = new Course();
            c.setUserId(uid);
            List<Course> courseList = courseDao.select(c);

            map.put("option",courseList);
            map.put("status",200);

        }catch (Exception e){
            e.printStackTrace();
            map.put("status",-200);
            map.put("message","error");
        }
        return map;
    }

    //10. 删除功课
    @RequestMapping("deleteCourse")
    public Map deleteCourse(String uid,String id){
        HashMap map = new HashMap<>();
        try {
            courseDao.deleteByPrimaryKey(id);
            //查询用户当前所有功课
            Course c = new Course();
            c.setUserId(uid);
            List<Course> courseList = courseDao.select(c);

            map.put("option",courseList);
            map.put("status",200);

        }catch (Exception e){
            e.printStackTrace();
            map.put("status",-200);
            map.put("message","error");
        }

        return map;
    }

    //11. 展示计数器
    @RequestMapping("queryCounter")
    public Map queryCounter(String uid,String id){ //用户id 功课id
        System.out.println("11. 展示计数器");
        HashMap map = new HashMap<>();
        try {
            Counter counter = new Counter();
            counter.setUserId(uid);
            List<Counter> counterList = counterDao.select(counter);

            map.put("counters",counterList);
            map.put("status",200);
        }catch (Exception e){
            e.printStackTrace();
            map.put("status",-200);
            map.put("message","error");
        }
        return map;
    }

    //12. 添加计数器
    @RequestMapping("addCounter")
    public Map addCounter(String uid,String id ,String title){ //用户uid 功课id 标题
        System.out.println("12. 添加计数器");
        HashMap map = new HashMap<>();
        try {
            Counter c = new Counter();
            c.setId(UUID.randomUUID().toString());
            c.setCourseId(id);
            c.setUserId(uid);
            c.setTitle(title);
            c.setCreateDate(new Date());
            c.setCount(0);
            counterDao.insert(c);
            //展示当前用户计数器
            Counter counter = new Counter();
            counter.setUserId(uid);
            List<Counter> counterList = counterDao.select(counter);

            map.put("counters",counterList);
            map.put("status",200);
        }catch (Exception e){
            e.printStackTrace();
            map.put("status",-200);
            map.put("message","error");
        }
        return map;
    }

    //13. 删除计数器
    @RequestMapping("deleteCounter")
    public Map deleteCounter(String uid,String id){ //用户uid 计数器id
        System.out.println("13.删除计数器");
        HashMap map = new HashMap<>();
        try {
            counterDao.deleteByPrimaryKey(id);
            //展示当前用户计数器
            Counter counter = new Counter();
            counter.setUserId(uid);
            List<Counter> counterList = counterDao.select(counter);

            map.put("counters",counterList);
            map.put("status",200);
        }catch (Exception e){
            e.printStackTrace();
            map.put("status",-200);
            map.put("message","error");
        }
        return map;
    }

    //14. 变更计数器
    @RequestMapping("updateCounter")
    public Map updateCounter(String uid,String id,Integer count){ //用户uid 计数器id 计数值count
        System.out.println("14.变更计数器");
        HashMap map = new HashMap<>();
        try {
            Counter c = new Counter();
            c.setCount(count);
            c.setId(id);
            counterDao.updateByPrimaryKeySelective(c);
            //展示当前用户计数器
            Counter counter = new Counter();
            counter.setUserId(uid);
            List<Counter> counterList = counterDao.select(counter);

            map.put("counters",counterList);
            map.put("status",200);
        }catch (Exception e){
            e.printStackTrace();
            map.put("status",-200);
            map.put("message","error");
        }
        return map;
    }
    //15. 修改个人信息
    @RequestMapping("updateUser")
    public Map updateUser(String uid,String sex,String photo,String location,String sign,String nick_name,String password){
        System.out.println("15. 修改个人信息");
        HashMap map = new HashMap<>();
        try {
            User user = new User();
            user.setId(uid);
            user.setSex(sex);
            user.setLocation(location);
            user.setSign(sign);
            user.setNickName(nick_name);
            user.setPassword(password);
            userDao.updateByPrimaryKeySelective(user);

            map.put("user",user);
            map.put("status",200);
        }catch (Exception e){
            e.printStackTrace();
            map.put("status",-200);
            map.put("message","error");
        }
        return map;
    }
    //16. 金刚道友(随机展示5个用户)
    @RequestMapping("queryUserRandom")
    public Map queryUserRandom(String uid){ //用户uid
        System.out.println("16. 金刚道友(随机展示5个用户)");
        HashMap map = new HashMap<>();
        try {
            List<User> userList = userDao.queryUserRandom();
            map.put("list",userList);
            map.put("status",200);
        }catch (Exception e){
            e.printStackTrace();
            map.put("status",-200);
            map.put("message","error");
        }
        return map;
    }
    //17. 展示上师列表
    @RequestMapping("queryAllGuru")
    public Map queryAllGuru(String uid){ //用户uid
        System.out.println("17. 展示上师列表");
        HashMap map = new HashMap<>();
        try {
            List<Guru> guruList = guruDao.selectAll();
            map.put("list",guruList);
            map.put("status",200);
        }catch (Exception e){
            e.printStackTrace();
            map.put("status",-200);
            map.put("message","error");
        }
        return map;
    }
    //18. 添加关注上师
    @RequestMapping("addUg")
    public Map addUg(String uid,String id){ //用户uid 上师id
        System.out.println("18. 添加关注上师");
        HashMap map = new HashMap<>();
        try {
            Ug ug = new Ug();
            ug.setId(UUID.randomUUID().toString());
            ug.setUserid(uid);
            ug.setGuruid(id);
            ugDao.insert(ug);
            //查询当前用户关注的所有上师信息
            List<Guru> guruList = new ArrayList<>();
            
            List<Ug> ugList = ugDao.select(new Ug(null, uid, null));
            for (Ug u : ugList) {
                Guru guru = guruDao.selectByPrimaryKey(u.getGuruid());
                guruList.add(guru);
            }

            System.out.println("ugList = " + ugList);
            map.put("list",guruList);
            map.put("status",200);
        }catch (Exception e){
            e.printStackTrace();
            map.put("status",-200);
            map.put("message","error");
        }
        return map;
    }
}
