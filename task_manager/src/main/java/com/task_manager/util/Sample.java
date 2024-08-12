package com.task_manager.util;

import java.util.concurrent.Callable;

public class Sample {
    public static void main(String[] args){


        Runnable runnable = () -> {
          System.out.println("Hello");
        };

        Callable callable = () -> {
          return "Callable Hello";
        };

        Thread thread = new Thread(runnable);

        thread.start();

        thread.run();

        thread.stop();
    }
}
