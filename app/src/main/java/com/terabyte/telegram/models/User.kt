package com.terabyte.telegram.models

data class User(
    val id: String = "",
    var username: String = "",
    var bio: String = "",
    var fullName: String = "",
    var status: String = "",
    var phone: String = "",
    var photoUrl: String = ""
)