package io.github.bharatmane.banking.services;


import kong.unirest.GetRequest;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.text.MessageFormat;

import static org.powermock.api.mockito.PowerMockito.mockStatic;


@PrepareForTest({ Unirest.class})
@RunWith(PowerMockRunner.class)
public class OtpServiceTest {

    private OtpService otpService;
    public static final String API_KEY = "SGPkW7Zw5gBzRA3mUuQtlyvDc1qJCXeH908IoL64E2parVMKYOTZE5l8WMSmeFqtghKkdv9cwoUsuj4p";
    public static final String FAST2SMS_API_URL = "https://www.fast2sms.com/dev/bulkV2";


    @Mock
    private GetRequest getRequest;

    @Mock
    private HttpResponse<JsonNode> httpResponse;

    @Mock
    private JSONObject jsonObject;


    @BeforeEach
    public void init() {
        //this.otpService = new OtpService();
    }
    @Test
    @DisplayName("Should have OTP count 1 when OTP was sent")
    void  shouldHaveOtpCount1WhenOtpSent() {
//        String baseUrl =  MessageFormat.format( "{0}?authorization={1}"
//                ,FAST2SMS_API_URL,API_KEY);
//
//        String otp = OtpService.generateOTP();
//        String parameters = MessageFormat.format("&variables_values={0}&route=otp&numbers={1}",otp,"9632104315");
//
//        try (MockedStatic mocked = mockStatic(Unirest.class)) {
//            mocked.when(() -> Unirest.get("param1")).thenReturn(getRequest);
//        }
        //mockStatic(Unirest.class);
//        Mockito.when(Unirest.get(ArgumentMatchers.anyString())).thenReturn(getRequest);
//        when(getRequest.asJson()).thenReturn(httpResponse);
//        when(httpResponse.getBody().getObject()).thenReturn(jsonObject);
//        when(jsonObject.getString("request_id")).thenReturn("v9lexpf7io5a8m4");
//
//        //when(Unirest.get(baseUrl + parameters)).thenReturn(getRequest);
//
//
//        //Given
//        otpService.sendOTP("9632104315");
//        //When
//
//        //Then
//        assertThat(otpService.getQueueSize()).isEqualTo(1);
    }
}
