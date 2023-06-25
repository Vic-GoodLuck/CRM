package cn.myfourm.mapper;


import cn.myfourm.entity.orders;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdersMapper extends BaseMapper<orders>{
    IPage<orders> getOrdersList(@Param("page") Page<orders> userPage);
    IPage<orders> getOrdersListBy(@Param("page") Page<orders> userPage,@Param("ordersState")int ordersState,@Param("clientCode") String clientCode);
    List<orders> selectByClientCode(@Param("clientCode") String clientCode);
}


