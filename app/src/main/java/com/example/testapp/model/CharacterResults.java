package com.example.testapp.model;

import java.util.List;

public class CharacterResults {
    public Info getInfo() {
        return info;
    }

    public CharacterResults(List<Character> results, Info info) {
        this.results = results;
        this.info = info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }

    List<Character> results;

    Info info;

    public List<Character> getResults() {
        return results;
    }

    public void setResults(List<Character> results) {
        this.results = results;
    }
}
