package com.example.user.chhatisgarhtourist

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        AlreadyRegister.setOnClickListener {
            val intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)
        }

        Registerbtn.setOnClickListener {
           firebaseAuth()
        }
    }

     fun firebaseAuth() {
         val email = EmailText.text.toString()
         val password = PasswordText.text.toString()
         val username = Username.text.toString()

         if(email.isEmpty()|| password.isEmpty()) {
             Toast.makeText(this,"Empty!Plz Fill It",Toast.LENGTH_SHORT).show()
             return
         }

         FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password)
                 .addOnCompleteListener {
                     if(!it.isSuccessful) return@addOnCompleteListener

                     val intent = Intent(this,MainScreen::class.java)
                     intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                     startActivity(intent)

                     firebasedatabase()
                 }
                 .addOnFailureListener {
                     Toast.makeText(this,"Fail to Register",Toast.LENGTH_SHORT).show()
                 }
     }

    private fun firebasedatabase() {
        val uid = FirebaseAuth.getInstance().uid?:""
        val ref =  FirebaseDatabase.getInstance().getReference("/user/$uid")

        val user = User(uid,Username.text.toString())
        ref.setValue(user)
                .addOnSuccessListener {

                }
    }

    class User(val uid:String,val username:String)
}
