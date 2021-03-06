package cn.hellohao.service.impl;

import cn.hellohao.mapper.*;
import cn.hellohao.model.entity.*;
import cn.hellohao.service.ImgAndAlbumService;
import cn.hellohao.service.SysConfigService;
import cn.hellohao.util.*;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baidu.aip.contentcensor.AipContentCensor;
import com.baidu.aip.contentcensor.EImgType;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * @author Hellohao
 * @version 1.0
 * @date 2021/10/28 16:38
 */
@Service
@RequiredArgsConstructor
public class ClientService {

    
    private final ImgAndAlbumService imgAndAlbumService;
    
    private final NOSImageupload nOSImageupload;
    
    private final OSSImageupload ossImageupload;
    
    private final USSImageupload ussImageupload;
    
    private final KODOImageupload kodoImageupload;
    
    private final COSImageupload cosImageupload;
    
    private final SysConfigService sysConfigService;
    
    private final FTPImageupload ftpImageupload;
    
    private final UFileImageupload uFileImageupload;
    
    private final UserMapper userMapper;
    
    private final KeysMapper keysMapper;
    
    private final ConfigMapper configMapper;
    
    private final UploadConfigMapper uploadConfigMapper;
    
    private final ImgMapper imgMapper;
    
    ImgreviewMapper imgreviewMapper;


    public Msg uploadImg(HttpServletRequest request, MultipartFile multipartFile, String email, String pass ){
        Msg msg = new Msg();
        try {
            String sourceKeyId  ;
            FileInputStream fis = null;
            String md5key = null;
            Integer setday = 0;
            JSONObject jsonObject = new JSONObject();
            Config config = configMapper.getSourceype();
            String userip = GetIPS.getIpAddr(request);
            UploadConfig uploadConfig = uploadConfigMapper.getUpdateConfig();
            if (uploadConfig.getApi() != 1) {
                msg.setCode("4003");
                msg.setInfo("??????????????????API??????");
                return msg;
            }
            File file = SetFiles.changeFile_new(multipartFile);
            SysUser u2 = new SysUser();
            if (!file.exists() || email == null || pass == null) {
                msg.setCode("4005");
                msg.setInfo("????????????????????????");
                return msg;
            }
            u2.setEmail(email);
            u2.setPassword(Base64Encryption.encryptBASE64(pass.getBytes()));
            SysUser u = userMapper.getUsers(u2);
            //???????????????????????????????????????????????????
            if (null == u || u.getIsok() != 1) {
                msg.setCode("4006");
                msg.setInfo("?????????????????????,????????????");
                return msg;
            }
            String imguid = UUID.randomUUID().toString().replace("-", "");
            //??????????????????????????????????????????
            Msg msg1 = updateImgCheck(u, uploadConfig);
            if (!msg1.getCode().equals("300")) {
                return msg1;
            }
            sourceKeyId = siteGroup.getKeyID();
            StorageKey key = keysMapper.selectKeys(sourceKeyId);
            Long tmp = (memory == -1 ? -2 : UsedTotleMemory);
            if (tmp >= memory) {
                msg.setCode("4007");
                msg.setInfo(u == null ? "?????????????????????" : "????????????????????????");
                return msg;
            }
            if (file.length() > TotleMemory) {
                System.err.println("???????????????" + file.length());
                System.err.println("???????????????" + TotleMemory);
                msg.setCode("4008");
                msg.setInfo("??????????????????????????????");
                return msg;
            }
            try {
                fis = new FileInputStream(file);
                md5key = DigestUtils.md5Hex(fis);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("?????????????????????MD5,??????UUID");
            }
            Msg fileMiME = TypeDict.FileMiME(file );
            if (!fileMiME.getCode().equals("200")) {
                //???????????????
                msg.setCode("4009");
                msg.setInfo(fileMiME.getInfo());
                return msg;
            }
            if (md5key == null || md5key.equals("")) {
                md5key = UUID.randomUUID().toString().replace("-", "");
            }
            String prefix = file.getName().substring(file.getName().lastIndexOf(".") + 1);
            if (uploadConfig.getBlacklist() != null) {
                String[] iparr = uploadConfig.getBlacklist().split(";");
                for (String s : iparr) {
                    if (s.equals(userip)) {
                        msg.setCode("4010");
                        msg.setInfo("?????????????????????");
                        return msg;
                    }
                }
            }
            //????????????????????????
            if(Integer.valueOf(sysConfigService.getstate().getCheckduplicate())==1) {
                Images imaOBJ = new Images();
                imaOBJ.setMd5key(md5key);
                imaOBJ.setUserId(u.getId());
                if (imgMapper.md5Count(imaOBJ) > 0) {
                    Images images = imgMapper.selectImgUrlByMD5(md5key);
                    jsonObject.put("url", images.getImgUrl());
                    jsonObject.put("name", file.getName());
                    jsonObject.put("size", images.getSizes());
                    msg.setData(jsonObject);
                    return msg;
                }
            }
            Map<String, File> map = new HashMap<>();
            String fileName = file.getName();
            if (file.exists()) {
                map.put(prefix, file);
            }
            long stime = System.currentTimeMillis();
            Map<ReturnImage, Integer> m = null;
            ReturnImage returnImage = GetSource.storageSource(key.getStorageType(), map, updatePath,  key.getId());
            Images img = new Images();
            DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            if (returnImage.getCode().equals("200")) {
                String imgurl = returnImage.getImgUrl();
                Long imgsize = returnImage.getImgSize();
                String imgname = returnImage.getImgName();
                img.setImgUrl(imgurl);
                img.setUpdateTime(LocalDateTime.now());
                img.setSource(key.getId());
                img.setUserId(u == null ? "0": u.getId());
                img.setSizes(imgsize.intValue());
                if (uploadConfig.getUrlType() == 2) {
                    img.setImgName(imgname);
                } else {
                    img.setImgName(SetText.getSubString(imgname, key.getRequestAddress() + "/", ""));
                }
                img.setImgType(setday > 0 ? 1 : 0);
                img.setAbnormal(userip);
                img.setMd5key(md5key);
                img.setImgUid(imguid);
                img.setFormat(fileMiME.getData().toString());
                imgMapper.insert(img);
                long etime = System.currentTimeMillis();
                Print.Normal("??????????????????????????????" + String.valueOf(etime - stime) + "ms");
                jsonObject.put("url", img.getImgUrl());
                jsonObject.put("name", imgname);
                jsonObject.put("size", img.getSizes());
                //??????????????????
                new Thread(() -> {
                    LegalImageCheck(img);
                }).start();
            } else {
                msg.setCode("5001");
                msg.setInfo("????????????????????????");
                return msg;
            }
            file.delete();
            msg.setData(jsonObject);
//            ???????????????=========
            return msg;
        }catch (Exception e){
            e.printStackTrace();
            msg.setCode("5001");
            msg.setInfo("Error for server:500");
            return msg;
        }

    }


