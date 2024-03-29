package com.example.adminpanelbmscefoodadda

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.webkit.WebViewDatabase
import android.widget.Toast
import com.example.adminpanelbmscefoodadda.databinding.ActivitySignupBinding
import com.example.adminpanelbmscefoodadda.model.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class SignupActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var email : String
    private lateinit var password : String
    private lateinit var userName : String
    private lateinit var database: DatabaseReference

    private val binding : ActivitySignupBinding by lazy {
        ActivitySignupBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        auth = Firebase.auth
        database = Firebase.database.reference

        binding.signUpButton.setOnClickListener {

            userName = binding.nameOfOwner.text.toString().trim()
            email = binding.emailOfOwner.text.toString().trim()
            password = binding.passwordOfOwner.text.toString().trim()

            if(userName.isBlank() || email.isBlank() || password.isBlank() ){
                Toast.makeText(this,"Please fill all the details", Toast.LENGTH_SHORT).show()
            }else{
                createAccount(email,password)
            }
        }
        binding.AlreadyHaveAccountButton.setOnClickListener {
            val intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun createAccount(email: String, password: String) {
       auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
           if(task.isSuccessful){
               Toast.makeText(this, "Account created successfully",Toast.LENGTH_SHORT).show()
               saveUserData()
               val intent = Intent(this,LoginActivity::class.java)
               startActivity(intent)
               finish()
           }else{
               Toast.makeText(this, "Account created failed",Toast.LENGTH_SHORT).show()
               Log.d("Account","createAccount: Failure", task.exception)
           }
       }
    }

    private fun saveUserData() {
        userName = binding.nameOfOwner.text.toString().trim()
        email = binding.emailOfOwner.text.toString().trim()
        password = binding.passwordOfOwner.text.toString().trim()
        val user = UserModel(userName,email,password)
        val userId = FirebaseAuth.getInstance().currentUser!!.uid
        database.child("user").child(userId).setValue(user)
    }
}