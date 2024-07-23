package com.example.laptopstore.controller

import android.content.Context
import android.widget.Toast
import com.example.laptopstore.model.Account
import com.example.laptopstore.model.DatabaseHelper

class RegisterController(context: Context) {

    private val dbHelper = DatabaseHelper(context)

    fun register(username: String, password: String, role: String): Boolean {
        // Kiểm tra xem tài khoản đã tồn tại chưa
        if (dbHelper.getAccount(username) != null) {
            return false
        }

        val account = Account(username, password, role)
        val result = dbHelper.insertAccount(account)

        // Trả về true nếu chèn thành công, ngược lại false
        return result > 0
    }
}
