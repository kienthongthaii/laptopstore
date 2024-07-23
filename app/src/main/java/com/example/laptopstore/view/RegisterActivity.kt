package com.example.laptopstore.view

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.laptopstore.R
import com.example.laptopstore.controller.RegisterController
import com.example.laptopstore.utils.Validator

class RegisterActivity : AppCompatActivity() {

    private lateinit var registerController: RegisterController
    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var registerButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        registerController = RegisterController(this)

        // Initialize views
        usernameEditText = findViewById(R.id.username_edit_text)
        passwordEditText = findViewById(R.id.password_edit_text)
        registerButton = findViewById(R.id.register_button)

        registerButton.setOnClickListener {
            val username = usernameEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            // Kiểm tra username và password
            if (!Validator.isValidUsername(username)) {
                Toast.makeText(this, "Tên người dùng không hợp lệ", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password.length < 6) {
                Toast.makeText(this, "Mật khẩu phải có ít nhất 6 ký tự", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Thực hiện đăng ký
            if (registerController.register(username, password, "khachhang")) {
                Toast.makeText(this, "Đăng ký thành công", Toast.LENGTH_SHORT).show()
                finish() // Đóng Activity sau khi đăng ký thành công
            } else {
                Toast.makeText(this, "Tên người dùng đã tồn tại", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
