package com.lzy.springcloud.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lzy.springcloud.entity.Payment;
import org.springframework.stereotype.Service;
import com.lzy.springcloud.mapper.PaymentMapper;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PaymentService extends ServiceImpl<PaymentMapper, Payment> {

}
