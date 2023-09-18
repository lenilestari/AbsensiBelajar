package com.example.absensimmtcsimulasi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.absensimmtcsimulasi.databinding.ActivityLoginBinding
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth


class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.TVLogin.setOnClickListener {
            val moveIntentRegister = Intent(this, RegisterActivity::class.java)
            startActivity(moveIntentRegister)
        }

        binding.btnLogin.setOnClickListener {
            val email = binding.ETEmailLogin.text.toString()
            val password = binding.ETPasswordLogin.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {

                            val user = firebaseAuth.currentUser
                            val userName = user?.displayName

                            val moveIntentReg = Intent(this, Menu::class.java)

                            moveIntentReg.putExtra(Menu.EXTRA_NAME, userName)
                            startActivity(moveIntentReg)


                        } else {
                            Toast.makeText(this, "Gagal melakukan login. Periksa kembali email dan password Anda", Toast.LENGTH_LONG).show()

                        }
                    }
            } else {
                Toast.makeText(this, "Harus diisi, ga boleh kosong, cukup hati aja yang kosong", Toast.LENGTH_LONG).show()
            }
        }
    }
}
