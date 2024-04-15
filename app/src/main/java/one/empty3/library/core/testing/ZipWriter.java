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

package one.empty3.library.core.testing;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipWriter {

    private ZipOutputStream zos;

    public void addFile(ByteArrayOutputStream baos) {
        // TODO Auto-generated method stub

    }

    public void addFile(File image) throws IOException {
        byte[] b = new byte[1024];
        FileInputStream fis = new FileInputStream(image);
        fis.read(b, 0, b.length);
        ZipEntry ze = new ZipEntry(image.getName());
        ze.setSize((long) b.length);
        zos.setLevel(6);
        zos.putNextEntry(ze);
        zos.write(b, 0, b.length);
    }

    public void addFile(String name, byte[] b) throws IOException {
        ZipEntry ze = new ZipEntry(name);
        ze.setSize((long) b.length);
        zos.setLevel(6);
        zos.putNextEntry(ze);
        zos.write(b, 0, b.length);
    }

    public void end() throws IOException {
        zos.finish();
        zos.close();
    }

    public void init(File zipf) throws FileNotFoundException {
        zos = new ZipOutputStream(new FileOutputStream(zipf));
    }
}
