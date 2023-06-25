package cn.myfourm.service.impl;

import cn.myfourm.entity.RespPageBean;
import cn.myfourm.entity.clientInfo;
import cn.myfourm.entity.clientInfoQuery;
import cn.myfourm.entity.sysUser;
import cn.myfourm.mapper.ClientInfoMapper;
import cn.myfourm.service.ClientInfoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.collections4.CollectionUtils;


@Service
public class ClientInfoServiceImpl extends ServiceImpl<ClientInfoMapper, clientInfo> implements ClientInfoService {
    @Autowired
    private ClientInfoMapper clientInfoMapper;

    //分页查询用户列表
    @Override
    public RespPageBean getClientList(int currentPage, int size) {
        Page<clientInfo> userPage = new Page<>(currentPage,size);
        IPage<clientInfo> userIPage = clientInfoMapper.getClientList(userPage);

//        userIPage.getRecords().forEach(user -> {
//            String areaName=dataareaMapper.getDaName(user.getDaId());
//            user.setDaName(areaName);
//        });
        return new RespPageBean(userIPage.getTotal(), userIPage.getRecords());
    }

//条件查询
    @Override
   public RespPageBean conditionSelect(int currentPage, int size,String clientName_select,int clientAreaId_select,int clientLevelId_select,int clientContentment_select,int clientCredit_select,int clientCustId_select,int clientState_select){
        Page<clientInfo> userPage = new Page<>(currentPage,size);
        IPage<clientInfo> userIPage = clientInfoMapper.conditionSelect(userPage,clientName_select,clientAreaId_select,clientLevelId_select,clientContentment_select,clientCredit_select,clientCustId_select,clientState_select);
        return new RespPageBean(userIPage.getTotal(), userIPage.getRecords());
    }

    //新条件查询
    @Override
    public RespPageBean conditionSelectByList(clientInfoQuery query) {
        Page<clientInfo> page = new Page<>(query.getCurrentPage(), query.getSize());
        QueryWrapper<clientInfo> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(query.getClientName())) {
            wrapper.like("clientName", query.getClientName());
        }
        if (CollectionUtils.isNotEmpty(query.getClientAreaId())) {
            wrapper.in("clientAreaId", query.getClientAreaId());
        }
        if (CollectionUtils.isNotEmpty(query.getClientLevelId())) {
            wrapper.in("clientLevelId", query.getClientLevelId());
        }
        if (CollectionUtils.isNotEmpty(query.getClientContentment())) {
            wrapper.in("clientContentment", query.getClientContentment());
        }
        if (CollectionUtils.isNotEmpty(query.getClientCredit())) {
            wrapper.in("clientCredit", query.getClientCredit());
        }
        if (CollectionUtils.isNotEmpty(query.getClientCustId())) {
            wrapper.in("clientCustId", query.getClientCustId());
        }
        if (CollectionUtils.isNotEmpty(query.getClientState())) {
            wrapper.in("clientState", query.getClientState());
        }
        IPage<clientInfo> clientInfoIPage = clientInfoMapper.selectPage(page, wrapper);
        RespPageBean respPageBean = new RespPageBean();
        respPageBean.setData(clientInfoIPage.getRecords());
        respPageBean.setTotal(clientInfoIPage.getTotal());
        return respPageBean;
    }

}
