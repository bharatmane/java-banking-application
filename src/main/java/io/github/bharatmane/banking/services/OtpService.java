package io.github.bharatmane.banking.services;

import io.github.bharatmane.banking.exception.InvalidOtpException;
import io.github.bharatmane.banking.wrapper.UnirestWrapper;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Random;


public class OtpService {

    private final HashMap<Integer,String> otpMap;
    private final Random rand;
    private final UnirestWrapper unirestWrapper;
    public OtpService(UnirestWrapper unirestWrapper){
        otpMap = new HashMap<>();
        rand = new Random();
        this.unirestWrapper =  unirestWrapper;
    }

    public String sendOTP(String otp, String phoneNo) {
        String requestId = unirestWrapper.sendOTP(otp,phoneNo);
        otpMap.put(Integer.parseInt(otp),requestId);
        return requestId;
    }

    public  String generateOTP()
    {
        return new DecimalFormat("000000").format(rand.nextInt(999999));
    }

    public int getQueueSize() {
        return otpMap.size();
    }

    public boolean validateOtp(String otp, String requestId) throws InvalidOtpException {
        int intOtp = Integer.parseInt(otp);
        var result = this.otpMap.entrySet().stream() .filter(c-> c.getKey().equals(intOtp)).findFirst()
                .orElse(null);
        if(result == null || result.getValue().equals(requestId) == false)
            throw new InvalidOtpException("Invalid OTP, please try again");
        return true;
    }
}
