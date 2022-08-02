package com.lzy.springcloud.controller;

import com.lzy.springcloud.entity.CommonResult;
import com.lzy.springcloud.entity.Payment;

import com.lzy.springcloud.lb.MyRuleImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.net.URI;
import java.util.List;

@RestController
@Slf4j
public class OrderController {

//    public static final String  PAYMENT_URL = "http://localhost:8001";
    public static final String  PAYMENT_URL = "http://CLOUD-PAYMENT-SERVICE";

    @Resource
    private DiscoveryClient discoveryClient;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    MyRuleImpl myRule;


    @PostMapping("/consumer/payment/create")
    public CommonResult<Payment> create(Payment payment){
             return restTemplate.postForObject(PAYMENT_URL+"/payment/create", payment, CommonResult.class);
    }

    @GetMapping("/consumer/payment/get/{id}")
    public CommonResult<Payment> getPaymentById(@PathVariable("id") long id){
            return restTemplate.getForObject(PAYMENT_URL+"/payment/get/"+id,CommonResult.class);
    }

    @GetMapping("/consumer/payment/getEntity/{id}")
    public CommonResult<Payment> getPaymentById2(@PathVariable("id") long id){
        ResponseEntity<CommonResult> entity = restTemplate.getForEntity(PAYMENT_URL + "/payment/get/" + id, CommonResult.class);
        HttpStatus code = entity.getStatusCode();
        if (code.is2xxSuccessful()){
            return entity.getBody();
        }
        return new CommonResult<Payment>(entity.getStatusCodeValue(), "查询错误");
    }

    @GetMapping("/consumer/payment/port")
    public String getServerPort(){

        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");

        ServiceInstance serviceInstance = myRule.getServiceInstance(instances);

        URI uri = serviceInstance.getUri();

        log.info(uri.toString());

        return restTemplate.getForObject(uri+"/payment/port", String.class);
    }
}
