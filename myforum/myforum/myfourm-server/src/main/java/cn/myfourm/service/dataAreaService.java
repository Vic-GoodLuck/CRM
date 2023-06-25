package cn.myfourm.service;

import cn.myfourm.entity.RespBean;
import cn.myfourm.entity.RespPageBean;
import cn.myfourm.entity.dataArea;
import cn.myfourm.entity.sysUser;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;



public interface dataAreaService extends IService<dataArea>  {
    String getDaName(int daId);
    List<dataArea> getArea();
    RespPageBean getAreaList(int currentPage, int size);
}
