/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.listener;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import project.miscellaneous.PalatteData;
import project.utils.ConfigHelper;

/**
 * Web application lifecycle listener.
 *
 * @author thuyv
 */
public class MyContextServletListener implements ServletContextListener {
    private ScheduledExecutorService ses = null;

    @Override
    public void contextInitialized(ServletContextEvent sce) {

        // init REAL_PATH
        ServletContext context = sce.getServletContext();
        String realPath = context.getRealPath("/");
        ConfigHelper.configRealPath(realPath);

        // read config host
        ConfigHelper.configHost();
        ConfigHelper.configImage();
        ConfigHelper.configCrawl();
        
        // init list all canvas
        PalatteData.initListCanvas();

        //
        System.out.println("Start ScheduledExecutorService ------------------");
        ses = Executors.newSingleThreadScheduledExecutor();
        ses.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
//                PalatteData.updateNewPalatte();
                PalatteData.getRandomImgFromUnsplash();
            }
        }, 0, 5, TimeUnit.MINUTES);
    }
    
//    private void runBackgound() {
//        System.out.println("run schedule ----------------------------");
//        System.out.println(System.nanoTime());
//    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        if (ses != null) {
            ses.shutdown();
            System.out.println("Shutdown ScheduledExecutorService -----------");
        }
    }
}
