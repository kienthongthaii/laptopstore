// LoginActivity.kt
package com.example.laptopstore.view

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.laptopstore.R
import com.example.laptopstore.controller.LoginController

import com.example.laptopstore.model.Account
import com.example.laptopstore.utils.Validator

class LoginActivity : AppCompatActivity() {

    private lateinit var loginController: LoginController
    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button
    private lateinit var registerTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginController = LoginController(this)

        // Initialize views
        usernameEditText = findViewById(R.id.username_edit_text)
        passwordEditText = findViewById(R.id.password_edit_text)
        loginButton = findViewById(R.id.login_button)
        registerTextView = findViewById(R.id.register_text_view)

        loginButton.setOnClickListener {
            val username = usernameEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Tên người dùng và mật khẩu không được để trống!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!Validator.isValidUsername(username)) {
                Toast.makeText(this, "Tên người dùng không hợp lệ!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            try {
                val account: Account? = loginController.login(username, password)
                if (account != null) {
                    // Điều hướng dựa trên role của tài khoản
                    val intent = when (account.role) {
                        "khachhang" -> Intent(this, MainActivity::class.java)
                        "quanly" -> Intent(this, QuanLyActivity::class.java)
                        else -> throw IllegalStateException("Role không hợp lệ")
                    }

                    startActivity(intent)

                    // Tạo thông báo Toast với vai trò của tài khoản
                    val roleMessage = when (account.role) {
                        "khachhang" -> "Khách Hàng"
                        "quanly" -> "Quản Lý"
                        else -> "vai trò không xác định"
                    }

                    Toast.makeText(this, "Đăng nhập thành công với vai trò: $roleMessage", Toast.LENGTH_SHORT).show()
                    finish() // Đóng LoginActivity sau khi chuyển sang Activity mới
                } else {
                    Toast.makeText(this, "Tên người dùng hoặc mật khẩu không đúng!", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(this, "Đã xảy ra lỗi: ${e.message}", Toast.LENGTH_SHORT).show()
            }

        }

        registerTextView.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}
