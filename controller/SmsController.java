package com.upwork.newsports.controller;

import com.upwork.newsports.service.SmsService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sms")
public class SmsController {

    private final SmsService smsService;


    public SmsController(SmsService smsService) {
        this.smsService = smsService;
    }

    @PostMapping("/send")
    public String sendSms(@RequestBody SmsFormat smsFormat) {
        smsService.sendSms(smsFormat.getTo(), smsFormat.getBody());
        return "SMS sent successfully to " + smsFormat.getTo();
    }

}
@Data
class SmsFormat{
    // i will delete this TODO
    private String to;
    private String body;
}

