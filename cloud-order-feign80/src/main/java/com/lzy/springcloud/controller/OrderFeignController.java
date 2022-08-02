package com.lzy.springcloud.controller;

import com.lzy.springcloud.feign.PaymentFeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderFeignController {

    @Autowired
    PaymentFeignService paymentFeignService;

    @GetMapping("/consumer/payment/port")
    public String getServerPort(){
        return paymentFeignService.getServerPort();
    };
}
