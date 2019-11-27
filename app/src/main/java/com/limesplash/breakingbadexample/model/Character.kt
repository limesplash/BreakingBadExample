package com.limesplash.breakingbadexample.model

import com.google.gson.annotations.SerializedName

data class Character(val name: String,
                     @SerializedName("img") val imageURL: String,
                     val nickname: String?,
                     val occupation: List<String>?,
                     val status: String,
                     val appearance:List<Int>){
}