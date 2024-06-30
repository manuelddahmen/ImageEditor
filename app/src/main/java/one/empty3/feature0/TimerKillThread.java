/*
 * Copyright (c) 2024.
 *
 *
 *  Copyright 2023 Manuel Daniel Dahmen
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 *
 */

package one.empty3.feature0;

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
