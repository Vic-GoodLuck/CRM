package cn.myfourm.controller;

import cn.myfourm.entity.RespBean;
import cn.myfourm.entity.RespPageBean;
import cn.myfourm.entity.clientLinkman;
import cn.myfourm.entity.dataArea;
import cn.myfourm.mapper.ClientLinkmanMapper;
import cn.myfourm.service.ClientLinkmanService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;


@Controller
@ResponseBody
@RequestMapping("/linkman")
public class ClientLinkmanController {
    @Autowired
    private ClientLinkmanService clientLinkmanService;

    //分页查询联系人列表
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public RespPageBean getAreaList(
            @RequestParam(value = "currentPage",defaultValue = "1")int currentPage,
            @RequestParam(value = "size",defaultValue = "10")int size,
            @RequestParam(value = "clientCode",defaultValue = "")String clientCode){
        return clientLinkmanService.getLinkmanList(currentPage,size,clientCode);
    }

    @RequestMapping(value ="/add",method = RequestMethod.POST)
    public RespBean add(@RequestBody clientLinkman linkman){
        if(StringUtils.isEmpty(linkman.getClientLinkmanName())){
            return RespBean.error("客户联系人名称为空");
        }
        clientLinkman man1 = clientLinkmanService.getOne(new QueryWrapper<clientLinkman>().eq("clientLinkmanName", linkman.getClientLinkmanName()));
        if (man1 != null) {
            return RespBean.error("联系人名称已被使用");
        }
        boolean save = clientLinkmanService.save(linkman);
        if (save) return RespBean.success("添加成功");
        return RespBean.error("添加失败");
    }

    //修改信息
    @RequestMapping(value = "/infoModify",method = RequestMethod.PUT)
    public RespBean changeLinkman(@RequestBody clientLinkman linkman) {
        clientLinkman man1 = clientLinkmanService.getOne(new QueryWrapper<clientLinkman>().eq("clientLinkmanId", linkman.getClientLinkmanId()));
        if (man1 != null) {
            boolean update = clientLinkmanService.updateById(linkman);
            if (update) {
                return RespBean.success("修改信息成功", linkman);
            }
        }
        return RespBean.error("该信息不存在");
    }

    @RequestMapping(value = "/delete",method = RequestMethod.DELETE)
    public RespBean deleteLinkman(@RequestParam("clientLinkmanId") int id){
        boolean removeById = clientLinkmanService.removeById(id);
        if(removeById){
            return RespBean.success("删除成功");
        }
        return RespBean.error("删除失败");
    }



}
