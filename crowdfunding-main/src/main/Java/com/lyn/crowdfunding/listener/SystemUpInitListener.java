package com.lyn.crowdfunding.listener;

import com.lyn.crowdfunding.util.Const;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * 监听 application 对象的创建和销毁
 */
public class SystemUpInitListener implements ServletContextListener {

    // 日志跟踪
    Logger log = LoggerFactory.getLogger(SystemUpInitListener.class);

    // 当application创建时执行初始化方法
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext application = servletContextEvent.getServletContext();
        String contextPath = application.getContextPath();
        log.debug("当前应用上下文路径：{}",contextPath);
        // 当前应用上下文路径：/crowdfunding_main_war_exploded
        application.setAttribute(Const.PATH, contextPath);
    }

    // 当application销毁时执行销毁方法
    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        log.debug("当前应用application对象被销毁...");
    }
}
