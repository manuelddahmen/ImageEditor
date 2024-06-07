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

package one.empty3.library.core.lighting;

import one.empty3.library.*;

public class Material {
    double Ka, Kd, Ks;
    Representable r;

    public Material(double Ka, double Kd, double Ks, Representable r) {
        this.r = r;
        this.Ka = Ka;
        this.Kd = Kd;
        this.Ks = Ks;
    }

    public double getKa() {
        return Ka;
    }

    public double getKd() {
        return Kd;
    }

    public double getKs() {
        return Ks;
    }

    public Representable getRe() {
        return r;
    }

}
