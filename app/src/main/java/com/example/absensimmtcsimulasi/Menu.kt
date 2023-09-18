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

        val name = intent.getStringExtra(EXTRA_NAME)

        val text = "$name"
        tvDataReceived.text = text

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
