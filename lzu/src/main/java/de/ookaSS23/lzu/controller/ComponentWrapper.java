package de.ookaSS23.lzu.controller;

import java.lang.reflect.*;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

public class ComponentWrapper implements Runnable {
    private static final Logger logger = Logger.getLogger(ComponentWrapper.class.getName());
    private List<Class> classList;
    private State state;
    private UUID id;
    private String componentName;
    private String invokeMethodName, stopMethodeName;

    private Class invokeClass, stopClass;


    public ComponentWrapper(List<Class> classList, String componentName, String invokeMethodName, Class invokeClass, String stopMethodeName, Class stopClass) {
        this.classList = classList;
        state = State.DEPLOYED;
        id = UUID.randomUUID();
        this.componentName = componentName;
        this.invokeMethodName = invokeMethodName;
        this.invokeClass = invokeClass;
        this.stopMethodeName = stopMethodeName;
        this.stopClass = stopClass;
    }

    @Override
    public void run() {

            if (state!=State.RUNNING) {
                executeMethod(invokeClass, invokeMethodName);
                state = State.RUNNING;
                logger.info("Component was started.");
            } else {
                logger.info("Component is already running.");
            }

    }

    private void executeMethod(Class clazz, String methodName){
        try {
            Constructor[] constructors = clazz.getConstructors();
            if (constructors.length > 0) {
                Constructor<?> constructor = constructors[0];
                Object instance = constructor.newInstance();
                Method method = instance.getClass().getMethod(methodName);
                method.invoke(instance);
            }
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }
        catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        catch (InstantiationException e) {
            throw new RuntimeException(e);
        }
        catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    public void stop() {
        if (state==state.RUNNING) {
            executeMethod(stopClass, stopMethodeName);
            state = state.STOPPED;
            logger.info("Component was stopped.");
            }
        else {
            logger.info("Component is already stopped.");
        }
    }

    public State getState() {
        return state;
    }

    public UUID getId() {
        return id;
    }

    public String getComponentName() {
        return componentName;
    }

    public String getInvokeMethodName() {
        return invokeMethodName;
    }

    public String getStopMethodeName() {
        return stopMethodeName;
    }

    public Class getInvokeClass() {
        return invokeClass;
    }

    public Class getStopClass() {
        return stopClass;
    }

    public enum State {
        DEPLOYED("deployed"),
        RUNNING("running"),
        STOPPED("stopped")
        ;
        private final String text;
        State(final String text) {
            this.text = text;
        }
        @Override
        public String toString() {
            return text;
        }
    }
}
