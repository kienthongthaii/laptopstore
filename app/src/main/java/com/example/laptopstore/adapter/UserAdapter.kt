package com.example.laptopstore.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AlertDialog
import com.example.laptopstore.R
import com.example.laptopstore.model.DatabaseHelper
import com.example.laptopstore.model.User
import com.example.laptopstore.view.AddUserActivity
import com.example.laptopstore.view.NguoiDungActivity

class UserAdapter(
    context: Context,
    private var users: List<User>,
    private val editUserLauncher: ActivityResultLauncher<Intent>
) : ArrayAdapter<User>(context, R.layout.item_user, users) {

    private val databaseHelper = DatabaseHelper(context)

    fun updateData(newUsers: List<User>) {
        this.users = newUsers
        notifyDataSetChanged()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_user, parent, false)
        val user = getItem(position)!!

        // Lưu ID người dùng trong thuộc tính tag của view
        view.tag = user.id

        val nameTextView: TextView = view.findViewById(R.id.user_name)
        val emailTextView: TextView = view.findViewById(R.id.user_email)
        val editIcon: ImageView = view.findViewById(R.id.edit_icon)
        val deleteIcon: ImageView = view.findViewById(R.id.delete_icon)

        // Assuming User has fullName and username properties
        nameTextView.text = user.fullName ?: user.username
        emailTextView.text = user.username // Adjust according to your need

        editIcon.setOnClickListener {
            val intent = Intent(context, AddUserActivity::class.java).apply {
                putExtra("USER_ID", user.id) // Pass user ID to the intent
            }
            editUserLauncher.launch(intent)
        }

        deleteIcon.setOnClickListener {
            AlertDialog.Builder(context)
                .setTitle("Xác nhận xóa")
                .setMessage("Bạn có chắc chắn muốn xóa người dùng này không?")
                .setPositiveButton("Có") { _, _ ->
                    // Lấy ID người dùng từ tag của view
                    val userId = view.tag as? Int
                    if (userId != null) {
                        databaseHelper.deleteUser(userId)
                        (context as NguoiDungActivity).reloadUsers() // Reload users after deletion
                        Toast.makeText(context, "Người dùng đã bị xóa", Toast.LENGTH_SHORT).show()
                    }
                }
                .setNegativeButton("Không", null)
                .show()
        }

        return view
    }
}
