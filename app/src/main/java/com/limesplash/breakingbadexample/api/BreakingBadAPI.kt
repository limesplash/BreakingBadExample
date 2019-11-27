package com.limesplash.breakingbadexample.api

import com.limesplash.breakingbadexample.model.Character
import io.reactivex.Observable
import retrofit2.http.GET


interface BreakingBadAPI {

    @GET("/api/characters")
    fun getBBChatacters(): Observable<List<Character>>

}