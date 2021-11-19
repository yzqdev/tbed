package cn.hellohao.service.impl;

import cn.hellohao.entity.Keys;
import cn.hellohao.entity.ReturnImage;
import cn.hellohao.utils.*;
import com.UpYun;
import com.aliyun.oss.model.ObjectMetadata;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.*;

@Service
public class USSImageupload {
    static UpYun upyun;
    static Keys key;

    public ReturnImage ImageuploadUSS(Map<String, File> fileMap, String username,Integer keyID) {
        ReturnImage returnImage = new ReturnImage();
        File file = null;
        ObjectMetadata meta = new ObjectMetadata();
        meta.setHeader("Content-Disposition", "inline");
        try {
            for (Map.Entry<String, File> entry : fileMap.entrySet()) {
                String ShortUID = SetText.getShortUuid();
                file = entry.getValue();
                upyun.setContentMD5(UpYun.md5(file));
                boolean result = upyun.writeFile(username + "/" + ShortUID + "." + entry.getKey(), file, true);
                if(result){
                    returnImage.setImgName(username + "/" + ShortUID + "." + entry.getKey());
                    returnImage.setImgUrl(key.getRequestAddress() + "/" +username + "/" + ShortUID + "." + entry.getKey());
                    returnImage.setImgSize(entry.getValue().length());
                    returnImage.setCode("200");
                }else{
                    System.err.println("上传失败");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            returnImage.setCode("500");
        }
        return returnImage;

    }

    //初始化
    public static Integer Initialize(Keys k) {
        int ret = -1;
        if(k.getStorageType()!=null && k.getAccessKey() != null && k.getAccessSecret() != null && k.getBucketName() != null
                && k.getRequestAddress() !=null ) {
            if(!k.getStorageType().equals("") && !k.getAccessKey().equals("") && !k.getAccessSecret().equals("") && !k.getBucketName().equals("")
                    && !k.getRequestAddress().equals("") ) {
                // 初始化
                // 创建UpYun实例。
                UpYun upyun = new UpYun(k.getBucketName(), k.getAccessKey(), k.getAccessSecret());
                List<UpYun.FolderItem> items = null;
                try {
                    items = upyun.readDir("/",null);
                    ret = 1;
                    upyun = upyun;
                    key = k;
                } catch (Exception e) {
                    System.out.println("USS Object Is null");
                    ret = -1;
                }
            }
        }
        return ret;
    }

    public Boolean delUSS(Integer keyID, String fileName) {
        boolean b = true;
        try {
            boolean result = upyun.deleteFile(fileName, null);
        } catch (Exception e) {
            e.printStackTrace();
            b=false;
        }
        return b;
    }


}
