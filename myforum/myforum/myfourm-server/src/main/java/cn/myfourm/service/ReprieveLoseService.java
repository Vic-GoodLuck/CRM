package cn.myfourm.service;

import cn.myfourm.entity.RespPageBean;
import cn.myfourm.entity.clientReprieveLose;
import com.baomidou.mybatisplus.extension.service.IService;


public interface ReprieveLoseService extends IService<clientReprieveLose> {
    RespPageBean getLostList(int currentPage, int size,String clientCode);
}
