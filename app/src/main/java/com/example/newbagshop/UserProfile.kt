package com.example.newbagshop

class  UserProfile {
    lateinit var userName: String
    lateinit var userEmail: String
    lateinit var userNumber: String

    constructor() {}
    constructor(userName: String, userEmail: String, userNumber: String)
    {
        this.userName = userName
        this.userEmail = userEmail
        this.userNumber = userNumber
    }
}