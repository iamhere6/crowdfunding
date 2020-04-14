package com.lyn.crowdfunding.service.impl;

import com.github.pagehelper.PageInfo;
import com.lyn.crowdfunding.bean.TAdmin;
import com.lyn.crowdfunding.bean.TAdminExample;
import com.lyn.crowdfunding.exception.LoginException;
import com.lyn.crowdfunding.mapper.TAdminMapper;
import com.lyn.crowdfunding.service.TAdminService;
import com.lyn.crowdfunding.util.AppDateUtils;
import com.lyn.crowdfunding.util.Const;
import com.lyn.crowdfunding.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 业务层登录实现类
 */
@Service
public class TAdminServiceImpl implements TAdminService {

    @Autowired
    TAdminMapper adminMapper;

    public TAdmin getTAdminByLogin(Map<String, Object> paramMap) {
        // 查询用户
        String loginacct = (String)paramMap.get("loginacct");
        String loginpswd = (String)paramMap.get("loginpswd");
        // 判断用户是否为null
        TAdminExample example = new TAdminExample();
        example.createCriteria().andLoginacctEqualTo(loginacct);
        List<TAdmin> list = adminMapper.selectByExample(example);
        if (list==null || list.size()==0){
            throw new LoginException(Const.LOGIN_LOGINACCT_ERROR);
        }
        TAdmin admin = list.get(0);
        if (!admin.getUserpswd().equals(MD5Util.digest(loginpswd))){
            throw new LoginException(Const.LOGIN_USERPSWD_ERROR);
        }
        return admin;


        /*if (list!=null && list.size()==1){
            TAdmin admin = list.get(0);
            // 判断密码是否一致
            if (admin.getUserpswd().equals(loginpswd)){
                // 返回结果
                return admin;
            }else {
                throw new LoginException(Const.LOGIN_USERPSWD_ERROR);
            }
        }else {
            throw new LoginException(Const.LOGIN_LOGINACCT_ERROR);
        }*/
        // return null;
    }

    public PageInfo<TAdmin> listAdminPage(Map<String, Object> paramMap) {
        // 告知分页查询的条件
        TAdminExample example = new TAdminExample();
        // example.setOrderByClause("createtime desc"); // 或者采用分页合理化的方式代替

        List<TAdmin> list =  adminMapper.selectByExample(example);
        PageInfo<TAdmin> page = new PageInfo<TAdmin>(list,5);
        return page;
    }

    public void saveTAdmin(TAdmin admin) {
        // 系统赋予新增用户的密码初值,增加时间初值
        admin.setUserpswd(MD5Util.digest(Const.DEFAULT_USERPSWD));
        admin.setCreatetime(AppDateUtils.getFormatTime());
        // 如果admin中某字段值为null，那么这个字段就不参与保存
        adminMapper.insertSelective(admin);
    }

    public TAdmin getTAdminById(Integer id) {
        return adminMapper.selectByPrimaryKey(id);
    }

    public void updateTAdmin(TAdmin admin) {
        adminMapper.updateByPrimaryKeySelective(admin);
    }

    public void deleteTAdmin(Integer id) {
        adminMapper.deleteByPrimaryKey(id);
    }
}
