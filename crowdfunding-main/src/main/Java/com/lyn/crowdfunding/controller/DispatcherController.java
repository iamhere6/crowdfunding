package com.lyn.crowdfunding.controller;

import com.lyn.crowdfunding.bean.TAdmin;
import com.lyn.crowdfunding.bean.TMenu;
import com.lyn.crowdfunding.service.TAdminService;
import com.lyn.crowdfunding.service.TMenuService;
import com.lyn.crowdfunding.util.Const;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 页面跳转
 * 这里只包括登录，注册等功能，不包括登录成功之后的后台的功能
 * 为了更好的了解，我们加入日志跟踪
 */
@Controller
public class DispatcherController {

    // 日志跟踪
    Logger log = LoggerFactory.getLogger(DispatcherController.class);

    // 声明业务层接口
    @Autowired
    TAdminService adminService;
    @Autowired
    TMenuService menuService;

    /**
     * 跳转到系统主页面
     * @return
     */
    @RequestMapping("/index")
    public String index(){
        log.debug("跳转到系统主页面...");
        return "index";
    }

    /**
     *跳到登录主页面
     * @return
     */
    @RequestMapping("/login")
    public String login(){
        log.debug("跳转到登录主页面...");
        return "login";
    }

    /**
     *跳到后台主页面
     * menuList：存放菜单中的父级菜单
     * @return
     */
    @RequestMapping("/main")
    public String main(HttpSession session){
        log.debug("跳转到系统后台主页面...");
        // 存放父级菜单
        List<TMenu> menuList = menuService.listMenuAll();
        session.setAttribute("menuList", menuList);
        return "main";
    }

    /**
     *退出系统
     * 使用了重定向而不是请求转发
     *      原因：如果是请求转发，那么当刷新页面时，也会执行退出
     * @return
     */
    @RequestMapping("/logout")
    public String logout(HttpSession session){
        log.debug("注销登录...");
        if (session!=null){ // session默认存在的时间为30min
            session.removeAttribute(Const.LOGIN_ADMIN);
            session.invalidate();
        }
        return "redirect:/index";
    }

    /**
     * 开始登陆
     * @param loginacct  对应于login.jsp
     * @param loginpswd  对应于login.jsp
     * 注意：
     *        (1)这里传入的参数必须和login.jsp对应的name属性指相同
     *        (2)登录跳转到后台系统主页面采用的跳转方式是重定向，因为我们只需要登录一次。
     *           而如果采用请求转发，每次刷新的时候，系统又会执行一次登录操作，显然这不是我们想要的结果
     *           主要是为了避免表单重复提交
     * @return
     */
    @RequestMapping("/doLogin")
    public String doLogin(String loginacct, String loginpswd, HttpSession session, Model model){
        log.debug("开始登录...");
        log.debug("loginacct={}",loginacct);
        log.debug("loginpswd={}",loginpswd);
        // 封装
        Map<String,Object> paramMap = new HashMap<String,Object>();
        paramMap.put("loginacct", loginacct);
        paramMap.put("loginpswd", loginpswd);
        // 调用业务层
        try{
            TAdmin admin = adminService.getTAdminByLogin(paramMap);
            // admin 入 session域
            session.setAttribute(Const.LOGIN_ADMIN, admin);
            log.debug("登录成功...");
            return "redirect:/main";
            // return "main";
        }catch (Exception e){
            e.printStackTrace();
            log.debug("登录失败={}...",e.getMessage());
            model.addAttribute("message",e.getMessage());
            return "login";
        }
    }


}
