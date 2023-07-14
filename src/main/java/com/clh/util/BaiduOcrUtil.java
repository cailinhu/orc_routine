package com.clh.util;

import cn.hutool.core.io.resource.ClassPathResource;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.clh.bean.OrcResult;
import okhttp3.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Properties;

/**
 * @Author cailinhu
 * @Description TODO
 * @Date 2023/2/23 10:36
 * @Version 1.0
 */
public class BaiduOcrUtil {
    private  static  String CLIENT_ID;
    private  static String CLIENT_SECRET;
    static {
        try {
            ClassPathResource resource = new ClassPathResource("ocr_config.properties");
            Properties properties = new Properties();
            properties.load(resource.getStream());
            CLIENT_ID=properties.getProperty("client_id");
            CLIENT_SECRET=properties.getProperty("client_secret");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static String accessToken;
    static final OkHttpClient HTTP_CLIENT = new OkHttpClient().newBuilder().build();
    private static String getAccessToken(){
        if (StrUtil.isEmpty(accessToken)){
            HttpResponse execute = HttpRequest.post("https://aip.baidubce.com/oauth/2.0/token?client_id=" + CLIENT_ID + "&client_secret=" + CLIENT_SECRET + "&grant_type=client_credentials").execute();
            if (execute.getStatus()==200) {
                String body = execute.body();
                JSONObject entries = JSONUtil.parseObj(body);
                String access_token = entries.get("access_token").toString();
                accessToken=access_token;
            }
        }
        return accessToken;
    }

    public static OrcResult orcResult(String path) throws IOException {
        String code = getFileContentAsBase64Urlencoded(path);
        OrcResult orcResult = getOrcResult(code);
        return orcResult;
    }

    public static OrcResult orcResult(OutputStream outputStream) throws IOException {
        String code = getFileContentAsBase64Urlencoded(outputStream);
        OrcResult orcResult = getOrcResult(code);
        return orcResult;
    }

    private static OrcResult getOrcResult( String code) throws IOException {
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.create(mediaType, "image="+ code);
        // image 可以通过 getFileContentAsBase64("C:\fakepath\1677051269652.jpg") 方法获取
        // 如果Content-Type是application/x-www-form-urlencoded时,使用getFileContentAsBase64Urlencoded方法获取
        Request request = new Request.Builder()
                .url("https://aip.baidubce.com/rest/2.0/ocr/v1/general_basic?access_token=" + getAccessToken())
                .method("POST", body)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .addHeader("Accept", "application/json")
                .build();
        Response response = HTTP_CLIENT.newCall(request).execute();
        String s = response.body().string();
        OrcResult orcResult = JSONUtil.toBean(s, OrcResult.class);
        return orcResult;
    }

    /**
     * 获取文件base64 UrlEncode编码
     * @param path 文件路径
     * @return base64编码信息，不带文件头
     * @throws IOException IO异常
     */
    static String getFileContentAsBase64Urlencoded(String path) throws IOException {
        return URLEncoder.encode(getFileContentAsBase64(path), "utf-8");
    }
    /**
     * 获取文件base64 UrlEncode编码
     * @param outputStream 图片流
     * @return base64编码信息，不带文件头
     * @throws IOException IO异常
     */
    static String getFileContentAsBase64Urlencoded(OutputStream outputStream) throws IOException {
        return URLEncoder.encode(getFileContentAsBase64(outputStream), "utf-8");
    }

    /**
     * 获取文件base64编码
     * @param path 文件路径
     * @return base64编码信息，不带文件头
     * @throws IOException IO异常
     */
    static String getFileContentAsBase64(String path) throws IOException {
        byte[] b = Files.readAllBytes(Paths.get(path));
        return Base64.getEncoder().encodeToString(b);
    }

    /**
     * 获取文件base64编码
     * @param outputStream 图片流
     * @return base64编码信息，不带文件头
     * @throws IOException IO异常
     */
    static String getFileContentAsBase64(OutputStream outputStream) throws IOException {
        ByteArrayOutputStream os = (ByteArrayOutputStream)outputStream;
        return Base64.getEncoder().encodeToString(os.toByteArray());
    }


}
