package com.example.absensimmtcsimulasi

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.absensimmtcsimulasi.Fragment.HomeFragment
import com.example.absensimmtcsimulasi.Fragment.ProfileFragment
import com.example.absensimmtcsimulasi.databinding.ActivityMenuBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class Menu : AppCompatActivity() {

    lateinit var binding: ActivityMenuBinding

    companion object {
        const val EXTRA_NAME = "extra_name"
        const val EXTRA_NAME_SD = "extra_name_sd"

    }

    val fragmentHome: Fragment = HomeFragment()
    val fragmentProfile: Fragment = ProfileFragment()
    val fragmentManager: FragmentManager = supportFragmentManager
    var active: Fragment = fragmentHome


    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val tvDataReceived : TextView = findViewById(R.id.tv_data_received)
        val tvDataReceivedSD : TextView = findViewById(R.id.tv_data_received_SD)

        val sharedPreferences = getSharedPreferences("MY_PRE", MODE_PRIVATE)
        val username = sharedPreferences.getString("NAMA", "").toString()
        tvDataReceived.text = "$username"

//        val name = intent.getStringExtra(EXTRA_NAME)
        val nameSD = intent.getStringExtra(EXTRA_NAME_SD)

        val textSD = "$nameSD"
//        val text = "$name"
//        tvDataReceived.text = text
        tvDataReceivedSD.text = textSD

        Log.d("Menu", "Data Pengguna")

        bottomNavigationView = binding.navViewBottom

        // Inisialisasi fragment pertama
        fragmentManager.beginTransaction().add(R.id.ly_container, fragmentHome).show(fragmentHome).commit()

        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    callFragment(fragmentHome)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navigation_people -> {
                    callFragment(fragmentProfile)
                    return@setOnNavigationItemSelectedListener true
                }
                else -> false
            }
        }
    }

    private fun callFragment(fragment: Fragment) {
        // Menyembunyikan fragment aktif
        fragmentManager.beginTransaction().hide(active).commit()

        // Menampilkan fragment yang dipilih
        fragmentManager.beginTransaction().show(fragment).commit()

        active = fragment
    }
}
