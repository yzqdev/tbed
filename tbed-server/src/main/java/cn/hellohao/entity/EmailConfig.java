package cn.hellohao.entity;

import com.baomidou.mybatisplus.annotation.TableName;

/**
 * 邮件配置
 *
 * @author yanni
 * @date 2021/11/17
 */
@TableName("email_config")
public class EmailConfig {
    private Integer id ;
    private String emails;
    /**
     * 电子邮件的关键
     */
    private String emailKey;
    /**
     * 电子邮件网址
     */
    private String emailUrl;
    /**
     * 端口
     */
    private String port;
    private String emailName;
    private Integer using ;

    public EmailConfig() {
    }

    public EmailConfig(Integer id, String emails, String emailKey, String emailUrl, String port, String emailName, Integer using) {
        this.id = id;
        this.emails = emails;
        this.emailKey = emailKey;
        this.emailUrl = emailUrl;
        this.port = port;
        this.emailName = emailName;
        this.using = using;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmails() {
        return emails;
    }

    public void setEmails(String emails) {
        this.emails = emails;
    }

    public String getEmailKey() {
        return emailKey;
    }

    public void setEmailKey(String emailKey) {
        this.emailKey = emailKey;
    }

    public String getEmailUrl() {
        return emailUrl;
    }

    public void setEmailUrl(String emailUrl) {
        this.emailUrl = emailUrl;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getEmailName() {
        return emailName;
    }

    public void setEmailName(String emailName) {
        this.emailName = emailName;
    }

    public Integer getUsing() {
        return using;
    }

    public void setUsing(Integer using) {
        this.using = using;
    }
}
