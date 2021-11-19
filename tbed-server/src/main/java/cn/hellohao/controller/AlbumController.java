package cn.hellohao.controller;

import cn.hellohao.entity.*;
import cn.hellohao.entity.dto.AlbumDto;
import cn.hellohao.entity.vo.PageResultBean;
import cn.hellohao.service.ImgAndAlbumService;
import cn.hellohao.service.UserService;
import cn.hellohao.service.impl.AlbumServiceImpl;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Hellohao
 * @version 1.0
 * @date 2019/12/17 11:25
 */
@Controller
public class AlbumController {
    @Autowired
    AlbumServiceImpl albumServiceImpl;
    @Autowired
    ImgAndAlbumService imgAndAlbumService;
    @Autowired
    private UserService userService;

    @PostMapping("/admin/getGalleryList") //new
    @ResponseBody
    public Map<String, Object> getGalleryList (@RequestBody AlbumDto albumDto){
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        user =  userService.getUsers(user);
        Map<String, Object> map = new HashMap<String, Object>();

        Integer pageNum =albumDto.getPageNum();
        Integer pageSize = albumDto.getPageSize();
        String albumtitle = albumDto.getAlbumTitle();
        if(subject.hasRole("admin")){
        }else{
            albumDto.setUserId(String.valueOf(user.getId()));
        }
        PageHelper.startPage(pageNum, pageSize);
        List<Album> albums = null;
        try {
            albums = albumServiceImpl.selectAlbumURLList(albumDto);
            PageInfo<Album> rolePageInfo = new PageInfo<>(albums);
            map.put("code", 200);
            map.put("info", "");
            map.put("count", rolePageInfo.getTotal());
            map.put("data", rolePageInfo.getList());
        } catch (Exception e) {
            e.printStackTrace();
            map.put("code", 500);
            map.put("info", "获取数据异常");
        }
        return map;

    }

    @PostMapping("/admin/deleGallery") //new 删除画廊
    @ResponseBody
    public Msg deleGallery (@RequestParam(value = "data", defaultValue = "") String data) {
        Msg msg = new Msg();
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        try {
            JSONObject jsonObject = JSONObject.parseObject(data);
            JSONArray albumkeyList = jsonObject.getJSONArray("albumkeyList");
            for (int i = 0; i < albumkeyList.size(); i++) {
                if(subject.hasRole("admin")){
                    albumServiceImpl.deleteAlbum(albumkeyList.getString(i));
                }else{
                    AlbumDto album=new AlbumDto();
                    album.setAlbumKey(albumkeyList.getString(i));
                    final Album alb = albumServiceImpl.selectAlbum(album);
                    if(alb.getUserId().equals(user.getId())){
                        albumServiceImpl.deleteAlbum(albumkeyList.getString(i));
                    }
                }
            }
            msg.setInfo("画廊已成功删除");
        }catch (Exception e){
            msg.setCode("500");
            msg.setInfo("画廊删除失败");
            e.printStackTrace();
        }
        return msg;

    }

    @PostMapping("/getAlbumImgList") //new
    @ResponseBody
    public Msg getAlbumImgList(@RequestBody String[] data) {
        Msg msg = new Msg();


        JSONArray json = albumServiceImpl.getAlbumList(data);
        msg.setData(json);
        return msg;
    }

