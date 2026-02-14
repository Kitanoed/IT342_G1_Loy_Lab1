package com.example.mobile

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.mobile.api.RetrofitClient
import com.example.mobile.model.RegisterRequest
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity() {

    private lateinit var etFirstName: TextInputEditText
    private lateinit var etLastName: TextInputEditText
    private lateinit var etUsername: TextInputEditText
    private lateinit var etEmail: TextInputEditText
    private lateinit var etPassword: TextInputEditText
    private lateinit var btnRegister: MaterialButton
    private lateinit var progressBar: ProgressBar
    private lateinit var tvError: TextView
    private lateinit var tvLogin: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        etFirstName = findViewById(R.id.etFirstName)
        etLastName = findViewById(R.id.etLastName)
        etUsername = findViewById(R.id.etUsername)
        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        btnRegister = findViewById(R.id.btnRegister)
        progressBar = findViewById(R.id.progressBar)
        tvError = findViewById(R.id.tvError)
        tvLogin = findViewById(R.id.tvLogin)

        btnRegister.setOnClickListener { performRegister() }

        tvLogin.setOnClickListener {
            finish() // Go back to Login
        }
    }

    private fun performRegister() {
        val firstName = etFirstName.text.toString().trim()
        val lastName = etLastName.text.toString().trim()
        val username = etUsername.text.toString().trim()
        val email = etEmail.text.toString().trim()
        val password = etPassword.text.toString().trim()

        // Validation
        if (username.isEmpty()) {
            showError("Username is required")
            return
        }
        if (email.isEmpty()) {
            showError("Email is required")
            return
        }
        if (password.isEmpty()) {
            showError("Password is required")
            return
        }
        if (password.length < 6) {
            showError("Password must be at least 6 characters")
            return
        }

        setLoading(true)

        lifecycleScope.launch {
            try {
                val request = RegisterRequest(
                    email = email,
                    username = username,
                    password = password,
                    firstName = firstName.ifEmpty { null },
                    lastName = lastName.ifEmpty { null }
                )

                val response = RetrofitClient.authApi.register(request)

                if (response.isSuccessful && response.body() != null) {
                    val authResponse = response.body()!!
                    // Registration successful - go back to login
                    val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(intent)
                    finish()
                } else {
                    // Parse error - likely duplicate email/username
                    showError("Registration failed. Email or username may already be taken.")
                }
            } catch (e: Exception) {
                showError("Connection error. Make sure the server is running.")
            } finally {
                setLoading(false)
            }
        }
    }

    private fun showError(message: String) {
        tvError.text = message
        tvError.visibility = View.VISIBLE
    }

    private fun setLoading(loading: Boolean) {
        progressBar.visibility = if (loading) View.VISIBLE else View.GONE
        btnRegister.isEnabled = !loading
        tvError.visibility = View.GONE
    }
}
