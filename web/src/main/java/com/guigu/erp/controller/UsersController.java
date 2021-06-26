package com.guigu.erp.controller;

import com.github.pagehelper.PageInfo;
import com.guigu.erp.pojo.Users;
import com.guigu.erp.service.UsersService;
import com.guigu.erp.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/users")
public class UsersController {
    @Autowired
    private UsersService usersService;

    /**
     * 登录
     * @param loginId
     * @param password
     * @return
     */
    @RequestMapping("/login")
    public ResultUtil login(String loginId, String password, HttpServletRequest request) {
        return usersService.login(loginId, password,request);
    }

    //退出
    @RequestMapping("/logout")
    public void logout( HttpSession session) {
        session.invalidate();
    }

    /**
     * 条件分页查询
     * @param pageNo
     * @param pageSize
     * @param user
     * @return
     */
    @RequestMapping(value = "/page", produces = "application/json;charset=utf-8")
    public PageInfo<Users> page(@RequestParam(value = "pageNo", defaultValue = "1") int pageNo,
                                   @RequestParam(value = "pageSize", defaultValue = "8") int pageSize, Users user) {
        return usersService.queryPage(pageNo, pageSize, user);
    }

    /**
     * 查询用户名是否存在可用
     * @param name
     * @return
     */
    @RequestMapping(value = "/checkName", produces = "application/json;charset=utf-8")
    public ResultUtil checkName(String name) {
        return usersService.checkName(name);
    }

    /**
     * 新增
     *
     * @param userInfo
     * @return
     */
    @RequestMapping(value = "/insert", produces = "application/json;charset=utf-8")
    public ResultUtil insert(Users userInfo) {
        return usersService.insert(userInfo);
    }
    /**
     * 删除
     * @param id
     * @return
     */
    @RequestMapping("/delete/{id}")
    public ResultUtil delete(@PathVariable int id){
        return  usersService.deleteById(id);
    }

    @RequestMapping("/updatePwd")
    public boolean updatePwd(Users users){
        return  usersService.updatePwd(users);
    }

    @RequestMapping("/updatePhoto")
    public boolean updatePhoto(Users users){
        return  usersService.updatePhoto(users);
    }
}
