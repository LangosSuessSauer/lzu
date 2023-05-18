package de.ookaSS23.lzu.command;

import de.ookaSS23.lzu.controller.ComponentWrapper;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ThreadPoolExecutor;

public class StartComponentOperation implements ComponentOperation {
    private ComponentWrapper componentWrapper;

    public StartComponentOperation(ComponentWrapper componentWrapper) {
        this.componentWrapper = componentWrapper;
    }

    @Override
    public void execute() {
        if (componentWrapper.getState() != ComponentWrapper.State.RUNNING) {
            threadPool.submit(() -> {
                componentWrapper.run();
            });
        }
    }
}
