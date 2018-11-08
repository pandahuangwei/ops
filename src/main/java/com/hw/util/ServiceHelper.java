/*
* Copyright (c) 2016 Panda.HuangWei. All Rights Reserved.
*/
package com.hw.util;
import com.hw.service.system.MenuService;
import com.hw.service.system.RoleService;
import com.hw.service.system.UserService;
/**
 * Created : Panda.HuangWei
 * Date : 2016/9/5 14:57
 */
public final class ServiceHelper {
    public static Object getService(String serviceName){
        //WebApplicationContextUtils.
        return Const.WEB_APP_CONTEXT.getBean(serviceName);
    }

    public static UserService getUserService(){
        return (UserService) getService("userService");
    }

    public static RoleService getRoleService(){
        return (RoleService) getService("roleService");
    }

    public static MenuService getMenuService(){
        return (MenuService) getService("menuService");
    }
}