    @PostMapping("/SaveForAlbum")//new
    @ResponseBody
    public Msg SaveForAlbum(@RequestBody AlbumDto albumDto){
        //data = StringEscapeUtils.unescapeHtml4(data);
        Msg msg = new Msg();
        try {

            String albumtitle = albumDto.getAlbumTitle();
            String password = albumDto.getPassword();
           List<Images> albumList=albumDto.getAlbumList();
            if(null!=password){
                password = password.replace(" ", "");
                if(password.replace(" ", "").equals("") || password.length()<3){
                    msg.setCode("110403");
                    msg.setInfo("密码长度不得小于三位有效字符");
                    return msg;
                }
            }
            if(albumtitle==null || albumList.size()==0){
                msg.setCode("110404");
                msg.setInfo("标题和图片参数不能为空");
                return msg;
            }
            Subject subject = SecurityUtils.getSubject();
            User u = (User) subject.getPrincipal();
            String uuid = "TOALBUM"+ UUID.randomUUID().toString().replace("-", "").toLowerCase().substring(0,5)+"N";
            SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Album album = new Album();
            album.setAlbumTitle(albumtitle);
            album.setCreateDate(df.format(new Date()));
            album.setPassword(password);
            album.setAlbumKey(uuid);
            if(u==null){
                album.setUserId(0);
            }else{
                album.setUserId(u.getId());
            }
            albumServiceImpl.addAlbum(album);
            for (int i = 0; i < albumList.size(); i++) {
                Images img =albumList.get(i);
                ImgAndAlbum imgAndAlbum = new ImgAndAlbum();
                //imgAndAlbum.setImgName(img.getString("imgUrl"));
                imgAndAlbum.setAlbumKey(uuid);
                imgAndAlbum.setNotes(img.getNotes());
                albumServiceImpl.addAlbumForImgAndAlbumMapper(imgAndAlbum);
            }
            final JSONObject json = new JSONObject();
            json.put("url",uuid);
            json.put("title",albumtitle);
            json.put("password",password);
            msg.setCode("200");
            msg.setInfo("成功创建画廊链接");
            msg.setData(json);
        }catch (Exception e){
            e.printStackTrace();
            msg.setCode("500");
            msg.setInfo("创建画廊链接失败");
        }
        return msg;
    }


    @PostMapping("/checkPass")//new
    @ResponseBody
    public Msg checkPass(@RequestBody Map<String,String> jsonObject) {
        Msg msg = new Msg();
        JSONObject json = new JSONObject();

        try {

            String key = jsonObject.get("key");
            AlbumDto album=new AlbumDto();
            album.setAlbumKey(key);
            Album a =  albumServiceImpl.selectAlbum(album);
            if(a!=null){
                json.put("album",a);
                json.put("exist",true);
                if(a.getPassword()!=null && !a.getPassword().equals("")){
                    json.put("passType",true);
                }else{
                    json.put("passType",false);
                }
                msg.setData(json);
            }else{
                json.put("exist",false);
                msg.setData(json);
            }
        } catch (Exception e) {
            e.printStackTrace();
            msg.setCode("110500");
            msg.setInfo("操作失败");
            msg.setData(json);
        }
        return msg;
    }

    @PostMapping("/getAlbumList") //new
    @ResponseBody
    public Msg getAlbumList(@RequestBody AlbumDto albumDto){
        Msg msg = new Msg();
        JSONObject json = new JSONObject();
        Integer pageNum = albumDto.getPageNum();
        Integer pageSize = albumDto.getPageSize();
        String albumkey = albumDto.getAlbumKey();
        String password = albumDto.getPassword();
        if(null!=password){
            password = password.replace(" ", "");
        }
       AlbumDto album=new AlbumDto();
        album.setAlbumKey(albumkey);
        Album a =  albumServiceImpl.selectAlbum(album);
        if(a==null){
            msg.setCode("110404");
            msg.setInfo("画廊地址不存在");
        }else{
            PageHelper.startPage(pageNum, pageSize);
            if(a.getPassword()==null || (a.getPassword().replace(" ", "")).equals("")){
                List<Images> imagesList = imgAndAlbumService.selectImgForAlbumkey(albumkey);
                PageInfo<Images> rolePageInfo = new PageInfo<>(imagesList);
                PageResultBean<Images> pageResultBean = new PageResultBean<>(rolePageInfo.getTotal(), rolePageInfo.getList());
                json.put("imagesList",pageResultBean);
            }else{
                if(a.getPassword().equals(password)){
                    List<Images> imagesList =  imgAndAlbumService.selectImgForAlbumkey(albumkey);
                    PageInfo<Images> rolePageInfo = new PageInfo<>(imagesList);
                    PageResultBean<Images> pageResultBean = new PageResultBean<>(rolePageInfo.getTotal(), rolePageInfo.getList());
                    json.put("imagesList",pageResultBean);
                }else{
                    msg.setCode("110403");
                    msg.setInfo("画廊密码错误");
                }
            }
            json.put("titlename",a.getAlbumTitle());
            msg.setData(json);
        }
        return msg;
    }



}
