package com.lzy.springcloud.lb;

import org.springframework.cloud.client.ServiceInstance;

import java.util.List;

public interface MyRule {
    ServiceInstance getServiceInstance(List<ServiceInstance> serviceInstances);
}
