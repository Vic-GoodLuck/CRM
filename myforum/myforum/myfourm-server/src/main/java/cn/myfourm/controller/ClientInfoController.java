package cn.myfourm.controller;

import cn.myfourm.entity.*;


import cn.myfourm.mapper.sysUserMapper;
import cn.myfourm.service.ClientInfoService;
import cn.myfourm.service.dataAreaService;
import cn.myfourm.service.dataClientLevelService;
import cn.myfourm.entity.clientInfoQuery;
import cn.myfourm.service.sysUserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@ResponseBody
@RequestMapping("/clientInfo")
public class ClientInfoController {
    @Autowired
    private ClientInfoService clientInfoService;
    @Autowired
    private dataAreaService dataAreaService_;
    @Autowired
    private dataClientLevelService dataclientlevelService_;
    @Autowired
    private sysUserMapper sysUserMapper_;

    //    分页查询用户列表
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public RespPageBean getAreaList(@RequestParam(value = "currentPage",defaultValue = "1")int currentPage, @RequestParam(value = "size",defaultValue = "10")int size){
        System.out.println("当前页-"+currentPage);
        return clientInfoService.getClientList(currentPage,size);
    }


    //    条件查询
    @RequestMapping(value = "/conditionSelect",method = RequestMethod.GET)
    public RespPageBean conditionSelect(@RequestParam(value = "currentPage")int currentPage,
                                        @RequestParam(value = "size")int size,
                                        @RequestParam(value = "clientName")String clientName_select,
                                        @RequestParam(value = "clientAreaId")int clientAreaId_select,
                                        @RequestParam(value = "clientLevelId")int clientLevelId_select,
                                        @RequestParam(value = "clientContentment")int clientContentment_select,
                                        @RequestParam(value = "clientCredit")int clientCredit_select,
                                        @RequestParam(value = "clientCustId")int clientCustId_select,
                                        @RequestParam(value = "clientState")int clientState_select)
    {
        System.out.println("--------------------------------------------------------------------------");
        System.out.println("currentPage: " + currentPage);
        System.out.println("size: " + size);
        System.out.println("clientName_select: " + clientName_select);
        System.out.println("clientAreaId_select: " + clientAreaId_select);
        System.out.println("clientLevelId_select: " + clientLevelId_select);
        System.out.println("clientContentment_select: " + clientContentment_select);
        System.out.println("clientCredit_select: " + clientCredit_select);
        System.out.println("clientCustId_select: " + clientCustId_select);
        System.out.println("clientState_select: " + clientState_select);
        System.out.println("---------------------------------------------------------------------------");
        return clientInfoService.conditionSelect(currentPage,size,clientName_select,clientAreaId_select,clientLevelId_select,clientContentment_select,clientCredit_select,clientCustId_select,clientState_select);
    }

    @RequestMapping(value = "/conditionSelectByList",method = RequestMethod.POST)
    public RespPageBean conditionSelectByList(@RequestBody clientInfoQuery query) {
        System.out.println(query.toString());
        // 执行查询
        return clientInfoService.conditionSelectByList(query);
    }

    //修改信息
    @RequestMapping(value = "/infoModify",method = RequestMethod.PUT)
    public RespBean changeClientInfo(@RequestBody clientInfo clientInfo) {
        clientInfo client1 = clientInfoService.getOne(new QueryWrapper<clientInfo>().eq("clientCode",clientInfo.getClientCode()));
        if (client1 != null) {
            boolean update = clientInfoService.updateById(clientInfo);
            if (update) {
                return RespBean.success("修改信息成功", clientInfo);
            }
        }
        return RespBean.error("该信息不存在");
    }



    @RequestMapping(value = "/area_options", method = RequestMethod.GET)
    public ResponseEntity<List<Map<String, String>>> getAreaOptions() {
        List<dataArea> areas = dataAreaService_.getArea();
        List<Map<String, String>> areaOptions = new ArrayList<>();
        for (dataArea area : areas) {
            if(area.getDelMark()==1)
            {
                Map<String, String> option = new HashMap<>();
                option.put("value", String.valueOf(area.getDaId()));
                option.put("label", area.getDaName());
                areaOptions.add(option);
            }
        }
        return ResponseEntity.ok().body(areaOptions);
    }

    @RequestMapping(value = "/level_options", method = RequestMethod.GET)
    public ResponseEntity<List<Map<String, String>>> getLevelOptions() {
        List<dataClientLevel> levels = dataclientlevelService_.getLevel();
        List<Map<String, String>> areaOptions = new ArrayList<>();
        for (dataClientLevel level : levels) {
            if(level.getDelMark()==1)
            {
                Map<String, String> option = new HashMap<>();
                option.put("value", String.valueOf(level.getDclId()));
                option.put("label", level.getDclName());
                areaOptions.add(option);
            }
        }
        return ResponseEntity.ok().body(areaOptions);
    }

    @RequestMapping(value = "/cust_options", method = RequestMethod.GET)
    public ResponseEntity<List<Map<String, String>>> getCustOptions() {
        List<sysUser> userList = sysUserMapper_.selectList(new QueryWrapper<sysUser>().eq("userRoleId", 3));
        List<Map<String, String>> custOptions = new ArrayList<>();
        for (sysUser user : userList) {
            if(user.getDelMark()==1)
            {
                Map<String, String> option = new HashMap<>();
                option.put("value", String.valueOf(user.getUserId()));
                option.put("label", user.getUserName());
                custOptions.add(option);
            }
        }
        return ResponseEntity.ok().body(custOptions);
    }

    //修改信息
    @RequestMapping(value = "/state",method = RequestMethod.PUT)
    public RespBean changeClientState(
            @RequestParam(value = "clientCode")String clientCode_,
            @RequestParam(value = "clientState")int clientState_,
            @RequestParam(value = "clientLoseReason")String clientLoseReason_
            ) {
        clientInfo client1 = clientInfoService.getOne(new QueryWrapper<clientInfo>().eq("clientCode",clientCode_));
        client1.setClientState(clientState_);
        if(clientState_==3)
        {
            client1.setClientLoseReason(clientLoseReason_);
            LocalDate currentDate = LocalDate.now();
            String formattedDate = currentDate.toString();
            client1.setClientLoseDate(formattedDate);
            if (client1 != null) {
                boolean update = clientInfoService.updateById(client1);
                if (update) {
                    return RespBean.success("该用户设置为已流失", client1);
                }
            }
        }
        return RespBean.error("设置失败");
    }



}
