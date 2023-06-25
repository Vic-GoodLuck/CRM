package cn.myfourm.mapper;

import cn.myfourm.entity.clientInfo;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientInfoMapper extends BaseMapper<clientInfo> {
    IPage<clientInfo> getClientList(@Param("page") Page<clientInfo> userPage);
    IPage<clientInfo> conditionSelect(@Param("page")Page<clientInfo> userPage, @Param("clientName")String clientName,@Param("clientAreaId")int clientAreaId, @Param("clientLevelId")int clientLevelId,@Param("clientContentment")int clientContentment,@Param("clientCredit")int clientCredit, @Param("clientCustId")int clientCustId, @Param("clientState")int clientState);

}
