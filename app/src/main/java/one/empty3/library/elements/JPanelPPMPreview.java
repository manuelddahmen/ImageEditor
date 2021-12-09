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

package one.empty3.library.elements;

import one.empty3.library.ECBufferedImage;
import one.empty3.library.Scene;
import one.empty3.library.core.raytracer.RtScene;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class JPanelPPMPreview extends JPanel

{
    private Scene scene;
    private RtScene rtScene;
    private int typeScene;
    private StringBuilder ppmBuilder;
    private File recordFolder;
    private String filePrefix;
    private String timestampFormat;
    private String fileExtension;
    private String fileInfoPostfix;
    private Image previewImage;

    public JPanelPPMPreview(Scene scene) {

    }

    public void paint(Graphics graphics) {
        if (previewImage != null) {
            graphics.drawImage(previewImage, 0, 0, null, null);
        }
    }

    private void buildPreviewImage() {
        Graphics graphics = this.getGraphics();
        super.paint(graphics); // TODO CHECK

        PPMFileInputStream ppmFileInputStream = new PPMFileInputStream(ppmBuilder);

        // Read String
        // Complete with white, transparent or black

        ECBufferedImage ecBufferedImage = new ECBufferedImage(ppmFileInputStream);

    }
}
