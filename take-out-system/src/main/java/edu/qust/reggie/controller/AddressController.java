package edu.qust.reggie.controller;


import edu.qust.reggie.common.BaseContext;
import edu.qust.reggie.common.R;
import edu.qust.reggie.entity.Address;
import edu.qust.reggie.service.IAddressService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 
 * @version 1.0
 * @description 收货地址管理（地址簿）模块 - 控制层
 */
@Slf4j
@RestController
@RequestMapping("/addressBook")
public class AddressController {

    @Autowired
    private IAddressService addressService;

    /**
     * 新增收货地址
     * @param address 地址信息
     * @return R
     */
    @PostMapping
    public R<Address> save(@RequestBody Address address) {
        address.setUserId(BaseContext.getCurrentId());
        addressService.save(address);

        return R.success(address);
    }

    /**
     * 设置默认地址
     * @param address 地址信息
     * @return R
     */
    @PutMapping("/default")
    public R<Address> setDefault(@RequestBody Address address) {
        Address result = addressService.setDefault(address);
        return R.success(result);
    }

    /**
     * 根据 id 查询地址
     * @param id 地址 id
     * @return R
     */
    @GetMapping("/{id}")
    public R<Address> get(@PathVariable Long id) {
        Address address = addressService.getById(id);

        return address != null? R.success(address): R.error("没有找到该对象");

    }

    /**
     * 查询默认地址
     * @return R
     */
    @GetMapping("/default")
    public R<Address> getDefault() {
        Address address = addressService.getDefault();

        return address != null? R.success(address): R.error("没有找到该对象");
    }

    /**
     * 查询指定用户的全部地址
     * @param address address
     * @return R
     */
    @GetMapping("/list")
    public R<List<Address>> list(Address address) {
        List<Address> addressList = addressService.list(address);

        return R.success(addressList);
    }
}
