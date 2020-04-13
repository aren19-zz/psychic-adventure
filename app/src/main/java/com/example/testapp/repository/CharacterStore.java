package com.example.testapp.repository;

import androidx.core.util.Pair;

import com.example.testapp.model.Character;

import java.util.List;

import io.reactivex.Observable;

public class CharacterStore {

    private final RemoteStore remoteStore;
    private final LocalStore localStore;

    public CharacterStore(RemoteStore remoteStore, LocalStore localStore) {
        this.localStore = localStore;
        this.remoteStore = remoteStore;
    }

    public Observable<Pair<Boolean, List<Character>>> getCharacters() {
        return remoteStore.getCharacters();
    }
}
