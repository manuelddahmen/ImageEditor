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

package one.empty3.library;

//import com.xuggle.mediatool.IMediaReader;


import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.File;
import java.util.ArrayList;


@RequiresApi(api = Build.VERSION_CODES.O)
public class TextureMov extends ITexture {
    Bitmap image;
    VideoDecoder defs;
    private File file = null;
    private int transparent = Color.valueOf(1, 1, 1).toArgb();

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
        init(file.getAbsolutePath());
    }

    public TextureMov() {
    }

    public TextureMov(String filename) {
        init(filename);
    }

    public void init(String filename) {
        this.file = new File(filename);
        defs = VideoDecoderFactory.createInstance(file, this);
        //defs.start();
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public int getColorAt(double u, double v) {


        if (image == null)
            return 0;

        int x = (int) (u * image.getWidth());
        int y = (int) (v * image.getHeight());
        if (x >= 0 && x < image.getWidth() && y >= 0 && y < image.getHeight()) {
            int rgb = image.getPixel(x, y);
            int a = rgb >> 24 & 0xFF;
            int r = rgb >> 16 & 0xFF;
            int g = rgb >> 8 & 0xFF;
            int b = rgb >> 0 & 0xFF;
            return rgb & 0x00FFFFFF;

        } else
            return Color.valueOf(0, 0, 0, 0).toArgb();
    }

    protected void current() {

        if (defs.size() > 0)
            image = defs.current();
    }


    @Override
    public void timeNext() {
        nextFrame();
    }

    @Override
    public void timeNext(long milli) {

    }


    public String toString() {
        return " texture ( \"" + file.getAbsolutePath() + "\")";
    }

    public MatrixPropertiesObject copy() throws CopyRepresentableError, IllegalAccessException, InstantiationException {
        return null;
    }


    public boolean nextFrame() {
        current();
        return image != null;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void setTransparent(Color black) {
        this.transparent = black.toArgb();
    }

    public Bitmap getImage() {
        return image;
    }
}
