package cn.hellohao.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Hellohao
 * @version 1.0
 * @date 2019-12-18 22:12
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImgAndAlbum {
    /**
     * img的名字
     */
    private String imgName;
    private String albumKey;
    private String notes;


}
