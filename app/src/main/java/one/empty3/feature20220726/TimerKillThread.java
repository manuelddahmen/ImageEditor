package one.empty3.feature20220726;

import java.util.ArrayList;
import java.util.List;

public class TimerKillThread extends Thread {
    public static List<TimerKillThread> instances
            = new ArrayList<>();
    public int MAX_THREADS = 10;
    private final Thread thread;
    private long time;

    public TimerKillThread(Thread t) {

        this.thread = t;

        this.time = System.currentTimeMillis();

        while (instances.size() >= MAX_THREADS) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        thread.start();
    }

    @Override
    public void run() {
        instances.add(this);

        while ((System.currentTimeMillis() - time) < 4000L
                || instances.size() < MAX_THREADS) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        instances.remove(this);
        thread.interrupt();
    }

}
