package cn.hellohao.controller;

import cn.hellohao.model.base.CommonResult;
import cn.hellohao.service.MobilePaperService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import cn.hellohao.service.WallpaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;

@Controller
public class WallpaperController {
    @Value("${webName}")
    private String webName;
    @Value("${weblinks}")
    private String weblinks;

    @Autowired
    private WallpaperService wallpaperService;
    @Autowired
    private MobilePaperService mobilePaperService;

    //Hellohao编写修改
    @RequestMapping("/")
    public String index(Model model) {
        model.addAttribute("webName",webName);
        model.addAttribute("weblinks",weblinks);

        return "index-1";
    }

    //首页最新更新
//    @RequestMapping(value="/GetWallpapers",method = RequestMethod.POST)
//    @ResponseBody
//    public String GetWallpaper(Integer start, Integer count,Integer category) {
//        String wallpaper = "";
//        wallpaper = wallpaperService.GetWallpaper(start, count,category);
//        return wallpaper;
//    }


    @GetMapping(value = "/GetWallpapers" )
    @ResponseBody
    public CommonResult GetWallpaper(Integer start, Integer count, Integer category) {
        String wallpaper = wallpaperService.getWallpaper(start, count, category);

        JSONArray jsonArray = new JSONArray();
        HashMap<String,Object> res=new HashMap<>();
         JSONObject imgjo = JSONObject.parseObject(wallpaper);
        //可以使用parseObject(params，Class<T> clazz)直接转换成需要的Bean
         JSONArray imgjson =     JSONArray.parseArray(imgjo.getString("data"));
        for (int i = 0; i < imgjson.size(); i++) {
            JSONObject job = imgjson.getJSONObject(i);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("imgUrl", job.get("url"));
            jsonObject.put("imgTag", job.get("utag"));
            jsonArray.add(i, jsonObject);
        }
        res.put("urls",jsonArray);
        res.put("img",imgjson);
        return CommonResult.success(res );
    }


    //获取所有分类
    @RequestMapping(value = "/GetCategory", method = RequestMethod.GET)
    @ResponseBody
    public String GetCategory() {
        String category = wallpaperService.getWallpaperCategory();
        return category;
    }

    @RequestMapping(value = "/GetMobilepapers", method = RequestMethod.GET)
    @ResponseBody
    public String GetMobilepaper(Integer start, Integer count, String category) {
        String mobilpaper = mobilePaperService.GetMobilepaper(start, count, category);
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        com.alibaba.fastjson.JSONObject imgjo = com.alibaba.fastjson.JSONObject.parseObject(mobilpaper);
        System.out.println(imgjo.toJSONString());
        //可以使用parseObject(params，Class<T> clazz)直接转换成需要的Bean
        JSONObject imgjsonObj = JSONObject.parseObject(imgjo.getString("res"));
        System.out.println(imgjsonObj);
        JSONArray imgjson =JSONArray.parseArray(imgjsonObj.getString("vertical"));
        for (int i = 0; i < imgjson.size(); i++) {
            JSONObject job = imgjson.getJSONObject(i);  // 遍历 jsonarray 数组，把每一个对象转成 json 对象
            jsonObject.put("ImgUrl", job.get("preview"));
            jsonObject.put("ImgTag", job.get("tag"));
            jsonArray.add(i, jsonObject);
        }
        System.out.println();
        return jsonArray.toString();
    }


    //获取所有分类
    @RequestMapping(value = "/GetMobileCategory", method = RequestMethod.GET)
    @ResponseBody
    public String GetMobileCategory() {
        String category = mobilePaperService.GetMobilepaperCategory();
        return category;
    }


}
