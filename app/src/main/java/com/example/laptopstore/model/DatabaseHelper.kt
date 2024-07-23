package com.example.laptopstore.model

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        // Tạo bảng Account, User và Product
        db.execSQL(Account.CREATE_TABLE)
        db.execSQL(User.CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Xóa bảng cũ nếu tồn tại và tạo lại các bảng
        db.execSQL("DROP TABLE IF EXISTS ${Account.TABLE_NAME}")
        db.execSQL("DROP TABLE IF EXISTS ${User.TABLE_NAME}")
        onCreate(db)
    }

    // User methods
    fun insertOrUpdateUser(user: User): Boolean {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(User.COLUMN_USERNAME, user.username)
            put(User.COLUMN_FULL_NAME, user.fullName)
            put(User.COLUMN_DOB, user.dob)
            put(User.COLUMN_GENDER, user.gender)
            put(User.COLUMN_ADDRESS, user.address)
            put(User.COLUMN_PHONE_NUMBER, user.phoneNumber)
            put(User.COLUMN_ROLE, user.role)
        }

        val rowsAffected = db.update(
            User.TABLE_NAME,
            values,
            "${User.COLUMN_USERNAME} = ?",
            arrayOf(user.username)
        )

        return if (rowsAffected > 0) {
            // Cập nhật thành công
            true
        } else {
            // Không có hàng nào được cập nhật, thử chèn
            val insertResult = db.insert(User.TABLE_NAME, null, values)
            insertResult != -1L
        }
    }
    fun getUserID(username: String): Int? {
        val db = readableDatabase
        var userID: Int? = null

        val cursor = db.query(
            User.TABLE_NAME,
            arrayOf(User.COLUMN_ID), // Sử dụng COLUMN_ID
            "${User.COLUMN_USERNAME} = ?",
            arrayOf(username),
            null,
            null,
            null
        )

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                // Lấy ID người dùng từ con trỏ
                userID = cursor.getInt(cursor.getColumnIndexOrThrow(User.COLUMN_ID))
            }
            cursor.close()
        }

        return userID
    }





    // Account methods
    fun insertAccount(account: Account): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(Account.COLUMN_USERNAME, account.username)
            put(Account.COLUMN_PASSWORD, account.password)
            put(Account.COLUMN_ROLE, account.role)
        }
        return db.insert(Account.TABLE_NAME, null, values)
    }

    fun getAccount(username: String): Account? {
        val db = readableDatabase
        val cursor = db.query(
            Account.TABLE_NAME,
            arrayOf(Account.COLUMN_USERNAME, Account.COLUMN_PASSWORD, Account.COLUMN_ROLE),
            "${Account.COLUMN_USERNAME} = ?",
            arrayOf(username),
            null,
            null,
            null
        )

        val account: Account? = if (cursor.moveToFirst()) {
            Account(
                cursor.getString(cursor.getColumnIndexOrThrow(Account.COLUMN_USERNAME)),
                cursor.getString(cursor.getColumnIndexOrThrow(Account.COLUMN_PASSWORD)),
                cursor.getString(cursor.getColumnIndexOrThrow(Account.COLUMN_ROLE))
            )
        } else {
            null
        }

        cursor.close()
        return account
    }

    fun getAccountFull(username: String, password: String): Account? {
        val db = readableDatabase
        var cursor: Cursor? = null
        var account: Account? = null

        try {
            cursor = db.query(
                Account.TABLE_NAME,
                arrayOf(Account.COLUMN_USERNAME, Account.COLUMN_PASSWORD, Account.COLUMN_ROLE),
                "${Account.COLUMN_USERNAME} = ? AND ${Account.COLUMN_PASSWORD} = ?", // Điều kiện đầy đủ
                arrayOf(username, password),
                null,
                null,
                null
            )

            if (cursor.moveToFirst()) {
                account = Account(
                    cursor.getString(cursor.getColumnIndexOrThrow(Account.COLUMN_USERNAME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(Account.COLUMN_PASSWORD)),
                    cursor.getString(cursor.getColumnIndexOrThrow(Account.COLUMN_ROLE))
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            cursor?.close()
        }

        return account
    }
    // Trong DatabaseHelper
    fun getAllUsers(): List<User> {
        val users = mutableListOf<User>()
        val db = readableDatabase
        val cursor = db.query(
            User.TABLE_NAME,
            null, // Chọn tất cả các cột
            null, // Không có điều kiện
            null, // Không có giá trị điều kiện
            null, // Không nhóm theo
            null, // Không lọc
            null // Sắp xếp theo cột mặc định
        )

        while (cursor.moveToNext()) {
            val user = User(

                cursor.getInt(cursor.getColumnIndexOrThrow(User.COLUMN_ID)),
                cursor.getString(cursor.getColumnIndexOrThrow(User.COLUMN_USERNAME)),
                cursor.getString(cursor.getColumnIndexOrThrow(User.COLUMN_FULL_NAME)),
                cursor.getString(cursor.getColumnIndexOrThrow(User.COLUMN_DOB)),
                cursor.getString(cursor.getColumnIndexOrThrow(User.COLUMN_GENDER)),
                cursor.getString(cursor.getColumnIndexOrThrow(User.COLUMN_ADDRESS)),
                cursor.getString(cursor.getColumnIndexOrThrow(User.COLUMN_PHONE_NUMBER)),
                cursor.getString(cursor.getColumnIndexOrThrow(User.COLUMN_ROLE))
            )
            users.add(user)
        }

        cursor.close()
        return users
    }
    fun deleteUser(id: Int) {
        val db = this.writableDatabase
        db.delete("users", "id = ?", arrayOf(id.toString()))
        db.close()
    }

    fun updateUser(user: User) {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(User.COLUMN_USERNAME, user.username)
            put(User.COLUMN_FULL_NAME, user.fullName)
            put(User.COLUMN_DOB, user.dob)
            put(User.COLUMN_GENDER, user.gender)
            put(User.COLUMN_ADDRESS, user.address)
            put(User.COLUMN_PHONE_NUMBER, user.phoneNumber)
            put(User.COLUMN_ROLE, user.role)
        }
        val rowsAffected = db.update(User.TABLE_NAME, values, "${User.COLUMN_ID} = ?", arrayOf(user.id.toString()))
        if (rowsAffected == 0) {
            // Handle the case where no rows were updated
        }
        db.close()
    }
    // Add this method
    fun addUser(username: String, fullName: String, dob: String, gender: String, address: String, phoneNumber: String, role: String): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues().apply {
            put(User.COLUMN_USERNAME, username)
            put(User.COLUMN_FULL_NAME, fullName)
            put(User.COLUMN_DOB, dob)
            put(User.COLUMN_GENDER, gender)
            put(User.COLUMN_ADDRESS, address)
            put(User.COLUMN_PHONE_NUMBER, phoneNumber)
            put(User.COLUMN_ROLE, role)
        }

        // Insert the new user into the database
        return db.insert(User.TABLE_NAME, null, contentValues)
    }


    fun getUser(id: Int): User? {
        val db = this.readableDatabase
        val cursor = db.query(
            User.TABLE_NAME,
            arrayOf(
                User.COLUMN_ID,
                User.COLUMN_USERNAME,
                User.COLUMN_FULL_NAME,
                User.COLUMN_DOB,
                User.COLUMN_GENDER,
                User.COLUMN_ADDRESS,
                User.COLUMN_PHONE_NUMBER,
                User.COLUMN_ROLE
            ),
            "${User.COLUMN_ID} = ?",
            arrayOf(id.toString()),
            null, null, null
        )

        cursor?.use {
            if (it.moveToFirst()) {
                val idIndex = it.getColumnIndex(User.COLUMN_ID)
                val usernameIndex = it.getColumnIndex(User.COLUMN_USERNAME)
                val fullNameIndex = it.getColumnIndex(User.COLUMN_FULL_NAME)
                val dobIndex = it.getColumnIndex(User.COLUMN_DOB)
                val genderIndex = it.getColumnIndex(User.COLUMN_GENDER)
                val addressIndex = it.getColumnIndex(User.COLUMN_ADDRESS)
                val phoneNumberIndex = it.getColumnIndex(User.COLUMN_PHONE_NUMBER)
                val roleIndex = it.getColumnIndex(User.COLUMN_ROLE)

                if (idIndex == -1 || usernameIndex == -1 || fullNameIndex == -1 ||
                    dobIndex == -1 || genderIndex == -1 || addressIndex == -1 ||
                    phoneNumberIndex == -1 || roleIndex == -1) {
                    // Handle the case where the column indices are not found
                    return null
                }

                return User(
                    id = it.getInt(idIndex),
                    username = it.getString(usernameIndex),
                    fullName = it.getString(fullNameIndex),
                    dob = it.getString(dobIndex),
                    gender = it.getString(genderIndex),
                    address = it.getString(addressIndex),
                    phoneNumber = it.getString(phoneNumberIndex),
                    role = it.getString(roleIndex)
                )
            }
        }
        return null
    }






    companion object {
        private const val DATABASE_NAME = "laptop_store.db"
        private const val DATABASE_VERSION = 5
    }
}
