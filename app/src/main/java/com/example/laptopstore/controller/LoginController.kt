package com.example.laptopstore.controller

import android.content.Context
import com.example.laptopstore.model.Account
import com.example.laptopstore.model.DatabaseHelper

class LoginController(context: Context) {

    private val dbHelper = DatabaseHelper(context)

    fun login(username: String, password: String): Account? {
        return dbHelper.getAccountFull(username, password)
    }
}
