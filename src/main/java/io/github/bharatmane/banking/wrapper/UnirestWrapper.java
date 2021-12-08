package io.github.bharatmane.banking.wrapper;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONObject;

import java.text.MessageFormat;

public class UnirestWrapper {
    public static final String API_KEY = "SGPkW7Zw5gBzRA3mUuQtlyvDc1qJCXeH908IoL64E2parVMKYOTZE5l8WMSmeFqtghKkdv9cwoUsuj4p";
    public static final String FAST2SMS_API_URL = "https://www.fast2sms.com/dev/bulkV2";

    public String sendOTP(String otp, String phoneNumber){
        String url =  MessageFormat.format( "{0}?authorization={1}&variables_values={2}&route=otp&numbers={3}"
                ,FAST2SMS_API_URL,API_KEY,otp,phoneNumber);

        HttpResponse<JsonNode> response = Unirest.get(url)
                .header("cache-control", "no-cache")
                .asJson();
        JSONObject myObj = response.getBody().getObject();
        return myObj.getString("request_id");
    }

}
