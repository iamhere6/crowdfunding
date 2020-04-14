package com.lyn.crowdfunding.service.impl;

import com.lyn.crowdfunding.bean.TMenu;
import com.lyn.crowdfunding.mapper.TMenuMapper;
import com.lyn.crowdfunding.service.TMenuService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class TMenuServiceImpl implements TMenuService {

    // 日志跟踪
    Logger log = LoggerFactory.getLogger(TMenuServiceImpl.class);

    @Autowired
    TMenuMapper menuMapper;

    public List listMenuAll() {

        List<TMenu> menuList = new ArrayList<TMenu>(); // 存放父级菜单
        Map<Integer,TMenu> cache = new HashMap<Integer, TMenu>(); // 为children属性赋值

        List<TMenu> allList = menuMapper.selectByExample(null); // 表示查询所有
        for (TMenu tmenu : allList){
            if (tmenu.getPid() == 0){
                menuList.add(tmenu);
                cache.put(tmenu.getId(), tmenu);
            }
        }
        for (TMenu tmenu : allList){
            if (tmenu.getPid() != 0){
                Integer pid = tmenu.getPid();
                TMenu parent = cache.get(pid);
                // 根据子菜单pid查找父菜单id,然后根据父菜单children属性进行父子关系组合
                parent.getChildren().add(tmenu);
            }
        }
        log.debug("菜单={}",menuList);
        return menuList;
    }
}
