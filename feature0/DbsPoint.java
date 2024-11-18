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

package one.empty3.feature;

public class DbsPoint implements DataPoint {
    private double[] point = new double[2];
    private int clusterId;

    public DbsPoint(double[] d) {
        this.point = d;
    }

    @Override
    public double distance(DataPoint datapoint) {
        return Math.sqrt(
                (datapoint.getX() - point[0]) * (datapoint.getX() - point[0])
                        + (datapoint.getY() - point[1]) * (datapoint.getY() - point[1])
        );
    }

    @Override
    public void setCluster(int id) {
        clusterId = id;
    }

    @Override
    public int getCluster() {
        return clusterId;
    }

    @Override
    public int getX() {
        return (int) point[0];
    }

    @Override
    public int getY() {
        return (int) point[1];
    }
}
