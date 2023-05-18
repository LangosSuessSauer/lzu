package de.ookaSS23.lzu.controller;

import de.ookaSS23.lzu.command.ComponentOperation;
import de.ookaSS23.lzu.command.StopComponentOperation;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLDecoder;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.logging.Logger;

public class ComponentAssembler {

    private static final Logger logger = Logger.getLogger(ComponentAssembler.class.getName());
    private static ComponentAssembler instance;

    private Deque<ComponentOperation> componentOperations = new ArrayDeque<>();
    private List<ComponentWrapper> componentWrappers = new ArrayList<>();

    private ComponentAssembler()  {}

    public static ComponentAssembler getInstance ()  {
        if (ComponentAssembler.instance == null) {
            ComponentAssembler.instance = new ComponentAssembler();
        }
        return ComponentAssembler.instance;
    }

    public void addComponentOperation(ComponentOperation componentOperation) {
        componentOperations.add(componentOperation);
    }

    public void executeComponentOperations(){
        while(!componentOperations.isEmpty()){
            componentOperations.pop().execute();
            logger.info("Operation was executed.");
        }
    }

    public ComponentWrapper loadComponentFromJar(Component component){
        String path = ComponentWrapper.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        String decodedPath = null;
        try {
            decodedPath = URLDecoder.decode(path + "../../lib/"+component.toString(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        String pathToJar = decodedPath.substring(1);

        JarFile jarFile = null;
        try {
            jarFile = new JarFile(pathToJar);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Enumeration<JarEntry> e = jarFile.entries();
        URL[] urls = new URL[0];
        try {
            urls = new URL[]{ new URL("jar:file:" + pathToJar+"!/") };
        } catch (MalformedURLException ex) {
            throw new RuntimeException(ex);
        }
        URLClassLoader cl = URLClassLoader.newInstance(urls);

        List<Class> classList = new ArrayList<>();
        while(e.hasMoreElements()) {
            JarEntry je = e.nextElement();
            if (je.isDirectory() || !je.getName().endsWith(".class")) continue;

            String className = je.getName().substring(0, je.getName().length() - 6);
            className = className.replace('/', '.');
            Class c = null;
            try {
                c = cl.loadClass(className);
            } catch (ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            }
            classList.add(c);
        }

        Class<? extends Annotation> startAnnotation = null;
        Class<? extends Annotation> stopAnnotation = null;
        for(Class clazz: classList) {
            String [] classNameParts = clazz.getName().split("\\.");
            if(classNameParts[classNameParts.length-1].equals("Start")){
                startAnnotation = clazz;
            }
            if(classNameParts[classNameParts.length-1].equals("Stop")){
                stopAnnotation = clazz;
            }
        }

        String invokeMethodName = "", stopMethodName = "";
        Class invokeClass = null, stopClass = null;
        for(Class clazz: classList){
            Constructor[] constructors = clazz.getConstructors();
            if (!Modifier.isAbstract(clazz.getModifiers()) &&  constructors.length > 0) {
                Constructor<?> constructor = constructors[0];
                Object instance = null;
                try {
                    instance = constructor.newInstance();
                } catch (InstantiationException ex) {
                    throw new RuntimeException(ex);
                } catch (IllegalAccessException ex) {
                    throw new RuntimeException(ex);
                } catch (InvocationTargetException ex) {
                    throw new RuntimeException(ex);
                }
                for (Method method : instance.getClass().getDeclaredMethods()) {
                    if (method.isAnnotationPresent(startAnnotation)) {
                        invokeMethodName= method.getName();
                        invokeClass = clazz;
                        logger.info("Start Method was found");
                    }
                    if (method.isAnnotationPresent(stopAnnotation)) {
                        stopMethodName= method.getName();
                        stopClass = clazz;
                        logger.info("Stop Method was found");
                    }
                }
            }
        }
        if(Objects.isNull(invokeClass)) logger.info("Invoke Method wasn't found.");
        if(Objects.isNull(stopClass)) logger.info("Invoke Method wasn't found.");

        ComponentWrapper componentWrapper = new ComponentWrapper(classList, component.toString(), invokeMethodName, invokeClass, stopMethodName, stopClass);
        componentWrappers.add(componentWrapper);
        return componentWrapper;
    }

    public void stopAllThreads(){
        for(ComponentWrapper componentWrapper: componentWrappers){
            addComponentOperation(new StopComponentOperation(componentWrapper));
        }
        executeComponentOperations();
        ComponentOperation.threadPool.shutdownNow();
    }

    public List<ComponentWrapper> getComponentWrappers() {
        return componentWrappers;
    }

    public enum Component {
        COMPONENT_A("componentA.jar"),
        COMPONENT_B("componentB.jar")
        ;
        private final String text;
        Component(final String text) {
            this.text = text;
        }
        @Override
        public String toString() {
            return text;
        }
    }

}
