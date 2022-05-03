package cn.hellohao.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yanni
 * @date time 2022/5/3 23:21
 * @modified By:
 */
@RestController
@RequestMapping("/")
public class PageController {

    @GetMapping("/home")
    public String indexPage(){
        return  "看到这个页面说明启动成功";
    }
}
