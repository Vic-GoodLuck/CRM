package cn.myfourm.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
//客户信息表
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("clientinfo")
public class clientInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "clientCode")
    private String clientCode;//客户编号（KH + 六位日期数字 + 三位数字流水号）

    @TableField("clientName")
    @NotNull(message = "客户名称不能为空")
    private String clientName;//客户名称

    @TableField("clientAreaId")
    @NotNull(message = "客户所在地区编号不能为空")
    private Integer clientAreaId;//客户所在地区

    @TableField("clientCustId")
    @NotNull(message = "所属客户经理编号不能为空")
    private Integer clientCustId;//所属客户经理编号

    @TableField("clientLevelId")
    @NotNull(message = "客户等级不能为空")
    private Integer clientLevelId;//客户等级

    @TableField("clientContentment")
    @NotNull(message = "客户满意度不能为空")
    private Integer clientContentment;//客户满意度

    @TableField("clientCredit")
    @NotNull(message = "客户信用度不能为空")
    private Integer clientCredit;//客户信用度

    @NotNull(message = "客户地址不能为空")
    private String clientAddress;//客户地址

    @NotNull(message = "客户邮政编码不能为空")
    private String clientDakCode;//客户邮政编码

    @NotNull(message = "电话不能为空")
    private String clientTel;//电话

    @NotNull(message = "传真不能为空")
    private String clientFax;//传真

    @NotNull(message = "网址不能为空")
    private String clientNet;//网址

    private String clientCharter;//营业执照注册号

    @NotNull(message = "法人不能为空")
    private String clientCorporation;//法人

    private Integer clientBankroll;//注册资金（万元）
    private Integer clientTurnoverYear;//年营业额（万元）

    @NotNull(message = "开户银行不能为空")
    private String clientBank;//开户银行

    @NotNull(message = "银行帐号不能为空")
    private String clientAccounts;//银行帐号

    private String clientNativeTax;//地税登记号
    private String clientCountryTax;//国税登记号

    @TableField("clientState")
    @NotNull(message = "客户状态不能为空")
    private Integer clientState;//客户状态

    private String clientLoseReason;//客户流失原因

    private String clientLoseDate;//客户流失日期


    @TableField(exist = false)
    private String code;


}
