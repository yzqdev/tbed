package cn.hellohao.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Hellohao
 * @version 1.0
 * @date 2021/10/26 18:59
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SiteGroup {
    private Integer id;
    private String groupName;
    private Integer keyId;
    private Integer userType;
    private Integer compress;
    private Integer storageType;
    private String keyName;


}
