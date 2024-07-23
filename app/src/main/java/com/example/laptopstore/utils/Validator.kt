// utils/Validator.kt
package com.example.laptopstore.utils

import android.util.Patterns

object Validator {

    fun isValidEmail(account: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(account).matches()
    }

    fun isValidUsername(username: String): Boolean {
        // Kiểm tra username có hợp lệ không, ví dụ:
        // - Độ dài tối thiểu là 3 ký tự
        // - Không chứa ký tự đặc biệt
        return username.length >= 3 && username.matches("^[a-zA-Z0-9_]+$".toRegex())
    }
}
