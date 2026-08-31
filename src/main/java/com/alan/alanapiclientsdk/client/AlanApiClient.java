package com.alan.alanapiclientsdk.client;

import cn.hutool.core.util.IdUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONUtil;
import com.alan.alanapiclientsdk.model.User;
import com.alan.alanapiclientsdk.utils.SignUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * 调用 alanapi 开放接口的客户端
 *
 * @author ALan
 * @date 2026/8/31 23:09
 *
 */
@Slf4j
public class AlanApiClient {

    /**
     * 默认网关地址（本地接口服务）
     */
    public static final String DEFAULT_GATEWAY_HOST = "http://localhost:8123/api";

    private final String accessKey;

    private final String secretKey;

    private final String gatewayHost;

    public AlanApiClient(String accessKey, String secretKey) {
        this(accessKey, secretKey, DEFAULT_GATEWAY_HOST);
    }

    public AlanApiClient(String accessKey, String secretKey, String gatewayHost) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
        this.gatewayHost = gatewayHost;
    }

    /**
     * 构造签名请求头，secretKey 只参与本地签名计算，一定不能随请求发送
     */
    private Map<String, String> getHeaderMap(String body) {
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("accessKey", accessKey);
        headerMap.put("body", body);
        headerMap.put("nonce", IdUtil.simpleUUID());
        headerMap.put("timestamp", String.valueOf(System.currentTimeMillis() / 1000));
        headerMap.put("sign", SignUtils.genSign(headerMap, secretKey));
        return headerMap;
    }

    /**
     * 统一发送逻辑：给请求附加签名请求头后执行
     *
     * @param request 已配置好地址和参数的请求
     * @param body    参与签名的请求体内容（表单接口为参数值，JSON 接口为 JSON 串）
     * @return 响应内容
     */
    private String executeWithSign(HttpRequest request, String body) {
        getHeaderMap(body).forEach(request::header);
        return request.execute().body();
    }

    public String getNameByGet(String name) {
        String result = executeWithSign(HttpRequest.get(gatewayHost + "/name/").form("name", name), name);
        log.info("name:{},result:{}", name, result);
        return result;
    }

    public String getNameByPost(String name) {
        String result = executeWithSign(HttpRequest.post(gatewayHost + "/name/").form("name", name), name);
        log.info("name:{},result:{}", name, result);
        return result;
    }

    public String getUsernameByPost(User user) {
        String json = JSONUtil.toJsonStr(user);
        String result = executeWithSign(HttpRequest.post(gatewayHost + "/name/user").body(json), json);
        log.info("user:{},result:{}", user, result);
        return result;
    }

}
