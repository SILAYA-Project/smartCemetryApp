package com.silcare.css.api

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.os.Build
import android.util.Log
import android.util.Patterns
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import com.google.firebase.Timestamp
import com.google.firebase.app
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.firestore.FieldValue
import com.silcare.css.AuthPreferences
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date

@RequiresApi(Build.VERSION_CODES.O)
class MakamViewModel : ViewModel() {

    private val db = FirebaseFirestore.getInstance()
    val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private val _mayatList = MutableStateFlow<List<Mayat>>(emptyList())
    private val _notifikasiList = MutableStateFlow<List<AdminNotifikasi>>(emptyList())
    private val _blokMakamList = MutableStateFlow<List<BlokMakam>>(emptyList())
    private val _idMakamList = MutableStateFlow<List<IdMakam>>(emptyList())
    private val _beritaList = MutableStateFlow<List<Berita>>(emptyList())

    val notifikasiList: StateFlow<List<AdminNotifikasi>> = _notifikasiList
    val mayatList: StateFlow<List<Mayat>> = _mayatList
    val blokMakamList: StateFlow<List<BlokMakam>> = _blokMakamList
    val idMakamList: StateFlow<List<IdMakam>> = _idMakamList
    val beritaList = _beritaList.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading
    private val _isLoadingNotifikasi = MutableStateFlow(true)
    val isLoadingNotifikasi: StateFlow<Boolean> = _isLoadingNotifikasi

