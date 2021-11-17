package cn.hellohao.entity;

/**
 * @author Hellohao
 * @version 1.0
 * @date 2021/10/26 18:59
 */
public class Group {
    private Integer id;
    private String groupName;
    private Integer keyid;
    private Integer usertype;
    private Integer compress;
    private Integer storageType;
    private String keyname;


    public Group() {
    }

    public Group(Integer id, String groupName, Integer keyid, Integer usertype, Integer compress, Integer storageType, String keyname) {
        this.id = id;
        this.groupName = groupName;
        this.keyid = keyid;
        this.usertype = usertype;
        this.compress = compress;
        this.storageType = storageType;
        this.keyname = keyname;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Integer getKeyid() {
        return keyid;
    }

    public void setKeyid(Integer keyid) {
        this.keyid = keyid;
    }

    public Integer getUsertype() {
        return usertype;
    }

    public void setUsertype(Integer usertype) {
        this.usertype = usertype;
    }

    public Integer getCompress() {
        return compress;
    }

    public void setCompress(Integer compress) {
        this.compress = compress;
    }

    public Integer getStorageType() {
        return storageType;
    }

    public void setStorageType(Integer storageType) {
        this.storageType = storageType;
    }

    public String getKeyname() {
        return keyname;
    }

    public void setKeyname(String keyname) {
        this.keyname = keyname;
    }
}
