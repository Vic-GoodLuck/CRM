
package cn.myfourm.controller;

import cn.myfourm.entity.*;
import cn.myfourm.service.dataAreaService;
import cn.myfourm.service.dataClientLevelService;
import cn.myfourm.service.dataServerTypeService;
import cn.myfourm.service.impl.UserDetailsImpl;
import cn.myfourm.service.sysUserService;
import cn.myfourm.utils.JwtUtil;
import cn.myfourm.utils.RedisUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Controller
@ResponseBody
@RequestMapping("/dataClientLevel")
public class dataClientLevelController {
    @Autowired
    private dataClientLevelService dataclientlevelService;



    @RequestMapping(value = "/getLevel",method = RequestMethod.GET)
    public RespBean getArea(){
        List<dataClientLevel> areas = dataclientlevelService.getLevel();
        if(areas!=null){
            return RespBean.success("查询成功",areas);
        }
        return RespBean.error("查询失败");
    }

    //    分页查询用户列表
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public RespPageBean getLevelList(@RequestParam(value = "currentPage",defaultValue = "1")int currentPage, @RequestParam(value = "size",defaultValue = "10")int size){
        return dataclientlevelService.getLevelList(currentPage,size);
    }

    @RequestMapping(value ="/add",method = RequestMethod.POST)
    public RespBean add(@RequestBody dataClientLevel dataclientlevel){
        if(StringUtils.isEmpty(dataclientlevel.getDclName())){
            return RespBean.error("等级名称为空");
        }
        dataClientLevel dataclientlevel1 = dataclientlevelService.getOne(new QueryWrapper<dataClientLevel>().eq("dclName", dataclientlevel.getDclName()));
        if (dataclientlevel1 != null) {
            return RespBean.error("等级名称已被使用");
        }
        dataclientlevel.setDelMark(1);
        boolean save = dataclientlevelService.save(dataclientlevel);
        if (save) return RespBean.success("添加成功");
        return RespBean.error("添加失败");
    }

    //    修改信息
    @RequestMapping(value = "/infoModify",method = RequestMethod.PUT)
    public RespBean changeUser(@RequestBody dataClientLevel dataclientlevel) {
        dataClientLevel dataclientlevel1 = dataclientlevelService.getOne(new QueryWrapper<dataClientLevel>().eq("dclId", dataclientlevel.getDclId()));
        if (dataclientlevel1 != null) {
            boolean update = dataclientlevelService.updateById(dataclientlevel);
            if (update) {
                return RespBean.success("修改信息成功", dataclientlevel);
            }
        }
        return RespBean.error("该信息不存在");
    }

    @RequestMapping(value = "/delete",method = RequestMethod.DELETE)
    public RespBean deleteUser(@RequestParam("dclId") int id){
        boolean removeById = dataclientlevelService.removeById(id);
        if(removeById){
            return RespBean.success("删除成功");
        }
        return RespBean.error("删除失败");
    }

    //    批量删除
    @RequestMapping(value = "/deletebatch",method = RequestMethod.POST)
    public RespBean deleteUser(@RequestBody List<Integer> ids){
        boolean remove = dataclientlevelService.removeByIds(ids);
        if(remove){
            return RespBean.success("删除成功");
        }
        return RespBean.error("删除失败");
    }
}
