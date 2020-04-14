package com.lyn.crowdfunding.service;

import com.github.pagehelper.PageInfo;
import com.lyn.crowdfunding.bean.TAdmin;

import java.util.Map;

/**
 * 业务层登录接口
 */
public interface TAdminService {

    // 根据登录信息查询用户名、密码
    TAdmin getTAdminByLogin(Map<String, Object> paramMap);

    // 查询数据表admin   分页查询
    PageInfo<TAdmin> listAdminPage(Map<String, Object> paramMap);

    // 新增用户
    void saveTAdmin(TAdmin admin);

    // 根据id查询用户
    TAdmin getTAdminById(Integer id);

    // 修改用户
    void updateTAdmin(TAdmin admin);

    // 删除用户
    void deleteTAdmin(Integer id);
}
