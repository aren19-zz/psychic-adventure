package com.example.testapp;

import com.example.testapp.network.CharacterApi;
import com.example.testapp.network.NetworkService;

public class TestNetworkService extends NetworkService {

    @Override
    public CharacterApi getCharacterApi() {
        return new TestNetworkApi();
    }
}
