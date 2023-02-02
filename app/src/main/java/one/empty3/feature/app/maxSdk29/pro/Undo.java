/*
 * Copyright (c) 2023.
 *
 *
 *  Copyright 2012-2023 Manuel Daniel Dahmen
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

package one.empty3.feature.app.maxSdk29.pro;

import android.graphics.Bitmap;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javaAnd.awt.image.imageio.ImageIO;

public class Undo {
    private static int current = -1;
    private static Undo currentUndo = null;

    private List<DataApp> data = new ArrayList<DataApp>();

    public static Undo getUndo() {
        if (currentUndo == null) {
            currentUndo = new Undo();
        }
        return currentUndo;
    }

    private List<DataApp> getData() {
        return data;
    }

    private void setData(List<DataApp> data) {
        this.data = data;
    }

    public void onClickUndo() {
        if (data.size() > 0) {
            current--;
            if (current < 0) {
                current++;
            }
        }
    }

    private void invalidate() {
        throw new UnsupportedOperationException("Not implemented");
    }

    public void onClickRedo() {
        current++;
        if (current >= data.size()) {
            current--;
        }
    }

    public void doStep(DataApp dataApp) {
        if (data.size() > current + 1) {
            data.subList(current + 1, data.size()).clear();
        }
        data.add(dataApp);
    }

    public DataApp getDataApp() {
        if (data.size() >= current && data.get(current) != null) {
            return data.get(current);
        } else
            return null;
    }

    public File getCurrentFile() {
        return data.get(data.size() - 1).getImage();
    }

    public File getCurrentOriginalFile() {
        return data.get(data.size() - 1).getOriginalSizeImage();
    }

    public Bitmap getCurrentPhoto() {
        return Objects.requireNonNull(ImageIO.read(data.get(data.size() - 1).getImage())).bitmap;
    }

    public Bitmap getCurrentOriginalPhoto() {
        return Objects.requireNonNull(ImageIO.read(data.get(data.size() - 1).getOriginalSizeImage())).bitmap;
    }

    private Undo() {
    }
}
