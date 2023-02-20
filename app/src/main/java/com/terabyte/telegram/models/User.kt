package com.terabyte.telegram.models

data class User(
    val id: String = "",
    var username: String = "",
    var bio: String = "",
    var fullName: String = "",
    var state: String = "",
    var phone: String = "",
    var photoUrl: String = "empty" //Picasso can't read an empty url, we need to put some
//text here.
)