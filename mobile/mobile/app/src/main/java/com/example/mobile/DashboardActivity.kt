package com.example.mobile

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.mobile.api.RetrofitClient
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.launch

class DashboardActivity : AppCompatActivity() {

    private lateinit var tvName: TextView
    private lateinit var tvUsername: TextView
    private lateinit var tvEmail: TextView
    private lateinit var tvMemberSince: TextView
    private lateinit var btnLogout: MaterialButton
    private lateinit var progressBar: ProgressBar
    private lateinit var tvError: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        tvName = findViewById(R.id.tvName)
        tvUsername = findViewById(R.id.tvUsername)
        tvEmail = findViewById(R.id.tvEmail)
        tvMemberSince = findViewById(R.id.tvMemberSince)
        btnLogout = findViewById(R.id.btnLogout)
        progressBar = findViewById(R.id.progressBar)
        tvError = findViewById(R.id.tvError)

        btnLogout.setOnClickListener { performLogout() }

        loadUserProfile()
    }

    private fun loadUserProfile() {
        setLoading(true)

        lifecycleScope.launch {
            try {
                val response = RetrofitClient.authApi.getCurrentUser()

                if (response.isSuccessful && response.body() != null) {
                    val user = response.body()!!
                    val fullName = listOfNotNull(user.firstName, user.lastName)
                        .joinToString(" ")
                        .ifEmpty { "N/A" }

                    tvName.text = "Name: $fullName"
                    tvUsername.text = "Username: ${user.username}"
                    tvEmail.text = "Email: ${user.email}"
                    tvMemberSince.text = "Member since: ${user.createdAt?.take(10) ?: "N/A"}"
                } else {
                    // Session expired or not authenticated - redirect to login
                    navigateToLogin()
                }
            } catch (e: Exception) {
                showError("Failed to load profile. Check your connection.")
            } finally {
                setLoading(false)
            }
        }
    }

    private fun performLogout() {
        setLoading(true)

        lifecycleScope.launch {
            try {
                RetrofitClient.authApi.logout()
            } catch (_: Exception) {
                // Even if server call fails, clear local session
            } finally {
                RetrofitClient.clearCookies()
                navigateToLogin()
            }
        }
    }

    private fun navigateToLogin() {
        val intent = Intent(this@DashboardActivity, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }

    private fun showError(message: String) {
        tvError.text = message
        tvError.visibility = View.VISIBLE
    }

    private fun setLoading(loading: Boolean) {
        progressBar.visibility = if (loading) View.VISIBLE else View.GONE
    }
}
