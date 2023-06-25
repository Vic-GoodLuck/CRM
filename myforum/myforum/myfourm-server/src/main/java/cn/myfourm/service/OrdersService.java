package cn.myfourm.service;


import cn.myfourm.entity.RespPageBean;
import cn.myfourm.entity.dataArea;
import cn.myfourm.entity.orders;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface OrdersService extends IService<orders> {
    RespPageBean getOrdersList(int currentPage, int size);
    RespPageBean getOrdersListBy(int currentPage, int size,int ordersState,String clientCode);
    List<orders> selectByClientCode(String clientCode);
    List<orders> selectByClientCodeAndState(String clientCode_,int ordersState_);
}
