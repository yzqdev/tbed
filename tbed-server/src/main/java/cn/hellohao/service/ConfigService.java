package cn.hellohao.service;

import cn.hellohao.entity.Config;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;

@Service
public interface ConfigService extends IService<Config> {
    Config getSourceType();
    Integer setSourceType(Config config);
}
