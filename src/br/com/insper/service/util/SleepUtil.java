package br.com.insper.service.util;

public class SleepUtil {

    public static void sleep(Long milis) {
        try {
            Thread.sleep(milis);
        } catch (InterruptedException e) {
            System.out.println("Thread Sleep Exception\n");
            e.printStackTrace();
        }
    }
}
