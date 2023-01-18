package tiers.app.customer.config.sms;

import ClickSend.ApiClient;
import java.util.Arrays;
import java.util.List;
import ClickSend.Api.SmsApi;
import ClickSend.ApiException;
import ClickSend.Model.SmsMessage;
import ClickSend.Model.SmsMessageCollection;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SmsService {
    @Value("${tiers.app.sms.api.user}")
    private String apiUser;

    @Value("${tiers.app.sms.api.key}")
    private String apiKey;

    public void sendSMS(String smsBody, String to) {

        ApiClient defaultClient = new ApiClient();
        defaultClient.setUsername(apiUser);
        defaultClient.setPassword(apiKey);
        SmsApi apiInstance = new SmsApi(defaultClient);

        SmsMessage smsMessage=new SmsMessage();
        smsMessage.body(smsBody);
        smsMessage.to(to);
        smsMessage.source("TiersApp");

        List<SmsMessage> smsMessageList=Arrays.asList(smsMessage);
        // SmsMessageCollection | SmsMessageCollection model
        SmsMessageCollection smsMessages = new SmsMessageCollection();
        smsMessages.messages(smsMessageList);

        try {
           String result = apiInstance.smsSendPost(smsMessages);
           System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling SmsApi#smsSendPost");
            e.printStackTrace();
        }
    }

}
