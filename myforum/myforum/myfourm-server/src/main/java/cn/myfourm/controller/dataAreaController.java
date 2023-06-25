package cn.myfourm.controller;

import cn.myfourm.entity.RespBean;
import cn.myfourm.entity.RespPageBean;
import cn.myfourm.entity.dataArea;
import cn.myfourm.entity.sysUser;
import cn.myfourm.service.dataAreaService;
import cn.myfourm.service.impl.UserDetailsImpl;
import cn.myfourm.service.sysUserService;
import cn.myfourm.utils.JwtUtil;
import cn.myfourm.utils.RedisUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@ResponseBody
@RequestMapping("/dataArea")
public class dataAreaController {
    @Autowired
    private dataAreaService areaService;

    @RequestMapping(value = "/getArea",method = RequestMethod.GET)
    public RespBean getArea(){
        List<dataArea> areas = areaService.getArea();
        if(areas!=null){
            return RespBean.success("查询成功",areas);
        }
        return RespBean.error("查询失败");
    }

    //    分页查询用户列表
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public RespPageBean getAreaList(@RequestParam(value = "currentPage",defaultValue = "1")int currentPage, @RequestParam(value = "size",defaultValue = "10")int size){
        return areaService.getAreaList(currentPage,size);
    }

    @RequestMapping(value ="/add",method = RequestMethod.POST)
    public RespBean add(@RequestBody dataArea area){
        if(StringUtils.isEmpty(area.getDaName())){
            return RespBean.error("地区名称为空");
        }
        dataArea area1 = areaService.getOne(new QueryWrapper<dataArea>().eq("daName", area.getDaName()));
        if (area1 != null) {
            return RespBean.error("地区名称已被使用");
        }
        area.setDelMark(1);
        boolean save = areaService.save(area);
        if (save) return RespBean.success("添加成功");
        return RespBean.error("添加失败");
    }

    //    修改信息
    @RequestMapping(value = "/infoModify",method = RequestMethod.PUT)
    public RespBean changeUser(@RequestBody dataArea area) {
        dataArea area1 = areaService.getOne(new QueryWrapper<dataArea>().eq("daId", area.getDaId()));
        if (area1 != null) {
            boolean update = areaService.updateById(area);
            if (update) {
                return RespBean.success("修改信息成功", area);
            }
        }
        return RespBean.error("该信息不存在");
    }

    @RequestMapping(value = "/delete",method = RequestMethod.DELETE)
    public RespBean deleteUser(@RequestParam("daId") int id){
        boolean removeById = areaService.removeById(id);
        if(removeById){
            return RespBean.success("删除成功");
        }
        return RespBean.error("删除失败");
    }

    //    批量删除
    @RequestMapping(value = "/deletebatch",method = RequestMethod.POST)
    public RespBean deleteUser(@RequestBody List<Integer> ids){
        boolean remove = areaService.removeByIds(ids);
        System.out.println(ids);
        if(remove){
            return RespBean.success("删除成功");
        }
        return RespBean.error("删除失败");
    }
}
