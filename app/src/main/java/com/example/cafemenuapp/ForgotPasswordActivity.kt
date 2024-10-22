package com.example.cafemenuapp

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth


class ForgotPasswordActivity : AppCompatActivity() {
    private var emailEditText: EditText? = null
    private var resetPasswordButton: Button? = null
    private var progressBar: ProgressBar? = null
    var auth: FirebaseAuth? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        this.title = "Forgot Password"
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)
        emailEditText = findViewById<View>(R.id.email_text) as EditText
        resetPasswordButton = findViewById<View>(R.id.reset_button) as Button
        progressBar = findViewById<View>(R.id.progressBar) as ProgressBar
        auth = FirebaseAuth.getInstance()
        resetPasswordButton!!.setOnClickListener { resetPassword() }
    }

    private fun resetPassword() {
        val email = emailEditText!!.text.toString().trim { it <= ' ' }
        if (email.isEmpty()) {
            emailEditText!!.error = "Email is required!"
            emailEditText!!.requestFocus()
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEditText!!.error = "Please provide a valid email!"
            emailEditText!!.requestFocus()
            return
        }
        auth?.sendPasswordResetEmail(email)
            ?.addOnCompleteListener { task: Task<Void?> ->
                    if (task.isSuccessful()) {
                        progressBar!!.visibility = View.VISIBLE
                        Toast.makeText(
                            this@ForgotPasswordActivity,
                            "Check your email to reset your password!",
                            Toast.LENGTH_LONG
                        ).show()
                        val intent = Intent(this@ForgotPasswordActivity, LoginActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(
                            this@ForgotPasswordActivity,
                            "Email not registered! Try again!",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
    }