    fun getUserData(
        onSuccess: (UserData) -> Unit,
        onError: (String) -> Unit,
        uid: String?
    ) {

        if (uid == null) {
            onError("User belum login")
            return
        }

        FirebaseFirestore.getInstance()
            .collection("user")
            .document(uid)
            .get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val nama = document.getString("nama") ?: "Unknown"
                    val email = document.getString("email") ?: "Unknown"
                    val akses = document.getString("akses") ?: "Unknown"
                    val profil = document.getString("profil") ?: ""

                    onSuccess(UserData(nama, email, akses, profil))
                } else {
                    onError("Data user tidak ditemukan")
                }
            }
            .addOnFailureListener { e ->
                onError("Gagal ambil data: ${e.message}")
            }
    }

    fun updateUserToFirestore(
        uid: String,
        nama: String,
        email: String,
        akses: String,
        foto: String?,
        onDone: () -> Unit,
        onError:(String) -> Unit
    ) {
        val data = mutableMapOf<String, Any>(
            "nama" to nama,
            "email" to email,
            "akses" to akses
        )

        if (foto != null) {
            data["profil"] = foto
        }

        FirebaseFirestore.getInstance()
            .collection("user")
            .document(uid)
            .update(data)
            .addOnSuccessListener { onDone() }
            .addOnFailureListener { err ->
                Log.e("UPDATE_PROFILE", "Gagal update: ${err.message}")
                onError(err.message ?: "Terjadi kesalahan saat update profile")
            }
    }




    fun login(
        context: Context,
        email: String?,
        password: String?,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {

        if (email.isNullOrBlank()) {
            onError("Email tidak boleh kosong.")
            return
        }

        val cleanEmail = email.trim()

        if (!Patterns.EMAIL_ADDRESS.matcher(cleanEmail).matches()) {
            onError("Format email tidak valid.")
            return
        }

        if (password.isNullOrBlank()) {
            onError("Password tidak boleh kosong.")
            return
        }

        auth.signInWithEmailAndPassword(cleanEmail, password)
            .addOnCompleteListener { task ->
                try {
                    if (task.isSuccessful) {
                        val uid = auth.currentUser?.uid
                        if (uid == null) {
                            onError("Gagal mendapatkan UID pengguna.")
                            return@addOnCompleteListener
                        }

                        CoroutineScope(Dispatchers.IO).launch {
                            try {
                                AuthPreferences(context).setLoggedIn(true)
                                UserPreferences(context).saveUid(uid)
                            } catch (e: Exception) {
                                Log.e("LoginPrefError", e.message.toString())
                            }
                        }

                        onSuccess()

                    } else {
                        val e = task.exception
                        val msg = when (e) {
                            is FirebaseAuthInvalidCredentialsException -> "Email atau password salah."
                            is FirebaseAuthInvalidUserException -> "Akun tidak ditemukan."
                            else -> e?.localizedMessage ?: "Login gagal. Coba lagi."
                        }
                        onError(msg)
                    }
                } catch (e: Exception) {
                    onError("Terjadi kesalahan: ${e.message}")
                }
            }
            .addOnFailureListener { e ->
                onError("Login gagal: ${e.message}")
            }
    }

    fun register(
        context: Context,
        nama: String,
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener { authResult ->
                val uid = authResult.user?.uid
                if (uid == null) {
                    onError("UID tidak ditemukan.")
                    return@addOnSuccessListener
                }

                val userMap = mapOf(
                    "uid" to uid,
                    "nama" to nama,
                    "email" to email,
                    "password" to password,
                    "profil" to "https://res.cloudinary.com/drwwnzu1r/image/upload/v1750698734/gddsd4lbb8i9tngusdoa.png",
                    "akses" to "user",
                )

                db.collection("user").document(uid).set(userMap)
                    .addOnSuccessListener {
                        Log.d("Register", "Registrasi berhasil dan data disimpan.")

                        CoroutineScope(Dispatchers.IO).launch {
                            AuthPreferences(context).setLoggedIn(true)
                        }
                        onSuccess()
                    }
                    .addOnFailureListener { e ->
                        Log.e("FirestoreError", "Gagal simpan data: ${e.message}")
                        onError("Registrasi berhasil, tapi gagal menyimpan data.")
                    }
            }
            .addOnFailureListener { exception ->
                val errorMessage = when (exception) {
                    is FirebaseAuthUserCollisionException -> "Email sudah digunakan."
                    is FirebaseAuthWeakPasswordException -> "Password terlalu lemah (minimal 6 karakter)."
                    is FirebaseAuthInvalidCredentialsException -> "Format email tidak valid."
                    else -> exception.localizedMessage ?: "Registrasi gagal. Coba lagi nanti."
                }
                Log.e("RegisterError", errorMessage, exception)
                onError(errorMessage)
            }
    }

    fun konfirmasiNotifikasi(notifikasi: AdminNotifikasi, onResult: (Boolean) -> Unit) {
        val dataMayat = Mayat(
            id_mayat = notifikasi.id_mayat,
            nama_mayat = notifikasi.nama_mayat,
            blok_makam = notifikasi.blok_makam,
            id_makam = notifikasi.id_makam,
            usia = notifikasi.usia,
            meninggal_di = notifikasi.meninggal_di,
            tanggal_meninggal = notifikasi.tanggal_meninggal,
            tanggal_di_makamkan = notifikasi.tanggal_di_makamkan,
            tempat_dan_tanggal_lahir = notifikasi.tempat_dan_tanggal_lahir,
            jenis_kelamin = notifikasi.jenis_kelamin,
            nomor_telpon = notifikasi.nomor_telpon,
            sebab = notifikasi.sebab,
            alamat = notifikasi.alamatm,
            email = notifikasi.email,
            di_wakilkan_oleh = notifikasi.di_wakilkan_oleh,
            nomor_nik = notifikasi.nomor_nik,
            wafat = notifikasi.wafat,
            nomor_kk = notifikasi.nomor_kk,
        )
        println("tanggal di makamkan = ${notifikasi.tanggal_di_makamkan}")
        db.collection("mayat")
            .add(dataMayat)
            .addOnSuccessListener { ref ->
                db.collection("admin notifikasi")
                    .whereEqualTo("id_mayat", notifikasi.id_mayat)
                    .get()
                    .addOnSuccessListener { snapshot ->
                        for (doc in snapshot.documents) {
                            db.collection("admin notifikasi")
                                .document(doc.id)
                                .update(
                                    mapOf(
                                        "status" to true,
                                        "statusNotifikasi" to true
                                    )
                                )
                                .addOnSuccessListener {
                                    _notifikasiList.value = _notifikasiList.value.map { item ->
                                        if (item.id_mayat == notifikasi.id_mayat) {
                                            item.copy(
                                                status = true,
                                                statusNotifikasi = true
                                            )
                                        } else item
                                    }
                                    Log.d(
                                        "MakamViewModel",
                                        "✅ Status notifikasi berhasil diupdate (local)"
                                    )
                                }
                        }
                        db.collection("blok makam")
                            .whereEqualTo("nama_blok", notifikasi.blok_makam)
                            .get()
                            .addOnSuccessListener { snapshot ->
                                if (!snapshot.isEmpty) {
                                    for (doc in snapshot.documents) {
                                        db.collection("blok makam")
                                            .document(doc.id)
                                            .update("isi", FieldValue.increment(1))
                                            .addOnSuccessListener {
                                                Log.d(
                                                    "MakamViewModel",
                                                    "✅ totalIsi blok ${notifikasi.blok_makam} berhasil ditambah 1"
                                                )
                                            }
                                            .addOnFailureListener { e ->
                                                Log.e(
                                                    "MakamViewModel",
                                                    "❌ Gagal update totalIsi blok ${notifikasi.blok_makam}",
                                                    e
                                                )
                                            }
                                    }
                                } else {
                                    Log.e(
                                        "MakamViewModel",
                                        "❌ Tidak ditemukan blok makam dengan nama ${notifikasi.blok_makam}"
                                    )
                                }
                            }
                            .addOnFailureListener { e ->
                                Log.e("MakamViewModel", "❌ Query blok makam gagal", e)
                            }
                        onResult(true)
                    }
            }
            .addOnFailureListener { e ->
                Log.e("MakamViewModel", "❌ Gagal tambah mayat", e)
                onResult(false)
            }
    }

    fun fetchDataNotifikasi() {
        _isLoadingNotifikasi.value = true
        db.collection("admin notifikasi")
            .orderBy("tanggal_pengajuan", Query.Direction.DESCENDING)
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
                            usia = data["usia"] as? String ?: "",
                            nama_mayat = data["nama_mayat"] as? String ?: "",
                            blok_makam = data["blok_makam"] as? String ?: "",
                            id_makam = data["id_makam"] as? String ?: "",
                            meninggal_di = data["meninggal_di"] as? String ?: "",
                            skm = data["skm"] as? String ?: "",
                            nomor_kk = data["nomor_kk"] as? String ?: "",
                            nomor_nik = data["nomor_nik"] as? String ?: "",

                            tanggal_di_makamkan = (data["tanggal_di_makamkan"] as? Timestamp),
                            tanggal_meninggal = (data["tanggal_meninggal"] as? Timestamp),

                            tempat_dan_tanggal_lahir = data["tempat_dan_tanggal_lahir"] as? String
                                ?: "",
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
                    val sortedList = list
                        .sortedWith(compareBy<AdminNotifikasi> { it.statusNotifikasi }.thenByDescending { it.tanggal_pengajuan })
                    _notifikasiList.value = sortedList
                    _isLoadingNotifikasi.value = false
                }
            }
            .addOnFailureListener {
                _isLoadingNotifikasi.value = false
            }
    }

    fun tolakNotifikasi(notifikasi: AdminNotifikasi, onResult: (Boolean) -> Unit) {
        db.collection("admin notifikasi")
            .whereEqualTo("id_mayat", notifikasi.id_mayat)
            .get()
            .addOnSuccessListener { snapshot ->
                for (doc in snapshot.documents) {
                    db.collection("admin notifikasi")
                        .document(doc.id)
                        .update(
                            mapOf(
                                "status" to false,
                                "statusNotifikasi" to true
                            )
                        )
                        .addOnSuccessListener {
                            _notifikasiList.value = _notifikasiList.value.map { item ->
                                if (item.id_mayat == notifikasi.id_mayat) {
                                    item.copy(
                                        status = false,
                                        statusNotifikasi = true
                                    )
                                } else item
                            }
                            Log.d("MakamViewModel", "✅ Notifikasi berhasil ditolak")
                            onResult(true)
                        }
                        .addOnFailureListener { e ->
                            Log.e("MakamViewModel", "❌ Gagal tolak notifikasi", e)
                            onResult(false)
                        }
                }
            }
            .addOnFailureListener { e ->
                Log.e("MakamViewModel", "❌ Gagal mencari notifikasi", e)
                onResult(false)
            }
    }

    fun tambahNotifikasiAdmin(
        notifikasi: AdminNotifikasi,
        onResult: (Boolean) -> Unit
    ) {
        db.collection("admin notifikasi")
            .add(notifikasi)
            .addOnSuccessListener { ref ->
                Log.d("MakamViewModel", "✅ Notifikasi berhasil ditambahkan, docId = ${ref.id}")

                db.collection("admin notifikasi")
                    .document(ref.id)
                    .update("id_mayat", ref.id)
                    .addOnSuccessListener {
                        Log.d("MakamViewModel", "✅ id_makam berhasil diupdate dengan docId")
                        onResult(true)
                    }
                    .addOnFailureListener { e ->
                        Log.e("MakamViewModel", "❌ Gagal update id_makam", e)
                        onResult(false)
                    }
            }
            .addOnFailureListener { e ->
                Log.e("MakamViewModel", "❌ Gagal tambah notifikasi", e)
                onResult(false)
            }
    }

    fun fetchIdMakamByBlok(idBlok: String) {
        fetchIdMakamByBlokOnce(idBlok) { list ->
            val filteredList = list.filter { !it.status }
            _idMakamList.value = filteredList
            Log.e("fetchIdMakamByBlok 1: ", filteredList.toString())
        }
        Log.e("fetchIdMakamByBlok 2: ", idBlok)
        Log.e("fetchIdMakamByBlok 3: ", _idMakamList.value.toString())
    }

    fun fetchIdMakamByBlokOnce(
        idBlok: String,
        onResult: (List<IdMakam>) -> Unit
    ) {
        Log.d("FETCH_MAKAM", "Mulai fetch blok: $idBlok")

        db.collection("blok makam")
            .document(idBlok)
            .collection("id makam")
            .get()
            .addOnSuccessListener { makamSnapshot ->
                Log.d("FETCH_MAKAM", "Jumlah dokumen makam ditemukan: ${makamSnapshot.size()}")

                val makamList = makamSnapshot.documents.map { doc ->
                    Log.d("FETCH_MAKAM", "Doc makam ID=${doc.id}, code=${doc.getString("code_makam")}, status=${doc.getBoolean("status")}")

                    IdMakam(
                        id = doc.id,
                        code_makam = doc.getString("code_makam") ?: "",
                        status = doc.getBoolean("status") ?: false
                    )
                }

                if (makamList.isEmpty()) {
                    Log.w("FETCH_MAKAM", "List makam kosong")
                    onResult(emptyList())
                    return@addOnSuccessListener
                }

                db.collection("blok makam").document(idBlok)
                    .get()
                    .addOnSuccessListener { blokDoc ->
                        val namaBlok = blokDoc.getString("nama_blok") ?: ""
                        Log.d("FETCH_MAKAM", "Nama blok ditemukan: $namaBlok")

                        val makamCodes = makamList.map { it.code_makam }.filter { it.isNotEmpty() }
                        Log.d("FETCH_MAKAM", "Kode makam: $makamCodes")

                        if (makamCodes.isEmpty()) {
                            Log.w("FETCH_MAKAM", "Semua code_makam kosong!")
                            onResult(makamList.map { it.copy(nama_blok = namaBlok) })
                            return@addOnSuccessListener
                        }

                        if (makamCodes.size > 10) {
                            Log.e("FETCH_MAKAM", "ERROR! whereIn melebihi limit 10: jumlah = ${makamCodes.size}")
                        }

                        db.collection("mayat")
                            .whereIn("id_makam", makamCodes.take(10))
                            .get()
                            .addOnSuccessListener { mayatSnapshot ->
                                Log.d("FETCH_MAYAT", "Jumlah mayat ditemukan: ${mayatSnapshot.size()}")

                                val mayatMap = mayatSnapshot.documents.associate { doc ->
                                    val idMakam = doc.get("id_makam")?.toString() ?: ""
                                    val namaMayat = doc.getString("nama_mayat") ?: ""
                                    Log.d("FETCH_MAYAT", "Mayat: id_makam=$idMakam, nama=$namaMayat")
                                    idMakam to namaMayat
                                }

                                val finalList = makamList.map { makam ->
                                    makam.copy(
                                        nama_blok = namaBlok,
                                        namaAlmarhum = mayatMap[makam.code_makam] ?: ""
                                    ).also {
                                        Log.d("FETCH_RESULT", "Final: ${it.code_makam} - ${it.namaAlmarhum}")
                                    }
                                }

                                onResult(finalList)
                            }
                            .addOnFailureListener { error ->
                                Log.e("FETCH_MAYAT", "Gagal fetch mayat: ${error.message}")
                            }
                    }
                    .addOnFailureListener { error ->
                        Log.e("FETCH_BLOK", "Gagal fetch blok: ${error.message}")
                    }
            }
            .addOnFailureListener { error ->
                Log.e("FETCH_MAKAM", "Gagal fetch id makam: ${error.message}")
            }
    }


    fun fetchDataMayat(searchName: String? = null, filter: String? = null) {
        val keyword = searchName?.trim()?.lowercase()
        _isLoading.value = true
        db.collection("mayat")
            .orderBy("tanggal_di_makamkan",Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener { snapshot ->
                if (snapshot != null) {
                    val list = snapshot.documents.mapNotNull { doc ->
                        val tanggalDimakamkan = (doc.get("tanggal_di_makamkan") as? Timestamp)
                            ?.toDate()
                            ?.toInstant()
                            ?.atZone(ZoneId.systemDefault())
                            ?.toLocalDateTime()
                        println("RAW tanggal_di_makamkan: $tanggalDimakamkan (${tanggalDimakamkan?.javaClass})")

                        val tanggalMeninggal = (doc.get("tanggal_meninggal") as? Timestamp)
                            ?.toDate()
                            ?.toInstant()
                            ?.atZone(ZoneId.systemDefault())
                            ?.toLocalDateTime()
                        println("RAW tanggal_meninggal: $tanggalMeninggal (${tanggalMeninggal?.javaClass})")

                        Mayat(
                            id_mayat = doc.getString("id_mayat") ?: "-----",
                            di_wakilkan_oleh = doc.getString("di_wakilkan_oleh") ?: "-----",
                            usia = doc.getString("usia") ?: "-----",
                            nama_mayat = doc.getString("nama_mayat") ?: "-----",
                            blok_makam = doc.getString("blok_makam") ?: "-----",
                            id_makam = doc.getString("id_makam") ?: "-----",
                            meninggal_di = doc.getString("meninggal_di") ?: "-----",
                            skm = doc.getString("skm") ?: "-----",
                            nomor_kk = doc.getString("nomor_kk") ?: "-----",
                            nomor_nik = doc.getString("nomor_nik") ?: "-----",
                            tanggal_di_makamkan = doc.getTimestamp("tanggal_di_makamkan"),
                            tanggal_meninggal = doc.getTimestamp("tanggal_meninggal"),
                            tempat_dan_tanggal_lahir = doc.getString("tempat_dan_tanggal_lahir")
                                ?: "-----",
                            jenis_kelamin = doc.getString("jenis_kelamin") ?: "-----",
                            nomor_telpon = doc.getString("nomor_telpon") ?: "-----",
                            wafat = doc.getString("wafat") ?: "-----",
                            sebab = doc.getString("sebab") ?: "-----",
                            alamat = doc.getString("alamat") ?: "-----",
                            email = doc.getString("email") ?: "-----"
                        )
                    }

                    val filteredList = if (!keyword.isNullOrEmpty()) {
                        list.filter { item ->
                            item.nama_mayat?.lowercase()?.contains(keyword) == true
                            item.di_wakilkan_oleh?.lowercase()?.contains(keyword) == true
                        }
                    } else {
                        list
                    }

                    val sortedList = when (filter) {
                        "az" -> filteredList.sortedBy { it.nama_mayat?.lowercase() }
                        "za" -> filteredList.sortedByDescending { it.nama_mayat?.lowercase() }
                        "max" -> filteredList.sortedByDescending { it.tanggal_meninggal }
                        "min" -> filteredList.sortedBy { it.tanggal_di_makamkan }
                        else -> filteredList
                    }
                    _mayatList.value = sortedList
                }
                _isLoading.value = false
            }
            .addOnFailureListener {
                _isLoading.value = false
            }
    }

    fun tambahBlokMakam(namaBlok: String, kapasitas: Int, onResult: (Boolean) -> Unit) {
        val blok = mapOf(
            "nama_blok" to namaBlok,
            "maximal" to kapasitas,
            "isi" to 0
        )

        db.collection("blok makam")
            .add(blok)
            .addOnSuccessListener { docRef ->
                Log.d("MakamViewModel", "✅ Blok makam berhasil ditambahkan: ${docRef.id}")

                val batch = db.batch()
                for (i in 0 until kapasitas) {
                    val codeMakam = generateCodeMakam(namaBlok, i)
                    val makamRef = db.collection("blok makam")
                        .document(docRef.id)
                        .collection("id makam")
                        .document()

                    val makamData = mapOf(
                        "code_makam" to codeMakam,
                        "status" to false
                    )
                    batch.set(makamRef, makamData)
                }

                batch.commit()
                    .addOnSuccessListener {
                        Log.d("MakamViewModel", "✅ Semua id makam berhasil dibuat")
                        onResult(true)
                    }
                    .addOnFailureListener { e ->
                        Log.e("MakamViewModel", "❌ Gagal membuat id makam", e)
                        onResult(false)
                    }
            }
            .addOnFailureListener { e ->
                Log.e("MakamViewModel", "❌ Gagal tambah blok makam", e)
                onResult(false)
            }
    }

    fun fetchDataBlokMakam(
        searchName: String? = null,
        filterType: String? = null
    ) {
        val keyword = searchName?.trim()?.lowercase()

        db.collection("blok makam")
            .get()
            .addOnSuccessListener { snapshot ->

                if (snapshot != null) {

                    val list = snapshot.documents.mapNotNull { doc ->
                        val data = doc.toObject(BlokMakam::class.java)
                        data?.copy(id = doc.id)
                    }

                    val filteredList = if (!keyword.isNullOrEmpty()) {
                        list.filter { item ->
                            item.nama_blok?.lowercase()?.contains(keyword) == true
                        }
                    } else {
                        list
                    }

                    val sortedList = when (filterType) {
                        "az" -> filteredList.sortedBy { it.nama_blok?.lowercase() }
                        "za" -> filteredList.sortedByDescending { it.nama_blok?.lowercase() }
                        "max" -> filteredList.sortedByDescending { it.isi ?: 0 }
                        "min" -> filteredList.sortedBy { it.isi ?: 0 }
                        else -> filteredList
                    }

                    _blokMakamList.value = sortedList
                }
            }
    }

    fun fetchDataBerita() {
        viewModelScope.launch {
            db.collection("berita")
                .get()
                .addOnSuccessListener { snapshot ->
                    val list = snapshot.documents.mapNotNull { it.toObject(Berita::class.java) }
                    _beritaList.value = list
                }
        }
    }

    private val _notifikasiDetail = MutableStateFlow<AdminNotifikasi?>(null)
    val notifikasiDetail: StateFlow<AdminNotifikasi?> = _notifikasiDetail

    fun fetchNotifikasiByDocId(
        docId: String,
        onSuccess: (AdminNotifikasi) -> Unit,
        onError: (String) -> Unit
    ) {
        db.collection("admin notifikasi")
            .document(docId)
            .get()
            .addOnSuccessListener { doc ->
                if (doc.exists()) {
                    val data = doc.toObject(AdminNotifikasi::class.java)
                    if (data != null) {
                        _notifikasiDetail.value = data
                        onSuccess(data)
                    } else {
                        onError("Data tidak valid")
                    }
                } else {
                    onError("Data tidak ditemukan")
                }
            }
            .addOnFailureListener { e ->
                onError(e.message ?: "Gagal ambil data")
            }
    }
}

