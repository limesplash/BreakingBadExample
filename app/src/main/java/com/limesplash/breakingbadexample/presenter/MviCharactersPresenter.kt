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

    override fun bindIntents() {
        // Default loading of page
        // TODO
        val loadCharacters: Observable<List<Character>>  = intent(MviBBCharactersView::emitFirstTimeLoadEvent)
            .subscribeOn(Schedulers.io())
            .debounce(200, TimeUnit.MILLISECONDS)
            .flatMap { GetCharactersUseCase.getBreakingBadCharacters() }
            .observeOn(AndroidSchedulers.mainThread())

//        val initializeState = CharactersViewState(true)
        val selectCharacter: Observable<Character> = intent(MviBBCharactersView::emitSelectCharacterEvent)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

        val stateObservable = Observable.merge(
            loadCharacters.map { CharactersViewState(false,it) },
            selectCharacter.map { CharactersViewState(false,null, it) }
        ).startWith(CharactersViewState(true))

        subscribeViewState(
            stateObservable,
            MviBBCharactersView::updateViewState
        )

    }

}