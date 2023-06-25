package cn.myfourm.controller;

import cn.myfourm.entity.RespBean;
import cn.myfourm.entity.RespPageBean;
import cn.myfourm.entity.clientInfo;
import cn.myfourm.entity.orders;
import cn.myfourm.service.ClientInfoService;
import cn.myfourm.service.OrdersService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.Optional;



@Controller
@ResponseBody
@RequestMapping("/orders")
public class OrdersController {
    @Autowired
    private OrdersService ordersService;
    @Autowired
    private ClientInfoService clientInfoService;

    //分页查询订单列表
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public RespPageBean getOrdersList(
            @RequestParam(value = "currentPage",defaultValue = "1")int currentPage,
            @RequestParam(value = "size",defaultValue = "10")int size){
        return ordersService.getOrdersList(currentPage,size);
    }

    //分页查询订单列表(状态、用户)
    @RequestMapping(value = "/listBy",method = RequestMethod.GET)
    public RespPageBean getOrdersListBy(
            @RequestParam(value = "currentPage",defaultValue = "1")int currentPage,
            @RequestParam(value = "size",defaultValue = "10")int size,
            @RequestParam(value = "ordersState",defaultValue = "0")int ordersState,
            @RequestParam(value = "clientCode",defaultValue = "")String clientCode){
        return ordersService.getOrdersListBy(currentPage,size,ordersState,clientCode);
    }

    @RequestMapping(value = "/countdown",method = RequestMethod.GET)
    public int getOrdersByClientCode(@RequestParam("clientCode") String clientCode){
        // 从订单服务中获取客户订单列表
        List<orders> ordersList=ordersService.selectByClientCode(clientCode);
        // 获取距离当前时间最近的订单
        Optional<orders> nearestOrder = ordersList.stream()
                // 使用流遍历订单列表
                .min(Comparator.comparing(order -> Math.abs(order.getOrdersDate().getTime() - new Date().getTime())));
        // 找到距离当前时间最近的订单后，返回订单对象作为 Optional 类型
        // Optional 类型是 Java 8 中的新特性，用于避免空指针异常
        if (nearestOrder.isPresent()) {
            // 如果找到距离当前时间最近的订单
            // 获取当前日期
            LocalDate currentDate = LocalDate.now();
            // 获取距离当前时间最近的订单日期
            LocalDate nearestDate = nearestOrder.get().getOrdersDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            System.out.println(nearestDate);
            // 计算最近日期和当前日期之间的天数
            int countdown=180-(int)ChronoUnit.DAYS.between(nearestDate, currentDate);
            //后台检测是否countdown<0 直接设置对应客户的状态
            if(countdown<0){
                clientInfo client1 = clientInfoService.getOne(new QueryWrapper<clientInfo>().eq("clientCode",clientCode));
                if(client1.getClientState()==3){
                    return -1;
                }
                client1.setClientState(2);
                if (client1 != null) {
                    boolean update = clientInfoService.updateById(client1);
                    if (update) {
                        System.out.println("修改成功"+client1);
                    }
                }
                System.out.println("修改失败");
            }
            return countdown;
        } else {
            // 如果没有找到距离当前时间最近的订单
            return -1; // 返回-1
        }
    }


}