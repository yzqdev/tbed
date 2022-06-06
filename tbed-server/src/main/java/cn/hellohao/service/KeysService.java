package cn.hellohao.service;


import cn.hellohao.model.entity.StorageKey;
import cn.hellohao.model.entity.Msg;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface KeysService extends IService<StorageKey> {
    //查询密钥
    StorageKey selectKeys(String id);
    List<StorageKey> getStorageName();
    List<StorageKey> getStorage();
    //修改key
    Msg updateKey(StorageKey key);
    List<StorageKey> getKeys();
}
