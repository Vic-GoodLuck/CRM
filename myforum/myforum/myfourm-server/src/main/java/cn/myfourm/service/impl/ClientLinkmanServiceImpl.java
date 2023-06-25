package cn.myfourm.service.impl;

import cn.myfourm.entity.RespPageBean;
import cn.myfourm.entity.clientLinkman;
import cn.myfourm.entity.dataArea;
import cn.myfourm.mapper.ClientInfoMapper;
import cn.myfourm.mapper.ClientLinkmanMapper;
import cn.myfourm.mapper.dataAreaMapper;
import cn.myfourm.service.ClientInfoService;
import cn.myfourm.service.ClientLinkmanService;
import cn.myfourm.service.dataAreaService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ClientLinkmanServiceImpl extends ServiceImpl<ClientLinkmanMapper, clientLinkman> implements ClientLinkmanService {
    @Autowired
    private ClientLinkmanMapper clientLinkmanMapper;

    @Override
    public List<clientLinkman> getLinkman() {
        QueryWrapper<clientLinkman> queryWrapper = new QueryWrapper<>();
//        queryWrapper.like("delMark", 1);
        List<clientLinkman> linkman=clientLinkmanMapper.selectList(queryWrapper);
        return linkman;
    }

    @Override
    public RespPageBean getLinkmanList(int currentPage, int size,String clientCode) {
        Page<clientLinkman> userPage = new Page<>(currentPage,size);
        IPage<clientLinkman> userIPage = clientLinkmanMapper.getLinkmanList(userPage,clientCode);
        return new RespPageBean(userIPage.getTotal(), userIPage.getRecords());
    }

}
