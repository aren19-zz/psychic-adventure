package com.example.testapp;

import com.example.testapp.network.NetworkService;
import com.example.testapp.repository.CharacterStore;
import com.example.testapp.repository.LocalStore;
import com.example.testapp.repository.RemoteStore;

public class Module {

    static Module module;

    // should be singleton

    public CharacterStore getCharacterStore() {
        return characterStore;
    }

    private CharacterStore characterStore;

    private Module() {
        NetworkService networkService = new NetworkService();
        LocalStore localStore = new LocalStore();
        RemoteStore remoteStore = new RemoteStore(networkService);
        characterStore = new CharacterStore(remoteStore, localStore);
    }

    public synchronized static Module getInstance() {
        if (module == null) {
            module = new Module();
        }
        return module;
    }

}
