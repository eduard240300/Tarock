package com.eduard240300.TarockWebSite.Service;

import com.eduard240300.TarockWebSite.DBManager.DBManager;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class InitializerService implements ServletContextListener {

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // stuff to do on context destroy
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        if (DBManager.notConnected == true)
            DBManager.connect();
    }
}