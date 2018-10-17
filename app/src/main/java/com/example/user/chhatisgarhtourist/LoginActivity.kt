package com.example.user.chhatisgarhtourist

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.login_activity.*

class LoginActivity: AppCompatActivity()  {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)

        Loginbtn.setOnClickListener {
           loginfirebase()
        }

    }

    fun loginfirebase() {
        val email = Loginemail.text.toString()
        val password = Loginpassword.text.toString()

        if(email.isEmpty()||password.isEmpty()) {
            Toast.makeText(this,"Empty!Plz Fill",Toast.LENGTH_SHORT).show()
            return
        }

        FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password)
                .addOnCompleteListener() {
                    if(!it.isSuccessful) return@addOnCompleteListener

                    val intent = Intent(this,MainScreen::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                }
                .addOnFailureListener {
                    Toast.makeText(this,"fail to Register",Toast.LENGTH_SHORT).show()
                }
    }
}