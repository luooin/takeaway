package edu.qust.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.qust.reggie.common.BaseContext;
import edu.qust.reggie.entity.*;
import edu.qust.reggie.mapper.OrdersMapper;
import edu.qust.reggie.service.*;
import edu.qust.reggie.service.ex.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * 
 * @version 1.0
 * @description 订单模块 - 业务层实现类
 */

@Service
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders> implements IOrdersService {

    @Autowired
    private IShoppingCartService shoppingCartService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IAddressService addressService;

    @Autowired
    private IOrderDetailService orderDetailService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void submit(Orders orders) {
        // 获取当前用户 id
        long userId = BaseContext.getCurrentId();

        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        // 查询当前用户购物车信息
        queryWrapper.eq(ShoppingCart::getUserId,userId);
        List<ShoppingCart> shoppingCarts = shoppingCartService.list(queryWrapper);

        if(shoppingCarts == null || shoppingCarts.size() == 0){
            throw new CustomException("购物车信息不存在");
        }

        //查询用户数据
        User user = userService.getById(userId);

        //查询地址数据
        Long addressId = orders.getAddressId();
        Address address = addressService.getById(addressId);
        if(address == null){
            throw new CustomException("用户地址信息有误，不能下单");
        }

        // 订单号(mp生成)
        long orderId = IdWorker.getId();

        // 统计订单明细信息 计算当前订单总金额
        AtomicInteger amount = new AtomicInteger(0);
        List<OrderDetail> orderDetails = new ArrayList<>();
        for (ShoppingCart shoppingCart : shoppingCarts) {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrderId(orderId);
            orderDetail.setNumber(shoppingCart.getNumber());
            orderDetail.setDishFlavor(shoppingCart.getDishFlavor());
            orderDetail.setDishId(shoppingCart.getDishId());
            orderDetail.setSetmealId(shoppingCart.getSetmealId());
            orderDetail.setName(shoppingCart.getName());
            orderDetail.setImage(shoppingCart.getImage());
            orderDetail.setAmount(shoppingCart.getAmount());
            // 计算总价
            amount.addAndGet(shoppingCart.getAmount().multiply(new BigDecimal(shoppingCart.getNumber())).intValue());

            orderDetails.add(orderDetail);
        }

        // 补全其他信息
        orders.setId(orderId);
        orders.setOrderTime(LocalDateTime.now());
        orders.setCheckoutTime(LocalDateTime.now());
        orders.setStatus(2);
        orders.setAmount(new BigDecimal(amount.get()));
        orders.setUserId(userId);
        orders.setNumber(String.valueOf(orderId));
        orders.setUserName(user.getName());
        orders.setConsignee(address.getConsignee());
        orders.setPhone(address.getPhone());
        orders.setAddress((address.getProvinceName() == null ? "" : address.getProvinceName())
                + (address.getCityName() == null ? "" : address.getCityName())
                + (address.getDistrictName() == null ? "" : address.getDistrictName())
                + (address.getDetail() == null ? "" : address.getDetail()));

        // 向订单表中添加数据(一条)
        this.save(orders);

        // 向订单明细表中添加数据(根据购物车中物品数量)
        orderDetailService.saveBatch(orderDetails);

        //清空购物车数据
        shoppingCartService.remove(queryWrapper);

    }
}