    public static SiteGroup siteGroup; //????????????????????????????????????
    public static Long memory;//??????????????????????????????????????? memory
    public static Long TotleMemory;//?????????????????????????????????????????? //maxsize
    public static Long UsedTotleMemory;//?????????????????????????????????????????? //usermemory
    public static String updatePath="tourist";

    //???????????? ??? ?????? ????????????????????????????????????
     Msg updateImgCheck(SysUser sysUser, UploadConfig uploadConfig){
        final Msg msg = new Msg();
        DateTimeFormatter dateFormat =DateTimeFormatter.ofPattern("yyyy/MM/dd");
        try {
            if (sysUser == null) {
                //?????????????????????????????????????????????????????????
                if(uploadConfig.getIsUpdate()!=1){
                    msg.setCode("1000");
                    msg.setInfo("???????????????????????????");
                    return msg;
                }
                siteGroup = GetCurrentSource.GetSource(null);
                memory = Long.valueOf(uploadConfig.getVisitorStorage());//?????? B ??????????????????
                TotleMemory = Long.valueOf(uploadConfig.getFileSizeTourists());//?????? B  ?????????????????????
                UsedTotleMemory = imgMapper.getUserMemory("0")==null?0L : imgMapper.getUserMemory("0");//?????? B
            } else {
                //???????????????????????????
                if(uploadConfig.getUserclose()!=1){
                    msg.setCode("1001");
                    msg.setInfo("???????????????????????????");
                    return msg;
                }
                updatePath = sysUser.getUsername();
                siteGroup = GetCurrentSource.GetSource(sysUser.getId());
                memory = Long.valueOf(sysUser.getMemory())*1024*1024;//?????? B
                TotleMemory = Long.valueOf(uploadConfig.getFileSizeUser());//?????? B
                UsedTotleMemory = imgMapper.getUserMemory(sysUser.getId())==null?0L:imgMapper.getUserMemory(sysUser.getId());//?????? B
            }
            //???????????????????????????????????????
            if (uploadConfig.getUrlType() == 2) {
                updatePath = dateFormat.format(LocalDateTime.now());
            }
            msg.setCode("300");
        } catch (Exception e) {
            e.printStackTrace();
            msg.setCode("500");
        }
        return msg;
    }

    //????????????

      synchronized void LegalImageCheck(Images images){
        System.out.println("??????????????????????????????");
        Imgreview imgreview = null;
        try {
            imgreview = imgreviewMapper.selectByusing(1);
        } catch (Exception e) {
            Print.warning("???????????????????????????????????????");
            e.printStackTrace();
        }
        LegalImageCheckForBaiDu(imgreview,images);
    }

      void LegalImageCheckForBaiDu(Imgreview imgreview,Images images){
        if(imgreview.getUsing()==1){
            try {
                AipContentCensor client = new AipContentCensor(imgreview.getAppId(), imgreview.getApiKey(), imgreview.getSecretKey());
                client.setConnectionTimeoutInMillis(5000);
                client.setSocketTimeoutInMillis(30000);
                org.json.JSONObject res = client.imageCensorUserDefined(images.getImgUrl(), EImgType.FILE, null);
                res = client.imageCensorUserDefined(images.getImgUrl(), EImgType.URL, null);
                com.alibaba.fastjson.JSONArray jsonArray = JSON.parseArray("[" + res.toString() + "]");
                for (Object o : jsonArray) {
                    JSONObject jsonObject = (JSONObject) o;
                    com.alibaba.fastjson.JSONArray data = jsonObject.getJSONArray("data");
                    Integer conclusionType = jsonObject.getInteger("conclusionType");
                    if(conclusionType!=null){
                        if (conclusionType == 2) {
                            for (Object datum : data) {
                                JSONObject imgdata = (JSONObject) datum;
                                if (imgdata.getInteger("type") == 1) {
                                    Images img = new Images();
                                    img.setImgName(images.getImgName());
                                    //??????????????????????????????ID???????????????????????????????????????????????????
                                    img.setViolation("1[1]");
                                    imgMapper.setImg(img);
                                    Imgreview imgv = new Imgreview();
                                    imgv.setId("1");
                                    Integer count = imgreview.getCount();
                                    System.out.println("?????????????????????" + count);
                                    imgv.setCount(count + 1);
                                    imgreviewMapper.updateByPrimaryKeySelective(imgv);
                                    System.err.println("???????????????????????????????????????");
                                }
                            }
                        }
                    }
                }

            }catch (Exception e){
                System.out.println("?????????????????????????????????????????????");
                e.printStackTrace();

            }

        }
    }


}
