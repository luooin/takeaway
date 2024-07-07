package edu.qust.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import edu.qust.reggie.entity.ShoppingCart;

import java.util.List;

/**
 * 
 * @version 1.0
 * @description 购物车模块（前台） - 业务层接口
 */

public interface IShoppingCartService extends IService<ShoppingCart> {

    /**
     * 向购物车中添加菜品/套餐
     * @param shoppingCart 某件购物车商品信息
     * @return 添加后的购物车数据用于前端渲染
     */
    ShoppingCart add(ShoppingCart shoppingCart);

    /**
     * 查询用户购物车商品
     * @return 购物车商品信息
     */
    List<ShoppingCart> getList();

    /**
     * 购物车中删除菜品/套餐
     * @param shoppingCart 某件购物车商品信息
     * @return 添加后的购物车数据用于前端渲染
     */
    void sub(ShoppingCart shoppingCart);

    /**
     * 清空购物车
     */
    void clean();
}
