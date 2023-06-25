

package cn.myfourm.service;

import cn.myfourm.entity.*;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;


public interface dataClientLevelService extends IService<dataClientLevel> {
    List<dataClientLevel> getLevel();
    RespPageBean getLevelList(int currentPage, int size);
}
