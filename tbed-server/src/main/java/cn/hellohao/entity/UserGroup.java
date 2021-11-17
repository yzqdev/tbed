package cn.hellohao.entity;

/**
 * @author Hellohao
 * @version 1.0
 * @date 2019/8/19 16:01
 */
public class UserGroup {
    private Integer id;
    private Integer userId;
    private Integer groupId;

    public UserGroup() {
    }

    public UserGroup(Integer id, Integer userId, Integer groupId) {
        this.id = id;
        this.userId = userId;
        this.groupId = groupId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }
}
