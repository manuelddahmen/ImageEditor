/*
 *  This file is part of Empty3.
 *
 *     Empty3 is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     Empty3 is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with Empty3.  If not, see <https://www.gnu.org/licenses/>. 2
 */

/*
 * This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>
 */

/*

 Vous êtes libre de :

 */
package one.empty3.library.core.tribase;

public class Instance {

    private BaseGenerator instance;
    private ThreadInstance thread;

    public Instance(BaseGenerator bg, Params params) {
        instance = bg;
        thread = new ThreadInstance(params);
        thread.start();
    }

    public void kill() {
        thread.setStopped(true);
        thread.setKilled(true);
    }

    public void pauseInstance() {
        thread.setPaused(true);
    }

    public void restartInstance() {
        thread.setStopped(true);
        thread.setStarted(true);
    }

    public void startInstance() {
        thread.setStarted(true);
    }

    public void stopInstance() {
        thread.setStopped(true);
    }

    private class ThreadInstance extends Thread {

        private boolean started = false;
        private boolean stopped = false;
        private boolean paused = false;
        private boolean killed = false;

        public ThreadInstance(Params params) {
            instance.setParams(params);
        }

        public void run() {
            while (!killed) {

                while (!stopped & started & !paused) {
                    instance.initFrame();
                    instance.computeFrame();
                    instance.showFrame();
                    instance.computeFrame();
                }
                try {
                    sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        public void setKilled(boolean killed) {
            this.killed = killed;
        }

        public void setPaused(boolean paused) {
            this.paused = paused;
        }

        public void setStarted(boolean started) {
            this.started = started;
        }

        public void setStopped(boolean stopped) {
            this.stopped = stopped;
        }

    }
}
