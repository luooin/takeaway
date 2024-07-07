package edu.qust.reggie.common;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 
 * @version 1.0
 * @description 自定义元对象数据处理器，用于自动填充数据库表的公共字段
 */
@Component
@Slf4j
public class MyMetaObjectHandler implements MetaObjectHandler {

    /** 设置在插入操作时自动填充策略 setValue(属性名, 熟悉值) */
    @Override
    public void insertFill(MetaObject metaObject) {
        LocalDateTime now = LocalDateTime.now();
        long id = BaseContext.getCurrentId();
        metaObject.setValue("createUser", id);
        metaObject.setValue("createTime", now);
        metaObject.setValue("updateUser", id);
        metaObject.setValue("updateTime", now);

    }

    /** 设置在修改操作时自动填充策略 */
    @Override
    public void updateFill(MetaObject metaObject) {
        LocalDateTime now = LocalDateTime.now();
        long id = BaseContext.getCurrentId();
        metaObject.setValue("updateUser", id);
        metaObject.setValue("updateTime", now);
    }
}
