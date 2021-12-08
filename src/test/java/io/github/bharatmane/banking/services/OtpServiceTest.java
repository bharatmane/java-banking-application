package io.github.bharatmane.banking.services;


import io.github.bharatmane.banking.exception.InvalidCredentialsException;
import io.github.bharatmane.banking.exception.InvalidOtpException;
import io.github.bharatmane.banking.wrapper.UnirestWrapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
class OtpServiceTest {

    private OtpService otpService;
    public static final String API_KEY = "SGPkW7Zw5gBzRA3mUuQtlyvDc1qJCXeH908IoL64E2parVMKYOTZE5l8WMSmeFqtghKkdv9cwoUsuj4p";
    public static final String FAST2SMS_API_URL = "https://www.fast2sms.com/dev/bulkV2";


    @Mock
    private UnirestWrapper unirestWrapper;


    @BeforeEach
    public void init() {
        unirestWrapper = mock(UnirestWrapper.class);
        this.otpService = new OtpService(unirestWrapper);
    }

    @Test
    @DisplayName("Should have OTP count 1 when OTP was sent")
    void  shouldHaveOtpCount1WhenOtpSent() {

        String otp = otpService.generateOTP();
        when(unirestWrapper.sendOTP(otp,"9632104315")).thenReturn("v9lexpf7io5a8m4");

        //Given
        otpService.sendOTP(otp,"9632104315");
        //When

        //Then
        assertThat(otpService.getQueueSize()).isEqualTo(1);
    }

    @Test
    @DisplayName("Should validate OTP when compared with requestId")
    void  shouldValidateOtpWhenComparedWithRequestId() throws InvalidOtpException {

        String otp = otpService.generateOTP();
        String requestId = "v9lexpf7io5a8m4";
        when(unirestWrapper.sendOTP(otp,"9632104315")).thenReturn(requestId);

        //Given
        otpService.sendOTP(otp,"9632104315");
        //When

        //Then
        assertThat(otpService.validateOtp(otp,requestId)).isTrue();
    }
    @Test
    @DisplayName("Should throw invalid Otp exception when given wrong otp")
    void  shouldThrowInvalidOtpExceptionWhenGivenWrongOtp() {

        String otp = otpService.generateOTP();
        String wrongOtp = "12345";
        String requestId = "v9lexpf7io5a8m4";
        when(unirestWrapper.sendOTP(otp,"9632104315")).thenReturn(requestId);

        //Given
        otpService.sendOTP(otp,"9632104315");
        //When

        //Then
        assertThrows(InvalidOtpException.class, () -> otpService.validateOtp(wrongOtp,requestId));
    }
}
