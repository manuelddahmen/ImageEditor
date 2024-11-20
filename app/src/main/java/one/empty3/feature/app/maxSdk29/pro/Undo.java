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

package one.empty3.feature.app.maxSdk29.pro;

import android.graphics.Bitmap;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import one.empty3.ImageIO;

public class Undo {
    private static int current = 0;
    private List<DataApp> data = new ArrayList<DataApp>();
    private static Undo currentUndo;

    public static Undo getUndo () {
        if(currentUndo==null) {
            currentUndo = new Undo();
        }
        return currentUndo;
    }
    private Undo() {

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
        } else current = 0;
    }

    private void invalidate() {
        throw new UnsupportedOperationException("Not implemented");
    }

    public void add(DataApp dataApp) {
        if (current < 0) {
            data.add(0, dataApp);
        } else if (current + 1 > data.size()) {
            data.add(dataApp);
        } else {
            data.add(current + 1, dataApp);
        }
        current = data.indexOf(dataApp);
    }

    public DataApp getDataApp() {
        if (data.size() > current && current>=0 && data.get(current) != null) {
            return data.get(current);
        } else {
            //return new DataApp();
        }
        return null;
    }

    public File getCurrentFile() {
        if(data.isEmpty() || data.get(current)==null || data.get(current).getImage()==null)
            return null;
        return data.get(current).getImage();
    }

    public File getCurrentOriginalFile() {
        return data.get(data.size() - 1).getOriginalSizeImage();
    }

    public Bitmap getCurrentPhoto() {
        return Objects.requireNonNull(ImageIO.read(data.get(data.size() - 1).getImage())).getImage();
    }

    public Bitmap getCurrentOriginalPhoto() {
        return Objects.requireNonNull(ImageIO.read(data.get(data.size() - 1).getOriginalSizeImage())).getImage();
    }

    @NotNull
    public static Object isUndoInitialized() {
        if (currentUndo == null)
            return false;
        return true;
    }

    public DataApp back() {
        if(current>0 && data.size()>0) {
            current--;
            return getDataApp();
        }
        return getDataApp();
    }
    public DataApp next() {
        if(current<data.size()-1 && data.size()>1) {
            current++;
            return getDataApp();
        }
        // idée créer une nouvelle image si current au dernier élément. Donc new BufferesImage()
        return getDataApp();
    }

    private DataApp getCurrentData() {
        return data.get(current);
    }

    public void addNull(Object o) {
        add(new DataApp(null));
    }
}
