package cn.myfourm.service;

import cn.myfourm.entity.RespPageBean;
import cn.myfourm.entity.clientInfo;
import cn.myfourm.entity.clientInfoQuery;
import com.baomidou.mybatisplus.extension.service.IService;

public interface ClientInfoService extends IService<clientInfo> {
    RespPageBean getClientList(int currentPage, int size);

    RespPageBean conditionSelect(int currentPage, int size,String clientName_select,int clientAreaId_select,int clientLevelId_select,int clientContentment_select,int clientCredit_select,int clientCustId_select,int clientState_select);

    RespPageBean conditionSelectByList(clientInfoQuery query);
}
