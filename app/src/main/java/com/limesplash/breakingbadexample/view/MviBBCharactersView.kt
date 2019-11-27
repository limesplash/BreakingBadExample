package com.limesplash.breakingbadexample.view

import com.hannesdorfmann.mosby3.mvp.MvpView
import com.limesplash.breakingbadexample.model.Character
import com.limesplash.breakingbadexample.model.mvi.CharactersViewState
import io.reactivex.Observable

interface MviBBCharactersView:MvpView {
    fun emitFirstTimeLoadEvent(): Observable<Boolean>
    fun emitSelectCharacterEvent(): Observable<Character>
    fun updateViewState(charactersViewState: CharactersViewState)
//    fun displayCharacterDetails(character: Character)

}