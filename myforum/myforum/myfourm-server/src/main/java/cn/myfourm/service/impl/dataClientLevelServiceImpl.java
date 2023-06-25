package cn.myfourm.service.impl;

import cn.myfourm.entity.*;
import cn.myfourm.mapper.dataAreaMapper;
import cn.myfourm.mapper.dataClientLevelMapper;
import cn.myfourm.mapper.dataServerTypeMapper;
import cn.myfourm.mapper.sysUserMapper;
import cn.myfourm.service.dataAreaService;
import cn.myfourm.service.dataClientLevelService;
import cn.myfourm.service.dataServerTypeService;
import cn.myfourm.service.sysUserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class dataClientLevelServiceImpl extends ServiceImpl<dataClientLevelMapper, dataClientLevel> implements dataClientLevelService {
    @Autowired
    private dataClientLevelMapper dataclientlevelMapper_;


    @Override
    public List<dataClientLevel> getLevel(){
        QueryWrapper<dataClientLevel> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("delMark", 1);
        List<dataClientLevel> levels=dataclientlevelMapper_.selectList(queryWrapper);
        return levels;
    }

    @Override
    public RespPageBean getLevelList(int currentPage, int size) {
        Page<dataClientLevel> userPage = new Page<>(currentPage,size);
        IPage<dataClientLevel> userIPage = dataclientlevelMapper_.getLevelList(userPage);
        return new RespPageBean(userIPage.getTotal(), userIPage.getRecords());
    }
}
