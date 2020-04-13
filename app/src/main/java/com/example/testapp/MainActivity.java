package com.example.testapp;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;

public class MainActivity extends AppCompatActivity implements CharacterAdapter.LoadingCallback {

    RecyclerView recyclerView;
    CharacterAdapter characterAdapter;
    ProgressBar progressBar;
    CharacterViewModel viewModel;
    CompositeDisposable disposables;
    Module module;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        disposables = new CompositeDisposable();
        bindViews();
        bindModels();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        viewModel.onDestroy();
        disposables.clear();
    }

    private void bindModels() {
        module = Module.getInstance();
        viewModel = new CharacterViewModel(module.getCharacterStore());

        disposables.add(viewModel.getEndReachedObservable().observeOn(AndroidSchedulers.mainThread())
                .subscribe(aBoolean -> characterAdapter.setDoneLoading()));

        disposables.add(viewModel.getResponseReceivedObservable().observeOn(AndroidSchedulers.mainThread())
                .subscribe(aBoolean -> progressBar.setVisibility(View.GONE)));

        disposables.add(viewModel.getUsersObservable().observeOn(AndroidSchedulers.mainThread())
                .subscribe(characters -> characterAdapter.updateData(characters)));

        viewModel.init();
    }

    private void bindViews() {
        recyclerView = findViewById(R.id.recylerview);
        progressBar = findViewById(R.id.loading);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        characterAdapter = new CharacterAdapter(this);
        recyclerView.setAdapter(characterAdapter);

        progressBar.setVisibility(View.VISIBLE);

    }


    @Override
    public void onEndReached() {
        viewModel.getMore();
    }
}
