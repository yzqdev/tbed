package cn.hellohao.mapper;

import cn.hellohao.model.entity.EmailConfig;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EmailConfigMapper extends BaseMapper<EmailConfig> {
    /**
     * 获得电子邮件
     *
     * @return {@link EmailConfig}
     */
    EmailConfig getEmail();

    /**
     * 更新电子邮件
     *
     * @param emailConfig 邮件配置
     * @return {@link Integer}
     */
    Integer updateEmail(EmailConfig emailConfig);
}
