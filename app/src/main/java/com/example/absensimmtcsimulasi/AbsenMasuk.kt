package com.example.absensimmtcsimulasi

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import java.io.IOException
import java.util.Locale

class AbsenMasuk : AppCompatActivity() {

    private lateinit var AmbilFoto: TextView
    private lateinit var imageSelfie: ImageView
    private val REQUEST_CAMERA_PERMISSION = 100
    private val REQUEST_LOCATION_PERMISSION = 200
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private lateinit var strCurrentLocation: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_absen_masuk)

        AmbilFoto = findViewById(R.id.AmbilFoto)
        imageSelfie = findViewById(R.id.imageSelfie)

        // Inisialisasi tombol Ambil Foto TextView
        val tombolAmbilFoto = findViewById<TextView>(R.id.AmbilFoto)
        tombolAmbilFoto.isEnabled = true // Aktifkan tombol Ambil Foto jika izin kamera sudah diberikan

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        Log.d("ABSEN SIMULASI", "Izin Kamera: ${ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)}")
        Log.d("ABSEN SIMULASI", "Izin Lokasi: ${ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)}")

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Minta izin kamera untuk pengambilan foto
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CAMERA),
                REQUEST_CAMERA_PERMISSION
            )
        } else {
            // Izin kamera sudah diberikan, aktifkan tombol Ambil Foto
            AmbilFoto.isEnabled = true
        }

        AmbilFoto.setOnClickListener {
            // Saat tombol Ambil Foto diklik, cek izin lokasi
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // Jika izin lokasi belum diberikan, minta izin lokasi
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    REQUEST_LOCATION_PERMISSION
                )
            } else {
                // Jika izin lokasi sudah diberikan, dapatkan lokasi dan buka kamera
                dapatkanLokasiDanBukaKamera()
            }
        }
    }

    private fun dapatkanLokasiDanBukaKamera() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                if (location != null) {
                    // Lokasi berhasil didapatkan, Anda dapat mengakses lokasi di sini
                    val latitude = location.latitude
                    val longitude = location.longitude

                    val geocoder = Geocoder(this@AbsenMasuk, Locale.getDefault())
                    try {
                        val addressList =
                            geocoder.getFromLocation(latitude, longitude, 1)
                        if (addressList != null && addressList.isNotEmpty()) {
                            strCurrentLocation = addressList[0].getAddressLine(0)
                            tampilkanLokasi(strCurrentLocation)
                        }
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }

                    bukaKamera()
                } else {
                    // Lokasi tidak tersedia, Anda dapat menangani kasus ini
                    Toast.makeText(this@AbsenMasuk, "Lokasi tidak tersedia.", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun bukaKamera() {
        val i = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(i, 101)
    }

    private fun tampilkanLokasi(locationString: String) {
        // Tampilkan lokasi dalam sebuah TextView atau melakukan apa pun yang Anda inginkan dengan lokasi ini
        val textViewLokasi = findViewById<TextView>(R.id.inputLokasi)
        textViewLokasi.text = locationString
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 101 && resultCode == Activity.RESULT_OK) {
            // Jika pengambilan gambar dari kamera berhasil
            val pic: Bitmap? = data?.extras?.get("data") as Bitmap?
            if (pic != null) {
                // Tampilkan gambar selfie di ImageView
                imageSelfie.setImageBitmap(pic)

            }
        }
    }


}



