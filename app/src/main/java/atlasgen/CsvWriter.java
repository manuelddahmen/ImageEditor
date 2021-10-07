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

package atlasgen;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/*__
 * Created by Win on 01-07-18.
 */
public class CsvWriter {
    private final String columnSeparator;
    private final String lineSeparator;
    FileOutputStream fis;

    public CsvWriter(String lineSeparator,
                     String columnSeparator
    ) {
        this.lineSeparator = lineSeparator;
        this.columnSeparator = columnSeparator;
    }

    public int openFile(File csvFile) {
        try {
            fis = new FileOutputStream(csvFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return -1;
        }
        return 0;
    }

    public int writeLine(String[] lineArray) {
        byte[] bytes;
        StringBuilder sb = new StringBuilder(100);
        int i = 0;
        for (String column : lineArray) {
            sb.append(column);
            if (i < lineArray.length - 1)
                sb.append(columnSeparator);
        }
        sb.append(lineSeparator);

        try {
            fis.write(sb.substring(0).getBytes());
            return 0;
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }

    }

    public int closeFile() {
        try {
            fis.flush();
        } catch (IOException e) {
            e.printStackTrace();
            return -2;
        }
        try {
            fis.close();
            return 0;
        } catch (IOException e) {

            e.printStackTrace();
            return -1;
        }
    }

}
