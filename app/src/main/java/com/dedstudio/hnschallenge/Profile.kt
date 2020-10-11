package com.dedstudio.hnschallenge

data class Profile(
    var userName:String? = null,
    var id:String? = null,
    var profilePhotoReference:String? = null,
    var score:Int = 0,
    var rank:Int = 0,
    var subscribers:MutableList<String> = mutableListOf(),
    var subscription:MutableList<String> = mutableListOf()
)