package org.csu.mypetstore.controller;

import jakarta.servlet.*;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.csu.mypetstore.service.LogService;
import org.csu.mypetstore.vo.SignVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;


import java.io.IOException;
@Component
public class Myfilter implements Filter {

    private LogService logService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //过滤器注入service，原因：过滤器执行的时候，service还没有被实例化注入
        ServletContext sc = filterConfig.getServletContext();
        WebApplicationContext cxt = WebApplicationContextUtils.getWebApplicationContext(sc);
        if(cxt != null) {
            if (logService == null) {
                logService = cxt.getBean(LogService.class);
            }
        }

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        System.out.println("---------您已进入过滤器222----------");
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpSession session = httpRequest.getSession();
        SignVO signVO = (SignVO) session.getAttribute("userInfo");
        String username = signVO == null ? "" : signVO.getLoginAccount().getUsername();
        String requestURI = httpRequest.getRequestURI();
        String operation = "";

        if (requestURI.contains("/viewItem")) {
            String itemId = httpRequest.getParameter("itemId");
            operation = "View Item - itemId: " + itemId;
        } else if (requestURI.contains("/addItemToCart")) {

            String workingItemId = httpRequest.getParameter("workingItemId");
            operation = "Add Item to Cart - workingItemId: " + workingItemId;
        } else if (requestURI.contains("/removeCartItem")) {
            String workingItemId = httpRequest.getParameter("workingItemId");
            operation = "Remove Item from Cart - workingItemId: " + workingItemId;
        }
        // 记录用户行为
        if (!username.isEmpty() && !operation.isEmpty()) {

            logService.addRecord(username, operation);
        }
        filterChain.doFilter(servletRequest, servletResponse);

    }

}



