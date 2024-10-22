package com.example.cafemenuapp

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore

class LoginActivity : AppCompatActivity(), View.OnClickListener {
    private var register: TextView? = null
    private var forgotPassword: TextView? = null
    private var editEmail: EditText? = null
    private var editPassword: EditText? = null
    private var progressBar: ProgressBar? = null
    private var signIn: Button? = null
    private var mAuth: FirebaseAuth? = null
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        this.title = "Login"
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mAuth = FirebaseAuth.getInstance()

        register = findViewById(R.id.Register)
        register!!.setOnClickListener(this)
        forgotPassword = findViewById<View>(R.id.ForgotPassword) as TextView
        forgotPassword!!.setOnClickListener(this)
        signIn = findViewById<View>(R.id.login_button) as Button
        signIn!!.setOnClickListener(this)
        editEmail = findViewById<View>(R.id.email_text) as EditText
        editPassword = findViewById<View>(R.id.password_text) as EditText
        progressBar = findViewById<View>(R.id.progressBar) as ProgressBar // Initialize progressBar
        progressBar?.visibility = View.GONE
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.Register -> startActivity(Intent(this, RegisterActivity::class.java))
            R.id.login_button -> {
                progressBar?.visibility = View.VISIBLE
                inputData()
                userLogin()
            }
            R.id.ForgotPassword -> startActivity(Intent(this, ForgotPasswordActivity::class.java))
        }
    }

    fun inputData() {
        // Database Helper
        val database = DatabaseHelper(applicationContext)
        val data: ArrayList<Food> = FoodData.getData(applicationContext)
        for (food in data) {
            database.addData(food)
        }
    }

    private fun userLogin() {
        val email = editEmail!!.text.toString().trim { it <= ' ' }
        val password = editPassword!!.text.toString().trim { it <= ' ' }

        if (email.isEmpty()) {
            editEmail!!.error = "Email is required!"
            editEmail!!.requestFocus()
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editEmail!!.error = "Please enter a valid email!"
            editEmail!!.requestFocus()
            return
        }

        if (password.isEmpty()) {
            editPassword!!.error = "Password is required!"
            editPassword!!.requestFocus()
            return
        }

        if (password.length < 6) {
            editPassword!!.error = "Password length should be at least 6 characters!"
            editPassword!!.requestFocus()
            return
        }

        mAuth?.signInWithEmailAndPassword(email, password)
            ?.addOnCompleteListener(this, object : OnCompleteListener<AuthResult?> {
                override fun onComplete(task: Task<AuthResult?>) {
                    if (task.isSuccessful) {
                        val user: FirebaseUser? = mAuth!!.currentUser
                        val userId: String = user?.uid ?: ""
                        if (user != null) {
                            if (user.isEmailVerified) {
                                db.collection("users").document(mAuth!!.uid!!).get()
                                    .addOnSuccessListener { document ->
                                        if (document?.get("roleType") == "customer") {
                                            Toast.makeText(
                                                this@LoginActivity,
                                                "Login success",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                            // Redirect to Home
                                            // progressBar?.visibility = View.GONE
                                            startActivity(
                                                Intent(
                                                    this@LoginActivity,
                                                    HomeActivity::class.java
                                                )
                                            )
                                        } else if (document?.get("roleType") == "admin") {
                                            Toast.makeText(
                                                this@LoginActivity,
                                                "Welcome Admin!",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                            startActivity(
                                                Intent(
                                                    this@LoginActivity,
                                                    AdminActivity::class.java
                                                )
                                            )
                                        }
                                    }
                            } else {
                                Toast.makeText(
                                    this@LoginActivity,
                                    "Please verify your email address!",
                                    Toast.LENGTH_LONG
                                ).show()
                                // Hide the progress bar
                                progressBar?.visibility = View.GONE
                            }
                        }
                    } else {
                        // Handle login failure here
                        Toast.makeText(
                            this@LoginActivity,
                            "Login failed! Check your email and password.",
                            Toast.LENGTH_LONG
                        ).show()
                        // Hide the progress bar
                        progressBar?.visibility = View.GONE
                    }
                }
            })
    }


        }


