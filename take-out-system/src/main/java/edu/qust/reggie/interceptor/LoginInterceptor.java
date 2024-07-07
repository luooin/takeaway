package edu.qust.reggie.interceptor;

import com.alibaba.fastjson.JSON;
import edu.qust.reggie.common.BaseContext;
import edu.qust.reggie.common.R;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * @version 1.0
 * @description 登录功能的处理器拦截器，用户未登录状态下对部分页面进行拦截
 */

public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 判断登录状态，如果已经登录，直接放行 ----- 后台系统登录检测
        if (request.getSession().getAttribute("employee") != null) {
            // 将用户 id 保存到ThreadLocal中
            Long empId = (long) request.getSession().getAttribute("employee");
            BaseContext.setCurrentId(empId);
            return true;
        }
        // 判断登录状态，如果已经登录，直接放行 ----- 移动端登录检测
        if (request.getSession().getAttribute("user") != null) {
            // 将用户 id 保存到ThreadLocal中
            Long userId = (long) request.getSession().getAttribute("user");
            BaseContext.setCurrentId(userId);
            return true;
        }

        // 未登录通过输出流向前端页面响应数据（request.js 47）
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
        return false;
    }
}
