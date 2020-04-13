package com.example.testapp;

import com.example.testapp.model.Character;
import com.example.testapp.repository.CharacterStore;
import com.example.testapp.repository.LocalStore;
import com.example.testapp.repository.RemoteStore;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.observers.TestObserver;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Before
    public void setup() {
        // setup actions to happen
        RxJavaPlugins.setIoSchedulerHandler(scheduler -> Schedulers.trampoline());
        RxAndroidPlugins.setMainThreadSchedulerHandler(scheduler -> Schedulers.trampoline());

    }

    @Test
    public void getAllCharacters() {
        LocalStore localDataStore = new LocalStore();
        RemoteStore remoteDataStore = new RemoteStore(new TestNetworkService());
        CharacterStore characterDataStore = new CharacterStore(remoteDataStore, localDataStore);
        CharacterViewModel mainViewModel = new CharacterViewModel(characterDataStore);

        TestObserver<List<Character>> testCharactersObserver = new TestObserver<>();
        mainViewModel.getUsersObservable().subscribe(testCharactersObserver);

        mainViewModel.init();

        testCharactersObserver.assertNoErrors();
        testCharactersObserver.assertValue(characters -> characters.size() == 2);
    }
}