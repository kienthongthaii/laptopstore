package com.example.laptopstore.model

data class Account(
    val username: String,
    val password: String,
    val role: String
) {
    companion object {
        const val TABLE_NAME = "account"
        const val COLUMN_ID = "id"
        const val COLUMN_USERNAME = "username"
        const val COLUMN_PASSWORD = "password"
        const val COLUMN_ROLE = "role"

        const val CREATE_TABLE =
            "CREATE TABLE $TABLE_NAME (" +
                    "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "$COLUMN_USERNAME TEXT NOT NULL," +
                    "$COLUMN_PASSWORD TEXT NOT NULL," +
                    "$COLUMN_ROLE TEXT NOT NULL)"
    }
}
