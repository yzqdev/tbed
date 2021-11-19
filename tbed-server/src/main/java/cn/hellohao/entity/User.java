package cn.hellohao.entity;


import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 用户
 *
 * @author yanni
 * @date 2021/11/17
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User extends Model<User> {

    private Integer id;
    //@NotBlank(message = "用户名不能为空")
   // @Length(min = 6, max = 20, message = "用户名需要为 6 - 20 个字符")
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 电子邮件
     */
    private String email;
    /**
     * 生日
     */
    private String birthday;
    private Integer level;
    private String uid;
    private Integer isok;
    /**
     * 内存
     */
    private  Long memory;
    /**
     * 组id
     */
    private Integer groupId;
    /**
     * 组名称
     */
    private String groupName;
    ///**
    // * 用户照片数量
    // */
    //private Long counts;


}
