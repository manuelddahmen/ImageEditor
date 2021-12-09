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
