/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.crawler;

/**
 *
 * @author thuyv
 */
public class BaseThread extends Thread {

    public BaseThread() {
    }
    
    private static BaseThread instance;
    private static final Object LOCK = new Object();

    public static BaseThread getInstance() {
        synchronized (LOCK) {
            if (instance == null) {
                instance = new BaseThread();
            }
        }
        return instance;
    }
    
    private static boolean suspended = false;

    public static boolean isSuspended() {
        return suspended;
    }

    public static void setSuspended(boolean suspended) {
        BaseThread.suspended = suspended;
    }
    
    public void suspendThread() {
        setSuspended(true);
        System.out.println("Thread suspened!");
    }
    
    public synchronized void resumeThread() {
        setSuspended(false);
        notifyAll();
        System.out.println("Thread resume!");
    }
    
}
