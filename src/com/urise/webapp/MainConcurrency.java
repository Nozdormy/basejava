package com.urise.webapp;

import java.util.ArrayList;
import java.util.List;

public class MainConcurrency {

    public static final int THREADS_NUMBERS = 10_000;
    private static int counter;

    public static void main(String[] args) {
        System.out.println(Thread.currentThread().getName());
        Thread thread0 = new Thread() {
            @Override
            public void run() {
                System.out.println(getName() + " " + getState());
            }
        };
        thread0.start();

        new Thread(() -> System.out.println(Thread.currentThread().getName() + " " +
                Thread.currentThread().getState())).start();

        System.out.println(thread0.getState());
        MainConcurrency mainConcurrency = new MainConcurrency();

        List<Thread> threads = new ArrayList<>(THREADS_NUMBERS);
        for (int i = 0; i < THREADS_NUMBERS; i++) {
            Thread thread = new Thread(() -> {
                for (int j = 0; j < 100; j++) {
                    mainConcurrency.inc();
                }
            });
            thread.start();
            threads.add(thread);
        }
        threads.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        System.out.println(counter);

        final String lock1 = "lock1";
        final String lock2 = "lock2";
        deadlock(lock1, lock2);
        deadlock(lock2, lock1);
    }

    private static void deadlock(String lock1, String lock2) {
        new Thread(() -> {
            System.out.println("Waiting - " + lock1);
            synchronized (lock1) {
                System.out.println("Holding - " + lock1);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("Waiting - " + lock2);
                synchronized (lock2) {
                    System.out.println("Holding - " + lock2);
                }
            }
        }).start();
    }

    private synchronized void inc() {
        counter++;
    }
}