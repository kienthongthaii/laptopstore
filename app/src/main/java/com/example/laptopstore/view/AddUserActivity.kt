package com.example.laptopstore.view

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.laptopstore.R
import com.example.laptopstore.model.DatabaseHelper
import com.example.laptopstore.model.User
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import java.util.*

class AddUserActivity : AppCompatActivity() {

    private lateinit var databaseHelper: DatabaseHelper
    private var userId: Int? = null
    private lateinit var avatarImageView: ImageView
    private lateinit var fullNameEditText: TextInputEditText
    private lateinit var dobEditText: TextInputEditText
    private lateinit var genderEditText: TextInputEditText
    private lateinit var addressEditText: TextInputEditText
    private lateinit var phoneNumberEditText: TextInputEditText
    private lateinit var roleEditText: TextInputEditText
    private lateinit var saveButton: Button
    private lateinit var cancelButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_user)

        databaseHelper = DatabaseHelper(this)

        // Initialize views
        avatarImageView = findViewById(R.id.avatar_image)
        fullNameEditText = findViewById(R.id.full_name_input)
        dobEditText = findViewById(R.id.dob_input)
        genderEditText = findViewById(R.id.gender_input)
        addressEditText = findViewById(R.id.address_input)
        phoneNumberEditText = findViewById(R.id.phone_number_input)
        roleEditText = findViewById(R.id.role_input)
        saveButton = findViewById(R.id.save_button)
        cancelButton = findViewById(R.id.cancel_button)

        userId = intent.getIntExtra("USER_ID", -1).takeIf { it != -1 }

        if (userId != null) {
            // Load user details for editing
            val user = databaseHelper.getUser(userId!!)
            fullNameEditText.setText(user?.fullName)
            dobEditText.setText(user?.dob)
            genderEditText.setText(user?.gender)
            addressEditText.setText(user?.address)
            phoneNumberEditText.setText(user?.phoneNumber)
            roleEditText.setText(user?.role)
        }

        saveButton.setOnClickListener {
            val username = "abc"
            val fullName = fullNameEditText.text.toString()
            val dob = dobEditText.text.toString()
            val gender = genderEditText.text.toString()
            val address = addressEditText.text.toString()
            val phoneNumber = phoneNumberEditText.text.toString()
            val role = roleEditText.text.toString()

            // Validate required fields
            if (username.isBlank() || fullName.isBlank()) {
                Toast.makeText(this, "Tên người dùng và họ tên không được bỏ trống", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val resultIntent = Intent()

            if (userId != null) {
                // Update existing user
                val user = User(
                    id = userId!!,
                    username = username,
                    fullName = fullName,
                    dob = dob,
                    gender = gender,
                    address = address,
                    phoneNumber = phoneNumber,
                    role = role
                )
                databaseHelper.updateUser(user)
                Toast.makeText(this, "Người dùng đã được cập nhật", Toast.LENGTH_SHORT).show()
                resultIntent.putExtra("USER_UPDATED", true)
            } else {
                // Add new user
                val newUserId = databaseHelper.addUser(
                    username,
                    fullName,
                    dob,
                    gender,
                    address,
                    phoneNumber,
                    role
                )
                if (newUserId == -1L) {
                    Toast.makeText(this, "Có lỗi xảy ra khi thêm người dùng", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Người dùng đã được thêm", Toast.LENGTH_SHORT).show()
                    resultIntent.putExtra("NEW_USER_ID", newUserId)
                }
            }

            setResult(RESULT_OK, resultIntent)
            finish()
        }





        // Set onClickListener for cancel button
        cancelButton.setOnClickListener {
            finish()
        }

        // Set DatePicker for dobEditText
        dobEditText.setOnClickListener {
            showDatePickerDialog()
        }

        // Set onClickListener for avatarImageView
        avatarImageView.setOnClickListener {
            // Handle image selection here
        }
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePicker = DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
            val date = "$selectedDay/${selectedMonth + 1}/$selectedYear"
            dobEditText.setText(date)
        }, year, month, day)

        datePicker.show()
    }
}
