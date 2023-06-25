package cn.myfourm.service;

import cn.myfourm.entity.RespPageBean;
import cn.myfourm.entity.clientLinkman;
import cn.myfourm.entity.dataArea;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface ClientLinkmanService extends IService<clientLinkman> {

    List<clientLinkman> getLinkman();
    RespPageBean getLinkmanList(int currentPage, int size,String clientCode);
}
