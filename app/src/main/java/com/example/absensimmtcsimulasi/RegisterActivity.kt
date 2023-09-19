package com.example.absensimmtcsimulasi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.absensimmtcsimulasi.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initializedRegisterFirebase()
    }

    private fun initializedRegisterFirebase() {
        firebaseAuth = FirebaseAuth.getInstance()

        binding.TvSignIn.setOnClickListener {
            val moveIntentLogin = Intent(this, LoginActivity::class.java)
            startActivity(moveIntentLogin)
        }

        binding.btnRegister.setOnClickListener {
            val nama = binding.ETNamaReg.text.toString().trim()
            val email = binding.ETEmailReg.text.toString().trim()
            val password = binding.ETPasswordReg.text.toString().trim()

            println("ini email : $email dan password : $password")

            if (nama.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(
                    this,
                    "Harus diisi, ga boleh kosong, cukup hati aja yang kosong",
                    Toast.LENGTH_LONG
                ).show()

            } else {
                // registrasi dengan Firebase
                firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Registrasi berhasil, simpan nama pengguna di SharedPreferences
                            saveUsernameInSharedPreferences(nama)
                            Toast.makeText(this, "Registrasi berhasil", Toast.LENGTH_LONG).show()
                            val moveIntentLog = Intent(this, LoginActivity::class.java)
                            startActivity(moveIntentLog)
                            finish()

                        } else {
                            Toast.makeText(
                                this,
                                "Gagal melakukan registrasi. Periksa kembali email dan password Anda.",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
            }
        }
    }

    private fun saveUsernameInSharedPreferences(nama: String) {
        val sharedPreferences = getSharedPreferences("MY_PRE", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("NAMA", nama)
        editor.apply()
    }
}

