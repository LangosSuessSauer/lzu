package de.ookaSS23.lzu.command;

import de.ookaSS23.lzu.controller.ComponentWrapper;

import java.util.concurrent.ThreadPoolExecutor;
import java.util.logging.Logger;

public class DisplayStatusOperation implements ComponentOperation{
    private static final Logger logger = Logger.getLogger(DisplayStatusOperation.class.getName());
    private ComponentWrapper componentWrapper;
    public  DisplayStatusOperation(ComponentWrapper componentWrapper){
        this.componentWrapper = componentWrapper;
    }
    @Override
    public void execute() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        logger.info(String.format("Name of component: %s - State of component: %s - Id of component: %s - ThreadPoolSize - %d",
                componentWrapper.getComponentName(), componentWrapper.getState(), componentWrapper.getId(), ((ThreadPoolExecutor)threadPool).getPoolSize()));

    }
}
