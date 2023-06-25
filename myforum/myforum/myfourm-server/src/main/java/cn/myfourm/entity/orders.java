package cn.myfourm.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
//订单信息表
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("orders")
public class orders implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ordersId", type = IdType.AUTO)
    private Integer ordersId;//订单编号

    @NotNull(message = "订购日期不能为空")
    private Date ordersDate;//订购日期

    @NotNull(message = "订单状态不能为空")
    private Integer ordersState;//订单状态

    @NotNull(message = "所属客户编号不能为空")
    private String clientCode;//所属客户编号

    @NotNull(message = "订单总金额不能为空")
    private Integer ordersTotal;//订单总金额

    private String remark;//备注


    @TableField(exist = false)
    private String code;


}
