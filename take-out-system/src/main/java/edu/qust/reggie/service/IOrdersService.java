package edu.qust.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import edu.qust.reggie.entity.Orders;

/**
 * 
 * @version 1.0
 * @description 订单模块 - 业务层接口
 */

public interface IOrdersService extends IService<Orders> {

    /**
     * 用户下单
     * 操作三张表：订单表、订单明细表、购物车表  - 需要开启事务
     * @param orders 订单对象 json -> java 对象
     */
    void submit(Orders orders);
}
