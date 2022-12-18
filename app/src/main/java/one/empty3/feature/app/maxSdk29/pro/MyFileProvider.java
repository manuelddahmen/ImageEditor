package one.empty3.feature.app.maxSdk29.pro;

import androidx.core.content.FileProvider;

class MyFileProvider extends FileProvider {
    public MyFileProvider() {
        super(R.xml.file_paths);
    }
}
