package com.urise.webapp;

public class MainConcurrency2 {
    public static void main(String[] args) {

        final Object a = new Object();
        final Object b = new Object();

        new Thread(() -> {
            synchronized (a) {
                System.out.println("1");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                synchronized (b) {
                    System.out.println("11");
                }
            }
        }).start();

        new Thread(() -> {
            synchronized (b) {
                System.out.println("2");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                synchronized (a) {
                    System.out.println("22");
                }
            }
        }).start();
    }
}