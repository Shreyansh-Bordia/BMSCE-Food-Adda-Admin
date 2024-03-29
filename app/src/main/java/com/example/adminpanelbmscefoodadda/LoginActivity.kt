package com.example.adminpanelbmscefoodadda

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.adminpanelbmscefoodadda.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {

    private lateinit var email: String
    private lateinit var password : String
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    private val binding: ActivityLoginBinding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        auth = Firebase.auth
        database = Firebase.database.reference

        binding.loginButton.setOnClickListener {

            email = binding.emailOfOwner.text.toString().trim()
            password = binding.passwordOfOwner.text.toString().trim()

            if(email.isBlank() || password.isBlank() ){
                Toast.makeText(this,"Please fill all the details", Toast.LENGTH_SHORT).show()
            }else{
                createUserAccount(email,password)
            }
        }
        binding.DontHaveAccountButton.setOnClickListener {
            val intent = Intent(this,SignupActivity::class.java)
            startActivity(intent)
        }
    }

    private fun createUserAccount(email: String, password: String) {
       auth.signInWithEmailAndPassword(email,password).addOnCompleteListener { task ->
           if(task.isSuccessful){
               val user:FirebaseUser? = auth.currentUser
               updateUi(user)
           }else{
               Toast.makeText(this,"User Not Found, Please Sign Up first", Toast.LENGTH_LONG).show()
               startActivity(Intent(this,SignupActivity::class.java))
           }
       }
    }

    override fun onStart() {
        super.onStart()
        val currentUser: FirebaseUser? = auth.currentUser
        if(currentUser!=null){
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }
    }
    private fun updateUi(user: FirebaseUser?) {
        Toast.makeText(this,"Login Successful", Toast.LENGTH_SHORT).show()
        startActivity(Intent(this,MainActivity::class.java))
        finish()
    }
}