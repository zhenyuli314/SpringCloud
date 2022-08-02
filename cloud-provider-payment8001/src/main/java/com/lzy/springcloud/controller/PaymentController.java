package com.lzy.springcloud.controller;

import com.lzy.springcloud.entity.CommonResult;
import com.lzy.springcloud.entity.Payment;
import com.lzy.springcloud.service.PaymentService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@Slf4j
public class PaymentController {
    @Autowired
    PaymentService paymentService;

    @Value("${server.port}")
    private String serverPort;

    @Resource
    private DiscoveryClient discoveryClient;

    @PostMapping("/payment/create")
    public CommonResult<Payment> create(@RequestBody Payment payment){
        boolean save = paymentService.save(payment);

        if (save){
            return new CommonResult<Payment>(200 , "插入数据成功:"+serverPort,payment);
        }
        return new CommonResult<Payment>(444,"插入数据失败:"+serverPort,payment);
    }

    @GetMapping("/payment/get/{id}")
    public CommonResult<Payment> getPaymentById(@PathVariable("id") long id){

        Payment payment = paymentService.getById(id);
        log.info("查询结果为：",payment);
        if (payment!=null){
            return new  CommonResult<Payment>(200,"查询成功:"+serverPort,payment);
        }
        return new CommonResult<Payment>(444,"查询数据失败:"+serverPort,payment);
    }

    @GetMapping("/payment/discovery")
    public Object getDiscovery(){
        List<String> services = discoveryClient.getServices();
        for (String service : services) {
            log.info("****:"+service);
        }
        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        for (ServiceInstance instance : instances) {
            log.info("===="+instance.getInstanceId()+"==="+instance.getUri());
        }
        return discoveryClient;
    }

    @GetMapping("/payment/port")
    public String getServerPort(){
         return serverPort;
    }
}
