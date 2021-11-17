package cn.hellohao.service;

import cn.hellohao.entity.EmailConfig;
import org.springframework.stereotype.Service;

@Service
public interface EmailConfigService {
    EmailConfig getemail();
    Integer updateemail(EmailConfig emailConfig);
}
