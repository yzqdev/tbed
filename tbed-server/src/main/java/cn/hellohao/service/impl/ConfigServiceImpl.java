package cn.hellohao.service.impl;

import cn.hellohao.dao.ConfigMapper;
import cn.hellohao.entity.Config;
import cn.hellohao.service.ConfigService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConfigServiceImpl extends ServiceImpl<ConfigMapper,Config> implements ConfigService {
    @Autowired
    private ConfigMapper configMapper;
    @Override
    public Config getSourceype() {
        return configMapper.getSourceype();
    }

    @Override
    public Integer setSourceype(Config config) {
        return configMapper.setSourceype(config);
    }
}
