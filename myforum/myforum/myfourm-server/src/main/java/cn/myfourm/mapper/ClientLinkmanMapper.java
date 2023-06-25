package cn.myfourm.mapper;

import cn.myfourm.entity.clientLinkman;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface ClientLinkmanMapper extends BaseMapper<clientLinkman> {
    IPage<clientLinkman> getLinkmanList(@Param("page") Page<clientLinkman> userPage,@Param("clientCode") String clientCode);
}


