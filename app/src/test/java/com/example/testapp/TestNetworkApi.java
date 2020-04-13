package com.example.testapp;

import com.example.testapp.model.Character;
import com.example.testapp.model.CharacterResults;
import com.example.testapp.model.Info;
import com.example.testapp.network.CharacterApi;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

public class TestNetworkApi implements CharacterApi {
    @Override
    public Observable<CharacterResults> getCharacters(int page) {
        List<Character> characterList = new ArrayList<>();
        Character c1 = new Character("a", "b");
        Character c2 = new Character("b", "b");
        characterList.add(c1);
        characterList.add(c2);
        Info info = new Info(1, null, null);
        CharacterResults characterResults = new CharacterResults(characterList, info);
        return Observable.just(characterResults);
    }
}
