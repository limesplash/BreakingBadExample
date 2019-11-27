package com.limesplash.breakingbadexample.presenter

import com.limesplash.breakingbadexample.api.BreakingBadService
import com.limesplash.breakingbadexample.api.BreakingBadAPI
import com.limesplash.breakingbadexample.model.Character
import io.reactivex.Observable

object GetCharactersUseCase {
    fun getBreakingBadCharacters() : Observable<List<Character>> =
        BreakingBadService.retrofit().create(BreakingBadAPI::class.java).getBBChatacters()

}