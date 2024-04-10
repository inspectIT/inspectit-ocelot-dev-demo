package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RandomService {

    @Autowired
    private MyService serviceCaller;

    public String getUser() {
        return "This is random!";
    }

    public int getVeryBigData(String url) {
        int code = 400;
        try {
            code = this.serviceCaller.callAnotherService(url);
        } catch (Exception e) {
            System.out.println("### SERVICE CALL FAILED ###");
        }
        return code;
    }
}
