package com.lzy.springcloud.lb;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class MyRuleImpl implements MyRule{

    private AtomicInteger atomicInteger = new AtomicInteger(0);


    public final int incrementAndGetModulo(){
        int current;
        int next;
        do {
            current = atomicInteger.get();
            next = current>=Integer.MAX_VALUE?0:current+1;
        }while (!atomicInteger.compareAndSet(current, next));

        return atomicInteger.get();
    }

    //负载均衡算法：rest接口第几次请求数 % 服务器集群总数量 = 实际调用服务器位置下标  ，每次服务重启动后rest接口计数从1开始。
    @Override
    public ServiceInstance getServiceInstance(List<ServiceInstance> serviceInstances) {

        if (serviceInstances==null || serviceInstances.size()==0){
            return null;
        }

        int index = incrementAndGetModulo()%serviceInstances.size();

        System.out.println("下标为"+index);

        return serviceInstances.get(index);
    }
}
