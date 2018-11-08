/*
* Copyright (c) 2016 Panda.HuangWei. All Rights Reserved.
*/
package com.hw.util;

import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import com.hw.entity.system.Menu;
/**
 * Created : Panda.HuangWei
 * Date : 2016/9/5 14:52
 */
public class Jurisdiction {
    /**
     * 访问权限及初始化按钮权限(控制按钮的显示)
     * @param menuUrl  菜单路径
     * @return
     */
    public static boolean hasJurisdiction(String menuUrl){
        //判断是否拥有当前点击菜单的权限（内部过滤,防止通过url进入跳过菜单权限）
        /**
         * 根据点击的菜单的xxx.do去菜单中的URL去匹配，当匹配到了此菜单，判断是否有此菜单的权限，没有的话跳转到404页面
         * 根据按钮权限，授权按钮(当前点的菜单和角色中各按钮的权限匹对)
         */
        //shiro管理的session
        Subject currentUser = SecurityUtils.getSubject();
        Session session = currentUser.getSession();
        Boolean b = true;
        List<Menu> menuList = (List)session.getAttribute(Const.SESSION_allmenuList); //获取菜单列表

        for(int i=0;i<menuList.size();i++){
            for(int j=0;j<menuList.get(i).getSubMenu().size();j++){
                if(menuList.get(i).getSubMenu().get(j).getMENU_URL().split(".do")[0].equals(menuUrl.split(".do")[0])){
                    if(!menuList.get(i).getSubMenu().get(j).isHasMenu()){				//判断有无此菜单权限
                        return false;
                    }else{																//按钮判断
                        Map<String, String> map = (Map<String, String>)session.getAttribute(Const.SESSION_QX);//按钮权限
                        map.remove(Const.AUTH_ADD);
                        map.remove(Const.AUTH_DEL);
                        map.remove(Const.AUTH_EDIT);
                        map.remove(Const.AUTH_QUERY);
                        String MENU_ID =  menuList.get(i).getSubMenu().get(j).getMENU_ID();
                        String USERNAME = session.getAttribute(Const.SESSION_USERNAME).toString();	//获取当前登录者loginname
                        Boolean isAdmin = isAdmin(USERNAME);
                        map.put(Const.AUTH_ADD, (RightsHelper.testRights(map.get(Const.AUTH_ADDS), MENU_ID)) || isAdmin?"1":"0");
                        map.put(Const.AUTH_DEL, RightsHelper.testRights(map.get(Const.AUTH_DELS), MENU_ID) || isAdmin?"1":"0");
                        map.put(Const.AUTH_EDIT, RightsHelper.testRights(map.get(Const.AUTH_EDITS), MENU_ID) || isAdmin?"1":"0");
                        map.put(Const.AUTH_QUERY, RightsHelper.testRights(map.get(Const.AUTH_QUERYS), MENU_ID) || isAdmin?"1":"0");
                        session.removeAttribute(Const.SESSION_QX);
                        session.setAttribute(Const.SESSION_QX, map);	//重新分配按钮权限
                    }
                }
            }
        }
        return true;
    }

    /**
     * 按钮权限(方法中校验)
     * @param menuUrl  菜单路径
     * @param type  类型(add、del、edit、cha)
     * @return
     */
    public static boolean buttonJurisdiction(String menuUrl, String type){
        //判断是否拥有当前点击菜单的权限（内部过滤,防止通过url进入跳过菜单权限）
        /**
         * 根据点击的菜单的xxx.do去菜单中的URL去匹配，当匹配到了此菜单，判断是否有此菜单的权限，没有的话跳转到404页面
         * 根据按钮权限，授权按钮(当前点的菜单和角色中各按钮的权限匹对)
         */
        //shiro管理的session
        Subject currentUser = SecurityUtils.getSubject();
        Session session = currentUser.getSession();
        Boolean b = true;
        List<Menu> menuList = (List)session.getAttribute(Const.SESSION_allmenuList); //获取菜单列表

        for(int i=0;i<menuList.size();i++){
            for(int j=0;j<menuList.get(i).getSubMenu().size();j++){
                if(menuList.get(i).getSubMenu().get(j).getMENU_URL().split(".do")[0].equals(menuUrl.split(".do")[0])){
                    if(!menuList.get(i).getSubMenu().get(j).isHasMenu()){				//判断有无此菜单权限
                        return false;
                    }else{																//按钮判断
                        Map<String, String> map = (Map<String, String>)session.getAttribute(Const.SESSION_QX);//按钮权限
                        String MENU_ID =  menuList.get(i).getSubMenu().get(j).getMENU_ID();
                        String USERNAME = session.getAttribute(Const.SESSION_USERNAME).toString();	//获取当前登录者loginname
                        Boolean isAdmin = isAdmin(USERNAME);
                        if(Const.AUTH_ADD.equals(type)){
                            return ((RightsHelper.testRights(map.get(Const.AUTH_ADDS), MENU_ID)) || isAdmin);
                        }else if(Const.AUTH_DEL.equals(type)){
                            return ((RightsHelper.testRights(map.get(Const.AUTH_DELS), MENU_ID)) || isAdmin);
                        }else if(Const.AUTH_EDIT.equals(type)){
                            return ((RightsHelper.testRights(map.get(Const.AUTH_EDITS), MENU_ID)) || isAdmin);
                        }else if(Const.AUTH_QUERY.equals(type)){
                            return ((RightsHelper.testRights(map.get(Const.AUTH_QUERYS), MENU_ID)) || isAdmin);
                        }
                    }
                }
            }
        }
        return true;
    }

    private static boolean isAdmin(String userName) {
        return Const.ADMIN_CODE.equalsIgnoreCase(userName);
    }
}
