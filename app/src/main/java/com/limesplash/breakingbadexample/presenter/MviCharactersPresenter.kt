package com.limesplash.breakingbadexample.presenter

import com.hannesdorfmann.mosby3.mvi.MviBasePresenter
import com.limesplash.breakingbadexample.model.Character
import com.limesplash.breakingbadexample.model.mvi.CharactersViewState
import com.limesplash.breakingbadexample.view.MviBBCharactersView
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class MviCharactersPresenter: MviBasePresenter<MviBBCharactersView, CharactersViewState>() {


    private var characters: List<Character>? = null

    override fun bindIntents() {
        // Default loading of page
        // TODO
        val loadCharacters: Observable<List<Character>>  = intent(MviBBCharactersView::emitFirstTimeLoadEvent)
            .subscribeOn(Schedulers.io())
            .debounce(200, TimeUnit.MILLISECONDS)
            .flatMap { GetCharactersUseCase.getBreakingBadCharacters() }
            .doOnNext { characters = it }//remember for search
            .observeOn(AndroidSchedulers.mainThread())

//        val initializeState = CharactersViewState(true)
        val selectCharacter: Observable<Character> = intent(MviBBCharactersView::emitSelectCharacterEvent)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

        val search :Observable<List<Character>> = intent(MviBBCharactersView::emitSearchedCharaterName)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { filterCharacters(it) }

        val stateObservable = Observable.merge(
            loadCharacters.map { CharactersViewState(false,it) },
            search.map { CharactersViewState(false,it) },
            selectCharacter.map { CharactersViewState(false,null, it) }
        ).startWith(CharactersViewState(true))

        subscribeViewState(
            stateObservable,
            MviBBCharactersView::updateViewState
        )

    }

    private fun filterCharacters(name: String):  List<Character>? =
        if(name.isNotEmpty()) {
            characters?.filter { it.name.contains(name,true) }
        } else
            characters


}