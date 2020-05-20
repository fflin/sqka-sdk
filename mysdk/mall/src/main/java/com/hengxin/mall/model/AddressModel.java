package com.hengxin.mall.model;

import java.io.Serializable;

/**
 * author : fflin
 * date   : 2020/5/20 19:55
 * desc   : 收货地址信息
 * version: 1.0
 */
public class AddressModel implements Serializable {

    public String name, phone, addSel, addAll, addTag;
    public boolean isDefault;

}
