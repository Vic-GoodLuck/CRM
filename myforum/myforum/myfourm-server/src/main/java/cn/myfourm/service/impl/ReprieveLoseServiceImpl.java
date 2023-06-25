package cn.myfourm.service.impl;

import cn.myfourm.entity.RespPageBean;
import cn.myfourm.entity.clientReprieveLose;
import cn.myfourm.mapper.ReprieveLoseMapper;
import cn.myfourm.service.ReprieveLoseService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReprieveLoseServiceImpl extends ServiceImpl<ReprieveLoseMapper, clientReprieveLose> implements ReprieveLoseService {
    @Autowired
    private ReprieveLoseMapper reprieveLoseMapper;

    @Override
    public RespPageBean getLostList(int currentPage, int size,String clientCode) {
        Page<clientReprieveLose> userPage = new Page<>(currentPage,size);
        IPage<clientReprieveLose> userIPage = reprieveLoseMapper.getLostList(userPage,clientCode);
        return new RespPageBean(userIPage.getTotal(), userIPage.getRecords());
    }

}
