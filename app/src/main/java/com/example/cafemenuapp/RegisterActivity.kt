
package com.example.cafemenuapp

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore


class RegisterActivity : AppCompatActivity(), View.OnClickListener {
    private var radioGroup: RadioGroup? = null
    private var isAdminRadioButton: RadioButton? = null
    private var isCustomerRadioButton: RadioButton? = null
    private var banner: TextView? = null
    private var Register: TextView? = null
    private var edtUsername: EditText? = null
    private var edtEmail: EditText? = null
    private var edtPassword: EditText? = null
    private var progressBar: ProgressBar? = null
    private var mAuth: FirebaseAuth? = null
    private var fStore: FirebaseFirestore? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        this.title = "Register"
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        mAuth = FirebaseAuth.getInstance()
        fStore = FirebaseFirestore.getInstance()
        radioGroup = findViewById<View>(R.id.radioGroup) as RadioGroup
        isAdminRadioButton = findViewById<View>(R.id.isAdmin) as RadioButton
        isCustomerRadioButton = findViewById<View>(R.id.isCustomer) as RadioButton
        banner = findViewById<View>(R.id.CafeApp) as TextView
        banner!!.setOnClickListener(this)
        Register = findViewById<View>(R.id.Register) as Button
        Register!!.setOnClickListener(this)
        edtUsername = findViewById<View>(R.id.username_text) as EditText
        edtEmail = findViewById<View>(R.id.email_text) as EditText
        edtPassword = findViewById<View>(R.id.password_text) as EditText
        progressBar = findViewById<View>(R.id.progressBar) as ProgressBar
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.CafeApp -> startActivity(Intent(this, LoginActivity::class.java))
            R.id.Register -> Register()
        }
    }

    private fun Register() {
        val radioGroup = radioGroup?.checkedRadioButtonId
        val username = edtUsername!!.text.toString().trim { it <= ' ' }
        val email = edtEmail!!.text.toString().trim { it <= ' ' }
        val password = edtPassword!!.text.toString().trim { it <= ' ' }
        var roleType = ""
        if (radioGroup == R.id.isAdmin) {
            roleType = "admin"
        } else {
            roleType = "customer"
        }
        if (username.isEmpty()) {
            edtUsername!!.error = "Full name is required!"
            edtUsername!!.requestFocus()
            return
        }

        if (email.isEmpty()) {
            edtEmail!!.error = "Email is required!"
            edtEmail!!.requestFocus()
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            edtEmail!!.error = "Please provide a valid email!"
            edtEmail!!.requestFocus()
            return
        }

        if (password.isEmpty()) {
            edtPassword!!.error = "Password is required!"
            edtPassword!!.requestFocus()
            return
        }

        if (password.length < 6) {
            edtPassword!!.error = "Password length should be at least 6 characters!"
            edtPassword!!.requestFocus()
            return
        }

        mAuth?.createUserWithEmailAndPassword(email, password)
            ?.addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = User()

                    user.roleType = roleType // Set the determined role-type
                    val userId = FirebaseAuth.getInstance().currentUser?.uid

                    if (userId != null) {
                        sendEmailVerification()
                        fStore?.collection("users")?.document(userId)?.set(user)
                            ?.addOnCompleteListener { userCreationTask ->
                                if (userCreationTask.isSuccessful) {
                                    //progressBar!!.visibility = View.VISIBLE
                                    Toast.makeText(
                                        this@RegisterActivity,
                                        "User has been registered successfully!",
                                        Toast.LENGTH_LONG
                                    ).show()
                                    val intent = Intent(
                                        this@RegisterActivity,
                                        LoginActivity::class.java
                                    )
                                    startActivity(intent)
                                    finish()
                                } else {
                                    Toast.makeText(
                                        this@RegisterActivity,
                                        "Failed to register user data! Try again!",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                            }
                    }
                } else {
                    // Check if the failure is due to an existing email
                    if (task.exception?.message?.contains("email address is already in use") == true) {
                        Toast.makeText(
                            this@RegisterActivity,
                            "Email is already registered! Try a different email.",
                            Toast.LENGTH_LONG
                        ).show()
                    } else {
                        Toast.makeText(
                            this@RegisterActivity,
                            "Failed to register! Try again!",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }

    }
    private fun sendEmailVerification() {
        val user: FirebaseUser? = mAuth!!.currentUser
        user?.sendEmailVerification()
            ?.addOnCompleteListener { emailVerificationTask ->
                if (emailVerificationTask.isSuccessful) {
                    // Email sent successfully
                } else {
                    // Failed to send verification email
                    Toast.makeText(
                        this@RegisterActivity,
                        "Failed to send verification email! Try again.",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
    }
}




