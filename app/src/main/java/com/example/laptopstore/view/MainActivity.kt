package com.example.laptopstore.view

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.laptopstore.R
import com.google.android.material.navigation.NavigationView


class MainActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        drawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)

        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_dashboard -> {
                    // Xử lý khi chọn Dashboard
                    // Ví dụ: Chuyển đến DashboardActivity
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }
                R.id.nav_sanpham -> {
                    // Xử lý khi chọn SanPham
                    val intent = Intent(this, SanPhamActivity::class.java)
                    startActivity(intent)
                }
                R.id.nav_nguoidung -> {
                    // Xử lý khi chọn User Profile
                    // Ví dụ: Chuyển đến UserProfileActivity
                    val intent = Intent(this, NguoiDungActivity::class.java)
                    startActivity(intent)
                }
                R.id.nav_taikhoan -> {
                    // Xử lý khi chọn Add New Product
                    // Ví dụ: Chuyển đến AddProductActivity
                    val intent = Intent(this, TaiKhoanActivity::class.java)
                    startActivity(intent)
                }
                R.id.nav_thongke -> {
                    // Xử lý khi chọn Statistics
                    // Ví dụ: Chuyển đến StatisticsActivity
                    val intent = Intent(this, ThongKeActivity::class.java)
                    startActivity(intent)
                }
                R.id.nav_matkhau -> {
                    // Xử lý khi chọn Change Password
                    // Ví dụ: Chuyển đến ChangePasswordActivity
                    val intent = Intent(this, DoiMatKhauActivity::class.java)
                    startActivity(intent)
                }
                R.id.nav_logout -> {
                    AlertDialog.Builder(this)
                        .setTitle("Đăng xuất")
                        .setMessage("Bạn có chắc chắn muốn đăng xuất không?")
                        .setPositiveButton("Có") { dialog, which ->
                            // Chuyển đến LoginActivity và xóa tất cả các activity trên stack
                            val intent = Intent(applicationContext, LoginActivity::class.java)
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(intent)
                            finish()
                        }
                        .setNegativeButton("Không", null)
                        .show()
                }
            }
            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }

    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}