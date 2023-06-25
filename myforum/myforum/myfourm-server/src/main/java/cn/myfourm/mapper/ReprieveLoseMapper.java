package cn.myfourm.mapper;

import cn.myfourm.entity.clientReprieveLose;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReprieveLoseMapper extends BaseMapper<clientReprieveLose>{
    IPage<clientReprieveLose> getLostList(@Param("page") Page<clientReprieveLose> userPage,@Param("clientCode") String clientCode);
}

