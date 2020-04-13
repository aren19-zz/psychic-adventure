package com.example.testapp.repository;

import androidx.core.util.Pair;

import com.example.testapp.model.Character;
import com.example.testapp.network.NetworkService;

import java.util.List;

import io.reactivex.Observable;

public class RemoteStore {

    private final NetworkService networkService;
    private int page = 1;

    public RemoteStore(NetworkService networkService) {
        this.networkService = networkService;
    }

    Observable<Pair<Boolean, List<Character>>> getCharacters() {
        return networkService.getCharacterApi().getCharacters(page).map(characterResults -> {
            boolean isEnd = false;
            if (characterResults.getInfo().getNext() == null) {
                isEnd = true;
            } else {
                page++;
            }
            return new Pair<>(isEnd, characterResults.getResults());
        });
    }
}
