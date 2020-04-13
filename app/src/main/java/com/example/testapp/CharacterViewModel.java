package com.example.testapp;

import com.example.testapp.model.Character;
import com.example.testapp.repository.CharacterStore;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

public class CharacterViewModel {

    private CharacterStore characterStore;
    private CompositeDisposable compositeDisposable;
    private PublishSubject<List<Character>> characterPublishSubject;
    private PublishSubject<Boolean> endReachedPublishSubject;
    private PublishSubject<Boolean> responseReceivedPublishSubject;
    private List<Character> characterList;
    private boolean endReached;


    public CharacterViewModel(CharacterStore characterStore) {
        this.characterStore = characterStore;

        compositeDisposable = new CompositeDisposable();
        characterPublishSubject = PublishSubject.create();
        endReachedPublishSubject = PublishSubject.create();
        responseReceivedPublishSubject = PublishSubject.create();
        characterList = new ArrayList<>();
    }

    public Observable<List<Character>> getUsersObservable() {
        return characterPublishSubject.hide();
    }

    public Observable<Boolean> getEndReachedObservable() {
        return endReachedPublishSubject.hide();
    }

    public Observable<Boolean> getResponseReceivedObservable() {
        return responseReceivedPublishSubject.hide();
    }

    public void init() {
        compositeDisposable.add(characterStore.getCharacters()
                .subscribeOn(Schedulers.io()).subscribe(booleanListPair -> {
                    responseReceivedPublishSubject.onNext(true);
                    if (booleanListPair.first) {
                        endReached = true;
                        endReachedPublishSubject.onNext(true);
                    }
                    characterList.addAll(booleanListPair.second);
                    characterPublishSubject.onNext(characterList);
                }, throwable -> responseReceivedPublishSubject.onNext(true)));
    }

    public void getMore() {
        if (!endReached) {
            compositeDisposable.add(characterStore.getCharacters()
                    .subscribeOn(Schedulers.io()).subscribe(booleanListPair -> {
                        if (booleanListPair.first) {
                            endReached = true;
                            endReachedPublishSubject.onNext(true);
                        }
                        characterList.addAll(booleanListPair.second);
                        characterPublishSubject.onNext(characterList);
                    }, throwable -> getMore()));
        }
    }

    public void onDestroy() {
        compositeDisposable.clear();
    }
}
