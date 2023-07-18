package com.clh.util;

import cn.hutool.json.JSONUtil;
import com.clh.bean.TranslateResult;
import okhttp3.*;
import org.apache.log4j.Logger;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @Author cailinhu
 * @Description TODO
 * @Date 2023/7/17 17:13
 * @Version 1.0
 */
public class TranslateUtil {
    private static final Logger logger = Logger.getLogger(TranslateUtil.class);
    private static OkHttpClient httpClient = new OkHttpClient.Builder().build();
    private static  String APP_KEY ; // 您的应用ID
    private static  String APP_SECRET ;  // 您的应用密钥
    static {
        Properties properties = PropertiesUtil.properties;
        APP_KEY = properties.getProperty("youdao_app_key");
        APP_SECRET = properties.getProperty("youdao_app_secret");
    }
    public static TranslateResult getResult(String text)  {
        FormBody.Builder body = builderBody(text);
        Request request = getRequest(body);
        // 添加鉴权相关参数
        TranslateResult o =analyzeResp(request);
        return o;
    }

    private static Request getRequest(FormBody.Builder body) {
        Request.Builder builder = new Request.Builder().url("https://openapi.youdao.com/api");
        builder.method("POST", body.build());
        Request request = builder.build();
        return request;
    }

    /**
     * 解析返回数据
     * @param
     * @return
     */
    private static TranslateResult analyzeResp(Request request) {
        try (Response response = httpClient.newCall(request).execute()){
            ResponseBody body = response.body();
            if (body != null) {
                String res = new String(body.bytes(), StandardCharsets.UTF_8);
                logger.info(res);
                return  JSONUtil.toBean(res,TranslateResult.class);
            }
        }catch (Exception e){
            logger.error("",e);
        }
        return null;
    }

    private static FormBody.Builder builderBody(String text) {
        String from = "auto";
        String to = "auto";
        FormBody.Builder formBodyBuilder = new FormBody.Builder(StandardCharsets.UTF_8);
        Map<String, String[]> params = new HashMap<String, String[]>() {{
             put("q", new String[]{text});
             put("from", new String[]{from});
             put("to", new String[]{to});
         }};
        try {
            AuthV3Util.addAuthParams(APP_KEY, APP_SECRET, params);
            for (String key : params.keySet()) {
                String[] values = params.get(key);
                if (values != null) {
                    for (String value : values) {
                        formBodyBuilder.add(key, value);
                    }
                }
            }
        }catch (Exception e){
            logger.error("",e);
        }

        return formBodyBuilder;
    }
}
