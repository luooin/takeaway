package edu.qust.reggie.utils;


import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.github.qcloudsms.httpclient.HTTPException;
import org.json.JSONException;

/**
 * 
 * @version 1.0
 * @description 短信验证码发送的工具类
 */
public class SmsUtils {
    public static void sendMessage(String phoneNum, String code) throws Exception {

        String appKey = "43345791232eb793f86371db939dcf42";
        int appId = 1400763811;
        int templateId = 1603698;
        String smsSign = "ONEONE算法公众号";

        // 参数内容 根据你模板中有几个参数，按照对应的顺序放在数组中就行,这里只有一个参数
        String[] params = {code};
        try {
            SmsSingleSender sender = new SmsSingleSender(appId, appKey);
            // 签名参数未提供或者为空时，会使用默认签名发送短信
            SmsSingleSenderResult result = sender.sendWithParam("86", phoneNum,
                    templateId, params, smsSign, "", "");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
