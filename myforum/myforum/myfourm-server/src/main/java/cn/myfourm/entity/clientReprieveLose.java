package cn.myfourm.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
//客户暂缓流失措施记录表
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("clientreprievelose")
public class clientReprieveLose implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "clientReprieveLoseId", type = IdType.AUTO)
    private Integer clientReprieveLoseId;//客户暂缓流失记录编号

    @NotNull(message = "暂缓措施项不能为空")
    private String reprieveStep;//暂缓措施项

    @NotNull(message = "客户编号不能为空")
    private String clientCode;//客户编号


    @TableField(exist = false)
    private String code;


}
