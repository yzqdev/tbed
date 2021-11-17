package cn.hellohao.service;

import cn.hellohao.entity.UploadConfig;
import org.springframework.stereotype.Service;

@Service
public interface UploadConfigService {
    UploadConfig getUpdateConfig();
    Integer setUpdateConfig(UploadConfig updateConfig);
}
