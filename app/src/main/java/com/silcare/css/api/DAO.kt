package com.silcare.css.api

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MakamViewModel : ViewModel() {

    private val db = FirebaseFirestore.getInstance()

    private val _mayatList = MutableStateFlow<List<Mayat>>(emptyList())
    private val _blokMakamList = MutableStateFlow<List<BlokMakam>>(emptyList())
    private val _idMakamList = MutableStateFlow<List<IdMakam>>(emptyList())

    val mayatList: StateFlow<List<Mayat>> = _mayatList
    val blokMakamList: StateFlow<List<BlokMakam>> = _blokMakamList
    val idMakamList: StateFlow<List<IdMakam>> = _idMakamList

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