package cn.hellohao.entity;

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
    private Integer userid;
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
     * 用户名
     */
    private String username;
    /**
     * 存储类型
     */
    private Integer storageType;
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
    private String useridlist;
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
    private String yyyy;
    private String[] classifuidlist; //类别uid集合
    private String classificationuid; //类别uid集合



    public Images(String imgUrl, String sizes, String abnormal, String updatetime, String username, String md5key, String imgUid) {
        this.imgUrl = imgUrl;
        this.sizes = sizes;
        this.abnormal = abnormal;
        this.updateTime = updatetime;
        this.username = username;
        this.md5key = md5key;
        this.imgUid = imgUid;
    }

    public Images(Integer id, String imgName, String imgUrl, Integer userid, String sizes, String abnormal, Integer source,
                  Integer imgType, String updatetime, String username, Integer storageType, String startTime, String stopTime,
                  String explains, String md5key, String notes, String useridlist, String imgUid, String albumTitle,
                  String password, Integer selectType, Long countNum, Integer monthNum, String yyyy,
                  String format, String about, Integer great, String[] classifuidlist, String classificationuid, String violation) {
        this.id = id;
        this.imgName = imgName;
        this.imgUrl = imgUrl;
        this.userid = userid;
        this.sizes = sizes;
        this.abnormal = abnormal;
        this.source = source;
        this.imgType = imgType;
        this.updateTime = updatetime;
        this.username = username;
        this.storageType = storageType;
        this.startTime = startTime;
        this.stopTime = stopTime;
        this.explains = explains;
        this.md5key = md5key;
        this.notes = notes;
        this.useridlist = useridlist;
        this.imgUid = imgUid;
        this.albumTitle = albumTitle;
        this.password = password;
        this.selectType = selectType;
        this.countNum = countNum;
        this.monthNum = monthNum;
        this.yyyy = yyyy;
        this.format = format;
        this.about = about;
        this.great = great;
        this.classifuidlist = classifuidlist;
        this.classificationuid = classificationuid;
        this.violation = violation;

    }

}
	
		

