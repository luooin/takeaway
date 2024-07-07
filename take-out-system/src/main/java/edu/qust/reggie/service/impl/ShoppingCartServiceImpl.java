package edu.qust.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.qust.reggie.common.BaseContext;
import edu.qust.reggie.entity.ShoppingCart;
import edu.qust.reggie.mapper.ShoppingCartMapper;
import edu.qust.reggie.service.IShoppingCartService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 
 * @version 1.0
 * @description 购物车模块（前台） - 业务层实现类
 */

@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart> implements IShoppingCartService {
    @Override
    public ShoppingCart add(ShoppingCart shoppingCart) {
        // 补全用户 id
        long userId = BaseContext.getCurrentId();
        shoppingCart.setUserId(userId);

        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId, userId);

        // 判断此次添加的商品是菜品还是套餐，并补全过滤条件
        Long dishId = shoppingCart.getDishId();
        if (dishId != null) {
            // 为菜品
            queryWrapper.eq(ShoppingCart::getDishId, dishId);
        } else {
            // 为套餐
            Long setmealId = shoppingCart.getSetmealId();
            queryWrapper.eq(ShoppingCart::getSetmealId, setmealId);
        }

        // 判断该商品是否已经在购物车中出现，如果之前出现过将数量增加即可
        ShoppingCart cart = this.getOne(queryWrapper);

        if (cart == null) {
            // 不存在该商品，手动添加
            shoppingCart.setNumber(1);
            // 设置日志字段 - 创建时间
            shoppingCart.setCreateTime(LocalDateTime.now());
            cart = shoppingCart;
            this.save(cart);
        } else {
            // 已经存在，数量增加即可
            cart.setNumber(cart.getNumber() + 1);
            this.updateById(cart);
        }



        return cart;
    }

    @Override
    public List<ShoppingCart> getList() {
        // 获取当前登录用户的 id
        long userId = BaseContext.getCurrentId();

        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.eq(ShoppingCart::getUserId, userId);
        queryWrapper.orderByAsc(ShoppingCart::getCreateTime);

        List<ShoppingCart> res = this.list(queryWrapper);

        return res;
    }

    @Override
    public void sub(ShoppingCart shoppingCart) {
        // 获取当前用户信息
        long userId = BaseContext.getCurrentId();
        // 构造条件查询
        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId, userId);

        // 判断删除的是套餐还是菜品
        Long dishId = shoppingCart.getDishId();
        if (dishId != null) {
            queryWrapper.eq(ShoppingCart::getDishId, dishId);
        } else {
            Long setmealId = shoppingCart.getSetmealId();
            queryWrapper.eq(ShoppingCart::getSetmealId, setmealId);
        }
        // 判断数量，数量大于为 1 时直接删除该记录，否则将数量减一
        ShoppingCart cart = this.getOne(queryWrapper);
        Integer number = cart.getNumber();
        if (number > 1) {
            cart.setNumber(number - 1);
            this.updateById(cart);
        } else {
            this.remove(queryWrapper);
        }

    }

    @Override
    public void clean() {
        long userId = BaseContext.getCurrentId();
        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId, userId);
        this.remove(queryWrapper);
    }


}
