package com.lyn.crowdfunding.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lyn.crowdfunding.bean.TAdmin;
import com.lyn.crowdfunding.service.TAdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

/**
 * 控制层 管理员功能
 */

@Controller
public class TAdminController {

    Logger log = LoggerFactory.getLogger(TAdminController.class);

    @Autowired
    TAdminService adminService;

    /**
     * 跳转到用户维护页面
     * 跳转之前分页查询数据库信息
     * PageHelper.startPage(pageNum,pageSize)  调用该方法后，在此方法后面的第一个myaits查询语句就会按照这个进行分页
     * PageInfo<TAdmin> page = adminService.listAdminPage(paramMap)
     * 则对第一次查道询的集合传入，可以获得更多的页面操作信息，封装在PageInfo 这个类上
     * @param pageNum      当前页数
     * @param pageSize     一页大小
     * @param model        传递参数
     * @return
     */
    @RequestMapping("/admin/index")
    public String index(@RequestParam(value = "pageNum",required = false,defaultValue = "1") Integer pageNum,
                        @RequestParam(value = "pageSize",required = false,defaultValue = "2") Integer pageSize,
                        Model model){
        log.debug("开始分页查询...");
        PageHelper.startPage(pageNum,pageSize); // 线程绑定
        Map<String,Object> paramMap = new HashMap<String, Object>();
        PageInfo<TAdmin> page = adminService.listAdminPage(paramMap);
        model.addAttribute("page",page);
        log.debug("跳转到用户维护界面...");
        return "admin/index"; // 根据视图解析
    }

    /**
     * 跳转到新增用户页面
     * @return
     */
    @RequestMapping("/admin/toAdd")
    public String toAdd(){
        return "admin/add";
    }

    /**
     * 跳转到修改用户页面
     * 根据id查询用户
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("/admin/toUpdate")
    public String toUpdate(Integer id,Model model){
        TAdmin admin = adminService.getTAdminById(id);
        log.debug("获取到的用户={}",admin.getLoginacct());
        model.addAttribute("admin",admin);
        return "admin/update";
    }

    /**
     * 执行用户新增
     * @param admin
     * @return
     */
    @RequestMapping("/admin/doAdd")
    public String doAdd(TAdmin admin){
        adminService.saveTAdmin(admin);
        //return "redirect:/admin/index";
        return "redirect:/admin/index?pageNum="+Integer.MAX_VALUE; // 分页合理化
    }

    /**
     * 执行用户修改
     * @param admin
     * @return
     */
    @RequestMapping("/admin/doUpdate")
    public String doUpdate(TAdmin admin,Integer pageNum){
        adminService.updateTAdmin(admin);
        return "redirect:/admin/index?pageNum="+pageNum;
    }

    /**
     * 执行用户删除
     * @param id
     * @return
     */
    @RequestMapping("/admin/doDelete")
    public String doDelete(Integer id,Integer pageNum){
        adminService.deleteTAdmin(id);
        return "redirect:/admin/index?pageNum="+pageNum;
    }





}
