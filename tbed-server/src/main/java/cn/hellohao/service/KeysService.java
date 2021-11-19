package cn.hellohao.service;


import cn.hellohao.entity.Keys;
import cn.hellohao.entity.Msg;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface KeysService extends IService<Keys> {
    //查询密钥
    Keys selectKeys(Integer id);
    List<Keys> getStorageName();
    List<Keys> getStorage();
    //修改key
    Msg updateKey(Keys key);
    List<Keys> getKeys();
}
