package com.example.laptopstore.view

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ListView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.laptopstore.R
import com.example.laptopstore.adapter.UserAdapter
import com.example.laptopstore.model.DatabaseHelper
import com.example.laptopstore.model.User
import com.example.laptopstore.view.AddUserActivity

class NguoiDungActivity : AppCompatActivity() {

    private lateinit var searchBar: EditText
    private lateinit var filterButton: ImageButton
    private lateinit var addUserButton: Button
    private lateinit var userListView: ListView
    private lateinit var backButton: ImageButton

    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var userAdapter: UserAdapter

    private val editUserLauncher: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == RESULT_OK) {
                reloadUsers()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nguoi_dung)

        searchBar = findViewById(R.id.search_bar)
        filterButton = findViewById(R.id.filter_button)
        addUserButton = findViewById(R.id.add_user_button)
        userListView = findViewById(R.id.user_list_view)
        backButton = findViewById(R.id.back_button)

        databaseHelper = DatabaseHelper(this)
        reloadUsers()

        addUserButton.setOnClickListener {
            val intent = Intent(this, AddUserActivity::class.java)
            editUserLauncher.launch(intent)
        }

        filterButton.setOnClickListener {
            // Handle filter button click
        }

        searchBar.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                filterUsers(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        backButton.setOnClickListener {
            finish()
        }
    }

    fun reloadUsers() {
        val users = databaseHelper.getAllUsers()
        userAdapter = UserAdapter(this, users, editUserLauncher)
        userListView.adapter = userAdapter
    }
    private fun filterUsers(query: String) {
        val filteredUsers = databaseHelper.getAllUsers().filter { user ->
            user.username?.contains(query, ignoreCase = true) == true ||
                    user.fullName?.contains(query, ignoreCase = true) == true ||
                    user.dob?.contains(query, ignoreCase = true) == true ||
                    user.gender?.contains(query, ignoreCase = true) == true ||
                    user.address?.contains(query, ignoreCase = true) == true ||
                    user.phoneNumber?.contains(query, ignoreCase = true) == true ||
                    user.role?.contains(query, ignoreCase = true) == true
        }
        userAdapter.updateData(filteredUsers)
    }

}
