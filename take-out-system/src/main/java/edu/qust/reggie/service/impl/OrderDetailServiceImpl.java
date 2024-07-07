package edu.qust.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.qust.reggie.entity.OrderDetail;
import edu.qust.reggie.mapper.OrderDetailMapper;
import edu.qust.reggie.service.IOrderDetailService;
import org.springframework.stereotype.Service;

/**
 * 
 * @version 1.0
 * @description 订单明细模块 - 业务层实现类
 */

@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements IOrderDetailService {
}
