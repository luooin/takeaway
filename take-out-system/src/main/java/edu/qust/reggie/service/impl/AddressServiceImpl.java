package edu.qust.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.qust.reggie.common.BaseContext;
import edu.qust.reggie.entity.Address;
import edu.qust.reggie.mapper.AddressMapper;
import edu.qust.reggie.service.IAddressService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 
 * @version 1.0
 * @description 收货地址管理（地址簿） - 业务层实现类
 */

@Service
public class AddressServiceImpl extends ServiceImpl<AddressMapper, Address> implements IAddressService {
    @Override
    public Address setDefault(Address address) {
        LambdaUpdateWrapper<Address> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(Address::getUserId, BaseContext.getCurrentId());
        wrapper.set(Address::getIsDefault, 0);
        // SQL:update address_book set is_default = 0 where user_id = ?
        this.update(wrapper);

        address.setIsDefault(1);
        // SQL:update address_book set is_default = 1 where id = ?
        this.updateById(address);

        return address;
    }

    @Override
    public Address getDefault() {
        LambdaQueryWrapper<Address> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Address::getUserId, BaseContext.getCurrentId());
        queryWrapper.eq(Address::getIsDefault, 1);

        // SQL:select * from address_book where user_id = ? and is_default = 1
        Address address = this.getOne(queryWrapper);
        return address;
    }

    @Override
    public List<Address> list(Address address) {
        address.setUserId(BaseContext.getCurrentId());

        LambdaQueryWrapper<Address> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(null != address.getUserId(), Address::getUserId, address.getUserId());
        queryWrapper.orderByDesc(Address::getUpdateTime);

        // SQL:select * from address_book where user_id = ? order by update_time desc
        List<Address> addressList = this.list(queryWrapper);

        return addressList;
    }
}
