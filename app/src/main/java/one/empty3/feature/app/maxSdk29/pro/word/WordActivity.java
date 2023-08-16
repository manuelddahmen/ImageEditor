package one.empty3.feature.app.maxSdk29.pro.word;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.camera.camera2.interop.ExperimentalCamera2Interop;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import one.empty3.feature.app.maxSdk29.pro.ActivitySuperClass;
import one.empty3.feature.app.maxSdk29.pro.R;

public class WordActivity extends ActivitySuperClass {
    private WordViewModel mWordViewModel;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.recyclerview_words);

        RecyclerView recyclerView = findViewById(R.id.recyclerViewWords);
        final WordListAdapter adapter = new WordListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mWordViewModel = ViewModelProviders.of(this).get(WordViewModel.class);

        mWordViewModel.getAllWords().observe(this, new Observer<List<Word>>() {
            @Override
            public void onChanged(@Nullable final List<Word> words) {
                // Update the cached copy of the words in the adapter.
                adapter.setWords(words);
            }
        });

    }
}
