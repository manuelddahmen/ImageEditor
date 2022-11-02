package one.empty3.library;





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
/*__****************************************************************************
 * Copyright (c) 2014, Art Clarke.  All rights reserved.
 *
 * This file is part of Humble-Video.
 *
 * Humble-Video is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Humble-Video is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with Humble-Video.  If not, see <http://www.gnu.org/licenses/>.
 ***************************************************/


import android.graphics.Bitmap;

import java.util.ArrayList;
import java.io.File;

public abstract class VideoDecoder extends Thread {
    protected boolean eof = false;
    protected TextureMov text;
    protected File file;
    protected boolean stop = false;
    protected static final long MAXSIZE = 4;
    protected ArrayList<Bitmap> imgBuf = new ArrayList();


    /***
     * init, start, run, and block on maxsize reached
     * @param file video to draw on surface
     * @param refTextureMov texture to apply
     */
    public VideoDecoder(File file, TextureMov refTextureMov) {
        this.file = file;
        this.text = refTextureMov;
        // start();

    }

    public int size() {
        return imgBuf.size();
    }

    public boolean isClosed() {
        return eof;
    }

    public Bitmap current() {

        Bitmap c = imgBuf.get(0);
        imgBuf.remove(0);
        return c;

    }
}
