package cn.hellohao.entity;

/**
 * @author Hellohao
 * @version 1.0
 * @date 2019-12-18 22:13
 */
public class Album {
    /**
     * albumkey
     */
    private String  albumkey;
    /**
     * 专辑名称
     */
    private String albumTitle;
    /**
     * 创建日期
     */
    private String createDate;
    /**
     * 密码
     */
    private String password;
    /**
     * 用户标识
     */
    private Integer userid;
    /**
     * 用户名
     */
    private String username;

    public Album() {
    }

    public Album(String albumkey, String albumTitle, String createDate, String password, Integer userid, String username) {
        this.albumkey = albumkey;
        this.albumTitle = albumTitle;
        this.createDate = createDate;
        this.password = password;
        this.userid = userid;
        this.username = username;
    }

    public String getAlbumkey() {
        return albumkey;
    }

    public void setAlbumkey(String albumkey) {
        this.albumkey = albumkey;
    }

    public String getAlbumTitle() {
        return albumTitle;
    }

    public void setAlbumTitle(String albumTitle) {
        this.albumTitle = albumTitle;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
