package com.myz.starters.notice.dingding;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiRobotSendRequest;
import com.dingtalk.api.response.OapiRobotSendResponse;
import com.taobao.api.ApiException;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
/**
 * @author yzMa
 * @desc
 * @date 2020/9/21 11:10
 * @email 2641007740@qq.com
 */
public class DingDingService {

    private static Logger log = LoggerFactory.getLogger(DingDingService.class);

    private DingDingInfo dingDingInfo;

    public DingDingService(DingDingInfo dingDingInfo){
        this.dingDingInfo = dingDingInfo;
        this.secret = dingDingInfo.getSecret();
        this.accessToken = dingDingInfo.getAccessToken();
    }

    private String secret = "SECfbb8d23fe3e3f4cb02383c8cb4a27435c719f3bdd4d3238ad79a7991175dfaf7";
    private String accessToken = "dbb8cf706c2b586bdfd6de275f9fd8104398554804f43e3d23f9428bb5c42399";

    private String webHook = "https://oapi.dingtalk.com/robot/send?access_token="+accessToken;

    private ExecutorService poolExecutor = Executors.newSingleThreadExecutor();

    private String getSign(Long timestamp) throws NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeyException {
        String secret = this.secret;
        String stringToSign = timestamp + "\n" + secret;
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(new SecretKeySpec(secret.getBytes("UTF-8"), "HmacSHA256"));
        byte[] signData = mac.doFinal(stringToSign.getBytes("UTF-8"));
        String sign = URLEncoder.encode(new String(Base64.encodeBase64(signData)),"UTF-8");
        return sign;
    }

    private void send(OapiRobotSendRequest request){
        try {
            Long timestamp = System.currentTimeMillis();
            String sign = getSign(timestamp);
            String targetUrl = webHook +"&timestamp="+timestamp+"&sign="+sign;
            DingTalkClient client = new DefaultDingTalkClient(targetUrl);

            OapiRobotSendResponse response = client.execute(request);
            log.info("response code={},msg={},errorCode={},errorMsg={}",response.getCode(),response.getMsg(),response.getErrcode(),response.getErrmsg());
        }catch (Exception e){
            log.error("send dingding msg request={} fail e",request.getTextParams(),e);
        }
    }

    /**
     * "#### 今日配置主建筑总金币数10000\n" +
     *                 "> 当前系统分配900\n\n" +
     *                 "> ![screenshot](https://gw.alicdn.com/tfs/TB1ut3xxbsrBKNjSZFpXXcXhFXa-846-786.png)\n"  +
     *                 "> ###### 可能原因：算力值较大人均算力比较均匀示例：个人金币（1算力*100金币/1000算力）=0 \n"
     * @param title
     * @param markdownText
     * @throws ApiException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws UnsupportedEncodingException
     */
    public void sendMarkdownMsg(String title,String markdownText) {
        OapiRobotSendRequest request = new OapiRobotSendRequest();
        request.setMsgtype("markdown");
        OapiRobotSendRequest.Markdown markdown = new OapiRobotSendRequest.Markdown();
        markdown.setTitle(title);
        markdown.setText(markdownText);
        request.setMarkdown(markdown);

        send(request);
    }

    public void sendAsyncMarkdownMsg(String title,String markdownText) {
        poolExecutor.submit(()->sendMarkdownMsg(title,markdownText));
    }

    public void sendMarkdownMsg(String title,String markdownText,String atPhone) {
        OapiRobotSendRequest request = new OapiRobotSendRequest();
        request.setMsgtype("markdown");
        OapiRobotSendRequest.Markdown markdown = new OapiRobotSendRequest.Markdown();
        markdown.setTitle(title);
        markdown.setText(markdownText);
        request.setMarkdown(markdown);

        OapiRobotSendRequest.At at = new OapiRobotSendRequest.At();
        at.setAtMobiles(Arrays.asList(atPhone));
        request.setAt(at);

        send(request);
    }

    public void sendAsyncMarkdownMsg(String title,String markdownText,String atPhone) {
        poolExecutor.submit(()->sendMarkdownMsg(title,markdownText,atPhone));
    }

    public void sendMarkdownMsgAtAll(String title,String markdownText) {
        OapiRobotSendRequest request = new OapiRobotSendRequest();
        request.setMsgtype("markdown");
        OapiRobotSendRequest.Markdown markdown = new OapiRobotSendRequest.Markdown();
        markdown.setTitle(title);
        markdown.setText(markdownText);
        request.setMarkdown(markdown);

        OapiRobotSendRequest.At at = new OapiRobotSendRequest.At();
        at.setIsAtAll(true);
        request.setAt(at);

        send(request);
    }

    public void sendAsyncMarkdownMsgAtAll(String title,String markdownText) {
        poolExecutor.submit(()->sendMarkdownMsgAtAll(title,markdownText));
    }

