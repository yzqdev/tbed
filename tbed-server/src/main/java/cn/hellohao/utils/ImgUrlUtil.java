package cn.hellohao.utils;

import cn.hellohao.TbedApplication;
import cn.hellohao.pojo.Msg;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

/*
操作网络url图片工具类
* */
public class ImgUrlUtil {

    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder();
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }
    /**
     * 获取网络文件大小
     */
    public static long getFileLength(String downloadUrl) throws IOException {
        if(downloadUrl == null || "".equals(downloadUrl) ||downloadUrl.length()<=7){
            return 0L ;
        }
        URL url = new URL(downloadUrl);
        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("HEAD");
            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows 7; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/47.0.2526.73 Safari/537.36 YNoteCef/5.8.0.1 (Windows)");
            return (long) conn.getContentLength();
        } catch (IOException e) {
            return 0L;
        } finally {
            conn.disconnect();
        }
    }

    /*
     * 检测Url的响应值
     * 判断是否能访问。
     * */
    public static Map<String,String> checkURLStatusCode(String urlStr){
        Map<String,String> map = new HashMap<>();
        try {
            URL url = new URL(urlStr);
            URLConnection rulConnection   = url.openConnection();
            HttpURLConnection httpUrlConnection  =  (HttpURLConnection) rulConnection;
            httpUrlConnection.setConnectTimeout(300000);
            httpUrlConnection.setReadTimeout(300000);
            httpUrlConnection.connect();
            String code = new Integer(httpUrlConnection.getResponseCode()).toString();
            String message = httpUrlConnection.getResponseMessage();
            System.out.println("getResponseCode code ="+ code);
            System.out.println("getResponseMessage message ="+ message);
            if(!code.startsWith("2") && !code.startsWith("3")){
                map.put("Check","false");
                map.put("StatusCode",code);
                throw new Exception("ResponseCode is not begin with 2,code="+code);
            }
            map.put("Check","true");
            System.out.println("连接正常");
        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }

        return map;
    }


    /**
     * 从网络Url中下载文件
     * @param urlStr
     * @param fileName
     * @param savePath
     * @throws IOException
     */
    public static Map<String ,Object> downLoadFromUrl(String urlStr, String fileName, String savePath){

        Map<String ,Object> resmap = new HashMap<>();
        Map<String ,String> map = checkURLStatusCode(urlStr);
        if(map.get("Check").equals("false")){
//            StatusCode
            resmap.put("res",false);
            resmap.put("StatusCode",map.get("StatusCode"));
            return resmap;
        }
        try {
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            //设置超时间为3秒
            conn.setConnectTimeout(5*1000);
            //防止屏蔽程序抓取而返回403错误
            conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");

            //得到输入流
            InputStream inputStream = conn.getInputStream();
            //获取自己数组
            byte[] getData = readInputStream(inputStream);
            File saveDir = new File(savePath);
            if(!saveDir.exists()){
                saveDir.mkdir();
            }
            File file = new File(saveDir+File.separator+fileName);
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(getData);
            if(fos!=null){
                fos.close();
            }
            if(inputStream!=null){
                inputStream.close();
            }
            //下载并且保存成功后 判断格式 如果不是图像格式 就删除
//
            if(new File(saveDir+File.separator+fileName).exists()){
                Msg msg = TypeDict.FileMiME(file,null);
                if(msg.getCode().equals("200")){
                    File f = new File(saveDir+File.separator+fileName);
                    String imgPath = saveDir+File.separator+fileName+"."+(msg.getData().toString().replace("image/",""));
                    f.renameTo(new File(imgPath));
                    resmap.put("res",true);
                    resmap.put("imgPath",imgPath);
                }else{
                    new File(saveDir+File.separator+fileName).delete();
                    resmap.put("res",false);
                    resmap.put("StatusCode","110403");
                }
            }else{
                resmap.put("res",false);
                resmap.put("StatusCode","500");
            }


            return resmap;
        }catch (Exception e){
            e.printStackTrace();
            resmap.put("res",false);
            resmap.put("StatusCode","500");
            return resmap;
        }
    }


    /**
     * 从输入流中获取字节数组
     * @param inputStream
     * @return
     * @throws
     */
    public static  byte[] readInputStream(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int len = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while((len = inputStream.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
        bos.close();
        return bos.toByteArray();
    }

    /**
     * 根据java.io.*的流获取文件大小
     * @param file
     */
    public static Integer getFileSize2(File file){
        Integer imgsize = 0;
        FileInputStream fis = null;
        try {
            if(file.exists() && file.isFile()){
                String fileName = file.getName();
                fis = new FileInputStream(file);
                imgsize=fis.available()/1024;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            if(null!=fis){
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
return imgsize;
    }

}
