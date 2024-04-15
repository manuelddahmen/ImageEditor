/*
 * Copyright (c) 2023.
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

package one.empty3;

import java.util.*;
import java.io.*;

public class Run {
    public static void main(String[] args) {
        try {
            List<String> args2 = new ArrayList<>();
            Properties properties = new Properties();
            properties.load(new FileInputStream("runtestobjetsub.txt"));
            Scanner scanIn = new Scanner(System.in);
            boolean delete = false;
            properties.forEach((key, value) -> {
                String[] line = new String[]{(String) key, (String) value};
                String s;
                System.out.println("var " + key + "\n\tdefault:\t" + value + "\n\tchange ? \t");

                s = scanIn.nextLine();


                if (s.length() > 0 && !s.equals("\n")) {
                    args2.add(line[0] + "=" + value);
                    properties.replace(key, s);

                } else {
                    args2.add(line[0] + "=" + value);
                    properties.replace(key, value);
                }
            });
            properties.store(new FileOutputStream("runtestobjetsub.txt"),
                    null);
            args = new String[args2.size()];
            for (int i = 0; i < args2.size(); i++)
                args[i] = args2.get(i);
            TestRun.main(args);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
