package edu.qust.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import edu.qust.reggie.entity.Address;

import java.util.List;

/**
 * 
 * @version 1.0
 * @description 收货地址管理（地址簿） - 业务层接口
 */

public interface IAddressService extends IService<Address> {

    /**
     * 设置默认收货地址
     * @param address 收货地址
     * @return 收货地址对象
     */
    Address setDefault(Address address);

    /**
     * 查询默认收货地址
     * @return 收货地址对象
     */
    Address getDefault();

    /**
     * 查询指定用户的全部地址
     * @param address 收货地址
     * @return 用户的全部地址
     */
    List<Address> list(Address address);
}
