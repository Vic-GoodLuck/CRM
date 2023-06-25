package cn.myfourm.service.impl;

import cn.myfourm.entity.RespPageBean;
import cn.myfourm.entity.dataArea;
import cn.myfourm.entity.orders;
import cn.myfourm.mapper.OrdersMapper;
import cn.myfourm.mapper.dataAreaMapper;
import cn.myfourm.service.OrdersService;
import cn.myfourm.service.dataAreaService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, orders> implements OrdersService {
    @Autowired
    private OrdersMapper ordersMapper;

    @Override
    public RespPageBean getOrdersList(int currentPage, int size) {
        Page<orders> userPage = new Page<>(currentPage,size);
        IPage<orders> userIPage = ordersMapper.getOrdersList(userPage);
        return new RespPageBean(userIPage.getTotal(), userIPage.getRecords());
    }

    @Override
    public RespPageBean getOrdersListBy(int currentPage, int size,int ordersState,String clientCode) {
        Page<orders> userPage = new Page<>(currentPage,size);
        IPage<orders> userIPage = ordersMapper.getOrdersListBy(userPage,ordersState,clientCode);
        return new RespPageBean(userIPage.getTotal(), userIPage.getRecords());
    }

    @Override
    public List<orders> selectByClientCode(String clientCode){
        return ordersMapper.selectByClientCode(clientCode);
    }

    @Override
    public List<orders> selectByClientCodeAndState(String clientCode_,int ordersState_){
        QueryWrapper<orders> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("ordersState", ordersState_);
        queryWrapper.eq("clientCode", clientCode_);
        List<orders> ordersList = ordersMapper.selectList(queryWrapper);
        return ordersList;
    }

}