class UploadRepository {
    suspend fun uploadToCloudinary(context: Context, uri: Uri): String? {
        return withContext(Dispatchers.IO) {
            try {
                val inputStream =
                    context.contentResolver.openInputStream(uri) ?: return@withContext null
                val file = File.createTempFile("upload", ".jpg", context.cacheDir)
                file.outputStream().use { inputStream.copyTo(it) }

                val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
                val multipart = MultipartBody.Part.createFormData("file", file.name, requestFile)

                val uploadPreset = "unsigned_present"
                val uploadPresetBody = uploadPreset.toRequestBody("text/plain".toMediaTypeOrNull())

                val response =
                    CloudinaryApiClient.apiService.uploadToCloudinary(multipart, uploadPresetBody)
                if (response.isSuccessful) {
                    response.body()?.secure_url
                } else {
                    Log.e("CLOUDINARY_ERROR", "Gagal: ${response.errorBody()?.string()}")
                    null
                }
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun Timestamp?.toFormattedString(
    pattern: String = "dd MMMM yyyy, HH:mm"
): String {
    if (this == null) return "-"
    val ldt = this.toDate()
        .toInstant()
        .atZone(ZoneId.systemDefault())
        .toLocalDateTime()
    return ldt.format(DateTimeFormatter.ofPattern(pattern))
}

@RequiresApi(Build.VERSION_CODES.O)
fun LocalDateTime?.toTimestamp(): Timestamp? {
    return this?.let {
        val instant = it.atZone(ZoneId.systemDefault()).toInstant()
        Timestamp(Date.from(instant))
    }
}

@SuppressLint("DefaultLocale")
fun generateCodeMakam(namaBlok: String, count: Int): String {
    val inisial = namaBlok
        .trim()
        .split(" ")
        .filter { it.isNotBlank() }
        .map { it.first().uppercaseChar() }
        .joinToString("")

    return "$inisial-${String.format("%03d", count + 1)}"
}

object AjuanDataStore {
    private val _listData = mutableListOf<AdminNotifikasi>()
    val listData: List<AdminNotifikasi> get() = _listData

    private fun isDataLengkap(data: AdminNotifikasi): Boolean {
        val stringFields = listOf(
            data.id_mayat, data.di_wakilkan_oleh, data.nama_mayat, data.blok_makam, data.id_makam,
            data.meninggal_di, data.skm, data.tempat_dan_tanggal_lahir, data.jenis_kelamin,
            data.nomor_telpon, data.wafat, data.sebab, data.alamatm, data.alamatw, data.email,
            data.rtm, data.rwm, data.rtw, data.rww,
            data.kelurahanm, data.kecamatanm, data.kelurahanw, data.kecamatanw,
            data.hubungan, data.agama, data.kewarganegaraan, data.nama_bapak, data.nama_ibu,
            data.suami_atau_istri, data.anak, data.usia, data.nomor_kk, data.nomor_nik
        )

//        val numbersValid = data.usia > 0 && data.nomor_kk > 0 && data.nomor_nik > 0
        val tanggalValid = data.tanggal_di_makamkan != null && data.tanggal_meninggal != null

        return stringFields.all { it.isNotBlank() } && tanggalValid
    }

    private fun isKtpPerwakilan(data: AdminNotifikasi): Boolean {
        return data.urlFotoKtpPerwakilan.isNotBlank()
    }

    private fun isKtpAlm(data: AdminNotifikasi): Boolean {
        return data.urlFotoKtp.isNotBlank()
    }

    private fun isKk(data: AdminNotifikasi): Boolean {
        return data.urlFotoKk.isNotBlank()
    }

    private fun isSuratKematian(data: AdminNotifikasi): Boolean {
        return data.urlSuratKematian.isNotBlank()
    }

    private fun withValidasi(data: AdminNotifikasi): AdminNotifikasi {
        return data.copy(
            inputData = isDataLengkap(data),
            fotoKk = isKk(data),
            fotoKtp = isKtpAlm(data),
            fotoKtpPerwakilan = isKtpPerwakilan(data),
            suratKematian = isSuratKematian(data)
        )
    }

    fun addData(data: AdminNotifikasi) {
        val validated = withValidasi(data)
        _listData.add(validated)
        println("Total data tersimpan: ${_listData.size}")
        println("Data terakhir: ${_listData.last()}")
    }

    fun updateLastData(update: (AdminNotifikasi) -> AdminNotifikasi) {
        if (_listData.isNotEmpty()) {
            val updated = withValidasi(update(_listData.last()))
            _listData[_listData.lastIndex] = updated
            println("Data terakhir diupdate: ${_listData.last()}")
        }
    }

    fun getLastData(): AdminNotifikasi? {
        return _listData.lastOrNull()
    }
}