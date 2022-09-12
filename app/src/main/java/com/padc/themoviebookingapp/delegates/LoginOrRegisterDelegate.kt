package com.padc.themoviebookingapp.delegates

interface LoginOrRegisterDelegate {
    fun loginOrRegister(
        name: String,
        email: String,
        phone: String,
        password: String,
        state: String
    )
}