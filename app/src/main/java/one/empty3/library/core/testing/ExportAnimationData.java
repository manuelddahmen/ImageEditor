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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

/*__
 * Created by manuel on 01-06-17.
 */
public class ExportAnimationData {
    private final TestObjet animation;
    private final File file;
    private PrintWriter printWriter;

    public ExportAnimationData(File file, TestObjet animation) throws FileNotFoundException {
        this.file = file;
        this.animation = animation;

        printWriter = new PrintWriter(new FileOutputStream(file));

        printWriter.println("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>");

        printWriter.println("<exportData filename='" + file.getAbsoluteFile() +
                "' width='" + animation.getResx() + "' height='" + animation.getResy() + "' length='" + animation.getMaxFrames() + "'>");
    }

    public void writeGlobalData(String key, Object data) {

        printWriter.println("<global key='" + key + "'>" + data + "</global>");

    }

    public void writeFrameData(int frameNo, Object data) {

        printWriter.println("<frame frameNo='" + frameNo +
                "' type='" + data.getClass().getSimpleName() + "' javaType='" + data.getClass().getCanonicalName() +
                "'>" + data + "</global>");

    }

    public void end() {

        printWriter.println("</exportData>");
        printWriter.flush();
        printWriter.close();

    }
}
