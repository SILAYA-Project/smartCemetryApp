package com.silcare.css.addDataCsvToFirebase

import android.content.Context
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import java.io.BufferedReader
import java.io.InputStreamReader

class CsvImporter(private val context: Context) {

    private val db = FirebaseFirestore.getInstance()

    fun importCsv(fileName: String = "data.csv", onComplete: (Boolean) -> Unit) {
        try {
            val inputStream = context.assets.open(fileName)
            val reader = BufferedReader(InputStreamReader(inputStream))

            var line: String?
            var isFirstLine = true

            while (reader.readLine().also { line = it } != null) {
                val values = line!!.split(",")

                if (isFirstLine) {
                    isFirstLine = false
                } else {
                    val data = mapOf(
                        "id_mayat" to values[0].trim().toInt(),
                        "id_makam" to values[1].trim(),
                        "blok_makam" to values[2].trim(),
                        "nama_mayat" to values[3].trim(),
                        "tempat_dan_tanggal_lahir" to values[4].trim(),
                        "nik" to values[5].trim().toLong(),
                        "jenis_kelamin" to values[6].trim(),
                        "alamat" to values[7].trim(),
                        "wafat" to values[8].trim(),
                        "meninggal_di" to values[9].trim(),
                        "sebab" to values[10].trim(),
                        "tanggal_di_makamkan" to values[11].trim(),
                        "di_wakilkan_oleh" to values[12].trim(),
                        "skm" to values[13].trim(),
                        "usia" to 0,
                        "nomor_kk" to 0,
                        "nomor_telpon" to "",
                        "email" to ""
                    )

                    db.collection("mayat")
                        .add(data)
                        .addOnSuccessListener {
                            Log.d("CsvImporter", "✅ Berhasil tambah: $data")
                        }
                        .addOnFailureListener { e ->
                            Log.e("CsvImporter", "❌ Gagal: $e")
                        }
                }
            }
            reader.close()
            onComplete(true)
        } catch (e: Exception) {
            Log.e("CsvImporter", "Error: ${e.message}")
            e.printStackTrace()
            onComplete(false)
        }
    }
}