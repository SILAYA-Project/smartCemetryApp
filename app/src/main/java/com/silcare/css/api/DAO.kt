package com.silcare.css.api

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.time.LocalDate

class MakamViewModel : ViewModel() {

    private val db = FirebaseFirestore.getInstance()

    private val _mayatList = MutableStateFlow<List<Mayat>>(emptyList())
    private val _notifikasiList = MutableStateFlow<List<AdminNotifikasi>>(emptyList())
    private val _blokMakamList = MutableStateFlow<List<BlokMakam>>(emptyList())
    private val _idMakamList = MutableStateFlow<List<IdMakam>>(emptyList())

    val notifikasiList: StateFlow<List<AdminNotifikasi>> = _notifikasiList
    val mayatList: StateFlow<List<Mayat>> = _mayatList
    val blokMakamList: StateFlow<List<BlokMakam>> = _blokMakamList
    val idMakamList: StateFlow<List<IdMakam>> = _idMakamList

    @RequiresApi(Build.VERSION_CODES.O)
    fun fetchDataNotifikasi() {
        db.collection("admin notifikasi")
            .get()
            .addOnSuccessListener { snapshot ->
                if (snapshot != null) {
                    val list = snapshot.documents.mapNotNull { doc ->
                        val data = doc.data ?: return@mapNotNull null

                        AdminNotifikasi(
                            status = data["status"] as? Boolean ?: false,
                            inputData = data["inputData"] as? Boolean ?: false,
                            fotoKtpPerwakilan = data["fotoKtpPerwakilan"] as? Boolean ?: false,
                            fotoKtp = data["fotoKtp"] as? Boolean ?: false,
                            fotoKk = data["fotoKk"] as? Boolean ?: false,
                            suratKematian = data["suratKematian"] as? Boolean ?: false,
                            statusNotifikasi = data["statusNotifikasi"] as? Boolean ?: false,

                            id_mayat = data["id_mayat"] as? String ?: "",
                            di_wakilkan_oleh = data["di_wakilkan_oleh"] as? String ?: "",
                            usia = (data["usia"] as? Long)?.toInt() ?: 0,
                            nama_mayat = data["nama_mayat"] as? String ?: "",
                            blok_makam = data["blok_makam"] as? String ?: "",
                            id_makam = data["id_makam"] as? String ?: "",
                            meninggal_di = data["meninggal_di"] as? String ?: "",
                            skm = data["skm"] as? String ?: "",
                            nomor_kk = (data["nomor_kk"] as? Long)?.toInt() ?: 0,
                            nomor_nik = (data["nomor_nik"] as? Long)?.toInt() ?: 0,

                            tanggal_di_makamkan = (data["tanggal_di_makamkan"] as? String)?.let {
                                LocalDate.parse(it)
                            },
                            tanggal_meninggal = (data["tanggal_meninggal"] as? String)?.let {
                                LocalDate.parse(it)
                            },

                            tempat_dan_tanggal_lahir = data["tempat_dan_tanggal_lahir"] as? String ?: "",
                            jenis_kelamin = data["jenis_kelamin"] as? String ?: "",
                            nomor_telpon = data["nomor_telpon"] as? String ?: "",
                            wafat = data["wafat"] as? String ?: "",
                            sebab = data["sebab"] as? String ?: "",
                            alamatm = data["alamatm"] as? String ?: "",
                            alamatw = data["alamatw"] as? String ?: "",
                            email = data["email"] as? String ?: "",
                            rtm = data["rtm"] as? String ?: "",
                            rwm = data["rwm"] as? String ?: "",
                            rtw = data["rtw"] as? String ?: "",
                            rww = data["rww"] as? String ?: "",
                            kelurahanm = data["kelurahanm"] as? String ?: "",
                            kecamatanm = data["kecamatanm"] as? String ?: "",
                            kelurahanw = data["kelurahanw"] as? String ?: "",
                            kecamatanw = data["kecamatanw"] as? String ?: "",
                            hubungan = data["hubungan"] as? String ?: "",
                            agama = data["agama"] as? String ?: "",
                            kewarganegaraan = data["kewarganegaraan"] as? String ?: "",
                            nama_bapak = data["nama_bapak"] as? String ?: "",
                            nama_ibu = data["nama_ibu"] as? String ?: "",
                            suami_atau_istri = data["suami_atau_istri"] as? String ?: "",
                            anak = data["anak"] as? String ?: "",
                        )
                    }
                    _notifikasiList.value = list
                }
            }
    }

