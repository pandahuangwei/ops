package com.hw.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Panda.HuangWei.
 * @version 1.0 .
 * @since 2017.03.19 17:41.
 */
public class InitiativeWorker extends Thread {
    private final static Logger logger = LoggerFactory.getLogger(InitiativeWorker.class);

    boolean running = false;

    public InitiativeWorker() {
        running = true;
        this.start();
    }

    //保存记录
    @Override
    public void run() {
        while(running){
            try {
                sleep(100);
            } catch (InterruptedException e) {
                logger.error("",e);
            }
        }
    }

    //停止线程，并保存最后的记录
    public void stopWorker() {
        running= false;
    }
}
