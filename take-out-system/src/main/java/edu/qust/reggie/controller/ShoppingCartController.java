package edu.qust.reggie.controller;

import edu.qust.reggie.common.R;
import edu.qust.reggie.entity.ShoppingCart;
import edu.qust.reggie.service.IShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 
 * @version 1.0
 * @description 购物车模块（前台） - 控制层
 */

@Slf4j
@RestController
@RequestMapping("/shoppingCart")
public class ShoppingCartController {

    @Autowired
    private IShoppingCartService shoppingCartService;

    /**
     * 向购物车中添加菜品/套餐
     * @param shoppingCart 购物车订单实体
     * @return R
     */
    @PostMapping("/add")
    public R<ShoppingCart> add(@RequestBody ShoppingCart shoppingCart) {
        ShoppingCart res = shoppingCartService.add(shoppingCart);

        return R.success(res);
    }

    /**
     * 购物车中删除菜品/套餐
     * @param shoppingCart 购物车订单实体
     * @return R
     */
    @PostMapping("/sub")
    public R<String> sub(@RequestBody ShoppingCart shoppingCart) {
        shoppingCartService.sub(shoppingCart);

        return R.success("success");
    }

    /**
     * 查询用户购物车商品
     * @return R
     */
    @GetMapping("/list")
    public R<List<ShoppingCart>> list() {
        List<ShoppingCart> res = shoppingCartService.getList();

        return R.success(res);

    }

    /**
     * 清空购物车
     * @return R
     */
    @DeleteMapping("/clean")
    public R<String> clean() {
        shoppingCartService.clean();

        return R.success("success");
    }
}
