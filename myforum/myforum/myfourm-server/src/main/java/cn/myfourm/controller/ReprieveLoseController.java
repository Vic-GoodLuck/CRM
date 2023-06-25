package cn.myfourm.controller;

import cn.myfourm.entity.RespBean;
import cn.myfourm.entity.RespPageBean;
import cn.myfourm.entity.clientReprieveLose;
import cn.myfourm.service.OrdersService;
import cn.myfourm.service.ReprieveLoseService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@Controller
@ResponseBody
@RequestMapping("/lost")
public class ReprieveLoseController {
    @Autowired
    private ReprieveLoseService reprieveLoseService;

    //分页查询暂缓流失措施列表(根据code)
    @RequestMapping(value = "/listBy",method = RequestMethod.GET)
    public RespPageBean getLostList(
            @RequestParam(value = "currentPage",defaultValue = "1")int currentPage,
            @RequestParam(value = "size",defaultValue = "10")int size,
            @RequestParam(value = "clientCode",defaultValue = "")String clientCode){
        System.out.println("------------"+clientCode);
        return reprieveLoseService.getLostList(currentPage,size,clientCode);
    }


    @RequestMapping(value ="/add",method = RequestMethod.POST)
    public RespBean add(@RequestBody clientReprieveLose lose){
        boolean save = reprieveLoseService.save(lose);
        if (save) return RespBean.success("添加成功");
        return RespBean.error("添加失败");
    }

    //修改信息
    @RequestMapping(value = "/infoModify",method = RequestMethod.PUT)
    public RespBean changeLose(@RequestBody clientReprieveLose lose) {
        clientReprieveLose l1 = reprieveLoseService.getOne(new QueryWrapper<clientReprieveLose>().eq("clientReprieveLoseId", lose.getClientReprieveLoseId()));
        if (l1 != null) {
            boolean update = reprieveLoseService.updateById(lose);
            if (update) {
                return RespBean.success("修改信息成功", lose);
            }
        }
        return RespBean.error("该信息不存在");
    }

    @RequestMapping(value = "/delete",method = RequestMethod.DELETE)
    public RespBean deleteLose(@RequestParam("clientReprieveLoseId") int id){
        boolean removeById = reprieveLoseService.removeById(id);
        if(removeById){
            return RespBean.success("删除成功");
        }
        return RespBean.error("删除失败");
    }
}