package io.github.bharatmane.banking.services;

import com.google.gson.JsonObject;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONObject;

import java.text.MessageFormat;
import java.util.HashMap;


public class OtpService {

    public static final String API_KEY = "SGPkW7Zw5gBzRA3mUuQtlyvDc1qJCXeH908IoL64E2parVMKYOTZE5l8WMSmeFqtghKkdv9cwoUsuj4p";
    public static final String FAST2SMS_API_URL = "https://www.fast2sms.com/dev/bulkV2";
    private HashMap<Integer,String> otpMap;

    public OtpService(){
        otpMap = new HashMap<Integer,String>();
    }

    public void sendOTP(String phoneNo) {
        String otp = generateOTP();
        String url =  MessageFormat.format( "{0}?authorization={1}&variables_values={2}&route=otp&numbers={3}"
                ,FAST2SMS_API_URL,API_KEY,otp,phoneNo);
        HttpResponse<JsonNode> response = Unirest.get(url)
                .header("cache-control", "no-cache")
                .asJson();
        JSONObject myObj = response.getBody().getObject();
        String requestId = myObj.getString("request_id");
        otpMap.put(Integer.parseInt(otp),requestId);
    }

    public static String generateOTP()
    {
        int randomPin = (int) (Math.random()*9000)+1000;
        return String.valueOf(randomPin);
    }

    public int getQueueSize() {
        return otpMap.size();
    }
}