    fun tambahNotifikasiAdmin(
        notifikasi: AdminNotifikasi,
        onResult: (Boolean) -> Unit
    ) {
        db.collection("admin notifikasi")
            .add(notifikasi)
            .addOnSuccessListener {
                Log.d("MakamViewModel", "✅ Notifikasi berhasil ditambahkan")
                onResult(true)
            }
            .addOnFailureListener { e ->
                Log.e("MakamViewModel", "❌ Gagal tambah notifikasi", e)
                onResult(false)
            }
    }


    fun fetchIdMakamByBlok(idBlok: String) {
        fetchIdMakamByBlokOnce(idBlok) { list ->
            _idMakamList.value = list
        }
    }

    fun fetchIdMakamByBlokOnce(idBlok: String, onResult: (List<IdMakam>) -> Unit) {
        db.collection("blok makam")
            .document(idBlok)
            .collection("id makam")
            .get()
            .addOnSuccessListener { makamSnapshot ->
                val makamList = makamSnapshot.documents.map { doc ->
                    IdMakam(
                        id = doc.id,
                        code_makam = doc.getString("code_makam") ?: "",
                        status = doc.getBoolean("status") ?: false
                    )
                }

                if (makamList.isEmpty()) {
                    onResult(emptyList())
                    return@addOnSuccessListener
                }

                db.collection("blok makam").document(idBlok)
                    .get()
                    .addOnSuccessListener { blokDoc ->
                        val namaBlok = blokDoc.getString("nama_blok") ?: ""
                        val makamCodes = makamList.map { it.code_makam }.filter { it.isNotEmpty() }

                        if (makamCodes.isEmpty()) {
                            onResult(makamList.map { it.copy(nama_blok = namaBlok) })
                            return@addOnSuccessListener
                        }

                        db.collection("mayat")
                            .whereIn("id_makam", makamCodes)
                            .get()
                            .addOnSuccessListener { mayatSnapshot ->
                                val mayatMap = mayatSnapshot.documents.associate { doc ->
                                    val idMakam = doc.get("id_makam")?.toString() ?: ""
                                    val namaMayat = doc.getString("nama_mayat") ?: ""
                                    idMakam to namaMayat
                                }

                                val finalList = makamList.map { makam ->
                                    makam.copy(
                                        nama_blok = namaBlok,
                                        namaAlmarhum = mayatMap[makam.code_makam] ?: ""
                                    )
                                }

                                onResult(finalList)
                            }
                    }
            }
    }

    fun fetchDataMayat() {
        db.collection("mayat")
            .get()
            .addOnSuccessListener { snapshot ->
                if (snapshot != null) {
                    val list = snapshot.documents.mapNotNull { doc ->
                        val data = doc.toObject(Mayat::class.java)
                        println("DOKUMEN: ${doc.data}")
                        println("MODEL: $data")
                        data
                    }
                    _mayatList.value = list
                }
            }
    }

    fun fetchDataBlokMakam() {
        db.collection("blok makam")
            .get()
            .addOnSuccessListener { snapshot ->

                if (snapshot != null) {
                    val list = snapshot.documents.mapNotNull { doc ->
                        val data = doc.toObject(BlokMakam::class.java)
                        val dataWithId = data?.copy(id = doc.id) // ✅ simpan hasil copy
                        println("DOKUMEN: ${doc.data}")
                        println("MODEL: $dataWithId")
                        Log.d("get id document","id = ${doc.id}")
                        dataWithId // 🔹 pakai yang sudah di-copy
                    }
                    _blokMakamList.value = list
                }
            }
    }
}

object AjuanDataStore {
    private val _listData = mutableListOf<AdminNotifikasi>()
    val listData: List<AdminNotifikasi> get() = _listData

    fun addData(data: AdminNotifikasi) {
        _listData.add(data)
        println("Total data tersimpan: ${_listData.size}")
        println("Data terakhir: ${_listData.last()}")
    }

    fun updateLastData(update: (AdminNotifikasi) -> AdminNotifikasi) {
        if (_listData.isNotEmpty()) {
            val updated = update(_listData.last())
            _listData[_listData.lastIndex] = updated
            println("Data terakhir diupdate: ${_listData.last()}")
        }
    }

    fun getLastData(): AdminNotifikasi? {
        return _listData.lastOrNull()
    }
}