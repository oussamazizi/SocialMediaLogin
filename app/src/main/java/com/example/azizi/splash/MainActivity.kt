package com.example.azizi.splash

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_main.*
import java.net.URL
import java.util.*


class MainActivity : AppCompatActivity() {

    lateinit var providers: List<AuthUI.IdpConfig>
    var MY_REQUEST_CODE = 7117

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        providers = Arrays.asList(
                AuthUI.IdpConfig.EmailBuilder().build(), // Email builder
                AuthUI.IdpConfig.PhoneBuilder().build(), // Phone builder
                //AuthUI.IdpConfig.FacebookBuilder().build(), // Facebook builder
                AuthUI.IdpConfig.GoogleBuilder().build() // Google builder
        )

        showSignInOptions()
    }

    private fun showSignInOptions() {
        startActivityForResult(
                AuthUI.getInstance().createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .setTheme(R.style.MyTheme)
                        .build(), MY_REQUEST_CODE
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == MY_REQUEST_CODE){
            var response: IdpResponse = IdpResponse.fromResultIntent(data)!!
            if(resultCode == Activity.RESULT_OK)
            {
                // Get user
                var user: FirebaseUser? = FirebaseAuth.getInstance().currentUser
                // show email on toast
                Toast.makeText(this, "${user!!.photoUrl}", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MenuActivity::class.java)
                startActivity(intent)
                // Get button signout
            }
            else {
                Toast.makeText(this, "${response.error!!.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}