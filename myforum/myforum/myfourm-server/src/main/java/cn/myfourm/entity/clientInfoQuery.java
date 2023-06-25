package cn.myfourm.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;


/*
 * clientInfo查询返回对象
 *
 * 联合查询需要的内容
    clientName
    clientAreaId
    clientLevelId
    clientContentment
    clientCredit
    clientCustId
    clientState
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class clientInfoQuery {
    private String clientName;
    private List<Integer> clientAreaId;
    private List<Integer> clientLevelId;
    private List<Integer> clientContentment;
    private List<Integer> clientCredit;
    private List<Integer> clientCustId;
    private List<Integer> clientState;

    private int currentPage;
    private int size;

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public List<Integer> getClientAreaId() {
        return clientAreaId;
    }

    public void setClientAreaId(List<Integer> clientAreaId) {
        this.clientAreaId = clientAreaId;
    }

    public List<Integer> getClientLevelId() {
        return clientLevelId;
    }

    public void setClientLevelId(List<Integer> clientLevelId) {
        this.clientLevelId = clientLevelId;
    }

    public List<Integer> getClientContentment() {
        return clientContentment;
    }

    public void setClientContentment(List<Integer> clientContentment) {
        this.clientContentment = clientContentment;
    }

    public List<Integer> getClientCredit() {
        return clientCredit;
    }

    public void setClientCredit(List<Integer> clientCredit) {
        this.clientCredit = clientCredit;
    }

    public List<Integer> getClientCustId() {
        return clientCustId;
    }

    public void setClientCustId(List<Integer> clientCustId) {
        this.clientCustId = clientCustId;
    }

    public List<Integer> getClientState() {
        return clientState;
    }

    public void setClientState(List<Integer> clientState) {
        this.clientState = clientState;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
