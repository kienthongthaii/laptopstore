package com.example.laptopstore.model
data class User(

    val id: Int = 0, // ID tự động tăng, giá trị mặc định là 0
    val username: String,
    val fullName: String? = null,
    val dob: String? = null,
    val gender: String? = null,
    val address: String? = null,
    val phoneNumber: String? = null,
    val role: String? = null
) {
    companion object {
        const val TABLE_NAME = "user_profile"
        const val COLUMN_ID = "id" // Thêm cột ID
        const val COLUMN_USERNAME = "username"
        const val COLUMN_FULL_NAME = "full_name"
        const val COLUMN_DOB = "dob"
        const val COLUMN_GENDER = "gender"
        const val COLUMN_ADDRESS = "address"
        const val COLUMN_PHONE_NUMBER = "phone_number"
        const val COLUMN_ROLE = "role"

        const val CREATE_TABLE = ("CREATE TABLE $TABLE_NAME (" +
                "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, " + // Cột ID tự động tăng
                "$COLUMN_USERNAME TEXT UNIQUE, " + // Thay đổi PRIMARY KEY thành UNIQUE
                "$COLUMN_FULL_NAME TEXT, " +
                "$COLUMN_DOB TEXT, " +
                "$COLUMN_GENDER TEXT, " +
                "$COLUMN_ADDRESS TEXT, " +
                "$COLUMN_PHONE_NUMBER TEXT, " +
                "$COLUMN_ROLE TEXT)")
    }
}
