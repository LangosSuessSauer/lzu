package de.ookaSS23.lzu.command;

import de.ookaSS23.lzu.controller.ComponentWrapper;

public class StopComponentOperation implements ComponentOperation{
    private ComponentWrapper componentWrapper;
    public  StopComponentOperation(ComponentWrapper componentWrapper){
        this.componentWrapper = componentWrapper;
    }
    @Override
    public void execute() {
        componentWrapper.stop();
    }
}