    public void sendLinkMsg(String title,String content,String msgUrl) {
        OapiRobotSendRequest request = new OapiRobotSendRequest();
        request.setMsgtype("link");

        OapiRobotSendRequest.Link link = new OapiRobotSendRequest.Link();
        link.setMessageUrl(msgUrl);
        link.setPicUrl("");
        link.setTitle(title);
        link.setText(content);
        request.setLink(link);

        send(request);
    }

    public void sendAsyncLinkMsg(String title,String content,String msgUrl){
        poolExecutor.submit(()->sendLinkMsg(title,content,msgUrl));
    }

    public void sendLinkMsg(String title,String content,String msgUrl,String atPhone) {
        OapiRobotSendRequest request = new OapiRobotSendRequest();
        request.setMsgtype("link");

        OapiRobotSendRequest.Link link = new OapiRobotSendRequest.Link();
        link.setMessageUrl(msgUrl);
        link.setPicUrl("");
        link.setTitle(title);
        link.setText(content);
        request.setLink(link);

        OapiRobotSendRequest.At at = new OapiRobotSendRequest.At();
        at.setAtMobiles(Arrays.asList(atPhone));
        request.setAt(at);

        send(request);
    }

    public void sendAsyncLinkMsg(String title,String content,String msgUrl,String atPhone) {
        poolExecutor.submit(()->sendAsyncLinkMsg(title,content,msgUrl,atPhone));
    }


    public void sendLinkMsgAtAll(String title,String content,String msgUrl){
        OapiRobotSendRequest request = new OapiRobotSendRequest();
        request.setMsgtype("link");

        OapiRobotSendRequest.Link link = new OapiRobotSendRequest.Link();
        link.setMessageUrl(msgUrl);
        link.setPicUrl("");
        link.setTitle(title);
        link.setText(content);
        request.setLink(link);

        OapiRobotSendRequest.At at = new OapiRobotSendRequest.At();
        at.setIsAtAll(true);
        request.setAt(at);

        send(request);
    }

    public void sendAsyncLinkMsgAtAll(String title,String content,String msgUrl){
        poolExecutor.submit(()->sendLinkMsgAtAll(title,content,msgUrl));
    }

    public void sendTextMsg(String content) {
        OapiRobotSendRequest request = new OapiRobotSendRequest();
        request.setMsgtype("text");
        OapiRobotSendRequest.Text textReq = new OapiRobotSendRequest.Text();
        textReq.setContent(content);
        request.setText(textReq);
        send(request);
    }

    public void sendAsyncTextMsg(String content) {
        poolExecutor.submit(()->sendTextMsg(content));
    }
    /**
     * 消息@某个手机对应的人
     * @param content
     * @param atPhone
     * @throws ApiException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws UnsupportedEncodingException
     */
    public void sendTextMsg(String content,String atPhone){
        OapiRobotSendRequest request = new OapiRobotSendRequest();
        request.setMsgtype("text");
        OapiRobotSendRequest.Text textReq = new OapiRobotSendRequest.Text();
        textReq.setContent(content);
        request.setText(textReq);

        OapiRobotSendRequest.At at = new OapiRobotSendRequest.At();
        at.setAtMobiles(Arrays.asList(atPhone));
        request.setAt(at);

        send(request);
    }

    public void sendAsyncTextMsg(String content,String atPhone){
        poolExecutor.submit(()->sendTextMsg(content,atPhone));
    }
    /**
     * 消息通知所有人
     * @param content
     * @throws ApiException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws UnsupportedEncodingException
     */
    public void sendTextMsgAtAll(String content) {
        OapiRobotSendRequest request = new OapiRobotSendRequest();
        request.setMsgtype("text");
        OapiRobotSendRequest.Text textReq = new OapiRobotSendRequest.Text();
        textReq.setContent(content);
        request.setText(textReq);

        OapiRobotSendRequest.At at = new OapiRobotSendRequest.At();
        at.setIsAtAll(true);
        request.setAt(at);

        send(request);
    }

    public void sendAsyncTextMsgAtAll(String content) {
        poolExecutor.submit(()->sendTextMsgAtAll(content));
    }

    public void main(String[] args){

//        sendTextMsg("这是text文本测试","15010025300");
//        sendLinkMsgAtAll("link title","这是link测试","https://www.baidu.com");
//        sendMarkdownMsg("markdown title","# 标题1 \n ## 标题2 \n 没换行在来一遍","https://www.baidu.com");
//        DingTalkService.sendAsyncLinkMsgAtAll("配置","未配置金额，去配置","https://www.baidu.com");

//        WzDingTalkUtil.sendAsyncMarkdownMsg("剩余金币预警","# 总金币："+123456+" \n ## 已分配："+123400+"\n ## 分片总数："+2+"\n ## 当前分片："+1+"\n ## 可分得金币："+0+"\n ");
    }
}
