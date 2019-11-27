package com.limesplash.breakingbadexample.model.mvi

import com.limesplash.breakingbadexample.model.Character

data class CharactersViewState(
    val isLoading: Boolean = false,
    val characters: List<Character>? = emptyList(),
    val selectedCharacter: Character? = null
)