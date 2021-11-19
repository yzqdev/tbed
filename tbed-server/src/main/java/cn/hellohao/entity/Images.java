package cn.hellohao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 图片
 *
 * @author yanni
 * @date 2021/11/17
 */
@Data
@NoArgsConstructor
@TableName("img_data")
@AllArgsConstructor
public class Images {
    // 默认的时间字符串格式

    private Integer id;
    /**
     * imgname
     */
    private String imgName;
    /**
     * imgurl
     */
    private String imgUrl;
    private Integer userId;
    /**
     * 大小
     */
    private String sizes;
    /**
     * 不正常的
     */
    private String abnormal;
    /**
     * 源
     */
    private Integer source;
    /**
     * imgtype
     */
    private Integer imgType;

    /**
     * 更新时间
     */
    private String updateTime;


    /**
     * 存储类型
     */
    //private Integer storageType;
    /**
     * 开始时间
     */
    private String startTime;
    /**
     * 停止时间
     */
    private String stopTime;
    private String explains;
    /**
     * md5key
     */
    private String md5key;
    private String notes;
    private String imgUid;
    private String format;
    private String about;
    private Integer great;
    private String violation;
    private String albumTitle;
    //@Length(min = 0, max = 10, message = "画廊密码不能超过10个字符")
    private String password;
    private Integer selectType;
    private Long countNum;
    private Integer monthNum;
    //private String yyyy;
    //private String[] classifuidlist; //类别uid集合
    //private String classificationuid; //类别uid集合







}
	
		

