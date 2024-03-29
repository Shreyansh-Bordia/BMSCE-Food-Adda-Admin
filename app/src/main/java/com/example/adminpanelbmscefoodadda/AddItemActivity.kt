package com.example.adminpanelbmscefoodadda

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import com.example.adminpanelbmscefoodadda.databinding.ActivityAddItemBinding
import com.example.adminpanelbmscefoodadda.model.AllMenu
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage

class AddItemActivity : AppCompatActivity() {

    private lateinit var foodName: String
    private lateinit var foodPrice: String
    private var foodImageUri: Uri? = null
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase

    private val binding: ActivityAddItemBinding by lazy {
        ActivityAddItemBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        binding.addItembutton.setOnClickListener {
            foodName = binding.foodName.text.toString().trim()
            foodPrice = binding.foodPrice.text.toString().trim()

            if(foodName.isBlank() || foodPrice.isBlank()){
                Toast.makeText(this,"Please fill all the details", Toast.LENGTH_SHORT).show()
            }else{
                uploadData()
                Toast.makeText(this,"Item Added Successfully", Toast.LENGTH_SHORT).show()
                finish()
            }
        }

        binding.selectImage.setOnClickListener {
            pickImage.launch("image/*")
        }

        binding.backButton.setOnClickListener {
            finish()
        }
    }

    private fun uploadData() {
        val menuRef: DatabaseReference = database.getReference("menu")
        val newItemKey: String? = menuRef.push().key

        if(foodImageUri != null){
            val storageRef = FirebaseStorage.getInstance().reference
            val imageRef = storageRef.child("menu_images/${newItemKey}.jpg")
            val uploadTask = imageRef.putFile(foodImageUri!!)

            uploadTask.addOnSuccessListener {
                imageRef.downloadUrl.addOnSuccessListener {
                    downloadUri ->
                    val newItem = AllMenu(
                        foodName = foodName,
                        foodPrice = foodPrice,
                        foodImage = downloadUri.toString()
                    )
                    newItemKey?.let {
                        key ->
                        menuRef.child(key).setValue(newItem).addOnSuccessListener {
                            Toast.makeText(this,"Data uploaded Successfully", Toast.LENGTH_SHORT).show()
                        }

                        .addOnFailureListener {
                            Toast.makeText(this,"Data upload Failed", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
            .addOnFailureListener {
                Toast.makeText(this,"Image Upload failed", Toast.LENGTH_SHORT).show()
            }
        }else{
            Toast.makeText(this,"Please select an Image", Toast.LENGTH_SHORT).show()
        }
    }

    private val pickImage = registerForActivityResult(ActivityResultContracts.GetContent()){uri ->
        if(uri != null){
            binding.selectedImage.setImageURI(uri)
            foodImageUri = uri
        }
    }
}