package de.ookaSS23.lzu.command;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public interface ComponentOperation {
    ExecutorService threadPool = Executors.newFixedThreadPool(5);

    void execute();
}
