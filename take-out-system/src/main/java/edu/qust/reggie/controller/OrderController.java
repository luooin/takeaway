package edu.qust.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import edu.qust.reggie.common.BaseContext;
import edu.qust.reggie.common.R;
import edu.qust.reggie.entity.Category;
import edu.qust.reggie.entity.Orders;
import edu.qust.reggie.entity.Orders;
import edu.qust.reggie.service.IOrdersService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @version 1.0
 * @description
 */
@Slf4j
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private IOrdersService ordersService;

    /**
     * 用户下单
     * @param orders 订单对象 json -> java 对象
     * @return R
     */
    @PostMapping("/submit")
    public R<String> submit(@RequestBody Orders orders) {
        ordersService.submit(orders);
        return R.success("下单成功");
    }

    @GetMapping("/page")
    public R<Page<Orders>> page(int page, int pageSize, String number) {
        log.info("接收到分页查询请求");

        // 创建分页构造器
        Page<Orders> pageInfo = new Page<>(page, pageSize);
        // 创建条件构造器
        LambdaQueryWrapper<Orders> queryWrapper = new LambdaQueryWrapper<>();
        // 添加过滤条件
        queryWrapper.like(StringUtils.isNotEmpty(number), Orders::getNumber, number);
        // 添加排序条件
        queryWrapper.orderByDesc(Orders::getOrderTime);
        // 执行查询
        ordersService.page(pageInfo, queryWrapper);

        return R.success(pageInfo);
    }

// TODO: 2023/7/29

    @GetMapping("/userPage")
    public R<Page<Orders>> userPage(int page, int pageSize) {
        log.info("接收到分页查询请求");

        // 创建分页构造器
        Page<Orders> pageInfo = new Page<>(page, pageSize);
        // 创建条件构造器
        LambdaQueryWrapper<Orders> queryWrapper = new LambdaQueryWrapper<>();
        // 添加过滤条件
        queryWrapper.eq(Orders::getUserId, BaseContext.getCurrentId());
        // 添加排序条件
        queryWrapper.orderByDesc(Orders::getOrderTime);
        // 执行查询
        ordersService.page(pageInfo, queryWrapper);

        return R.success(pageInfo);
    }

    /**
     * 修改订单状态
     */
    @PutMapping
    public R<String> update(@RequestBody Orders category) {
        ordersService.updateById(category);
        return R.success("修改分类成功");
    }
}
