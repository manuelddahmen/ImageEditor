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

package one.empty3.library;

public class RFactory {
    String configName = "stable";

    public static void loadConfig() {

    }
    // get interfaces
    /*
    public static ZBuffer getZBufferInstance() {
        if(configName.equals("stable"))
            return new ZBufferImpl8();
        return new ZBufferImpl();
    }
    public static Vec getVecInstance() {
        if(configName.equals("stable"))
            return new Point3Dbak();
        return new Point3D();
    }
    */
}
