package com.silcare.css.api

import com.google.firebase.Timestamp

data class Mayat(
    val id_mayat: String = "",
    val di_wakilkan_oleh: String = "",
    val usia: String = "",
    val nama_mayat: String = "",
    val blok_makam: String = "",
    val id_makam: String = "",
    val meninggal_di: String = "",
    val skm: String = "",
    val nomor_kk: String = "",
    val nomor_nik: String = "",
    val tanggal_di_makamkan: Timestamp? = null,
    val tanggal_meninggal: Timestamp? = null,
    val tempat_dan_tanggal_lahir: String = "",
    val jenis_kelamin: String = "",
    val nomor_telpon: String = "",
    val wafat: String = "",
    val sebab: String = "",
    val alamat: String = "",
    val email: String = "",
)

data class AdminNotifikasi(
    val status: Boolean = false,
    val statusNotifikasi: Boolean = false,
    val inputData: Boolean = false,
    val fotoKtpPerwakilan: Boolean = false,
    val fotoKtp: Boolean = false,
    val fotoKk: Boolean = false,
    val suratKematian: Boolean = false,

    val urlFotoKtpPerwakilan: String = "",
    val urlFotoKtp: String = "",
    val urlFotoKk: String = "",
    val urlSuratKematian: String = "",
    val id_mayat: String = "",
    val di_wakilkan_oleh: String = "",
    val usia: String = "",
    val nama_mayat: String = "",
    val blok_makam: String = "",
    val id_makam: String = "",
    val meninggal_di: String = "",
    val skm: String = "",
    val nomor_kk: String = "",
    val nomor_nik: String = "",
    val tanggal_pengajuan: Timestamp? = null,
    val tanggal_konfirmasi: Timestamp? = null,
    val tanggal_di_makamkan: Timestamp? = null,
    val tanggal_meninggal: Timestamp? = null,
    val tempat_dan_tanggal_lahir: String = "",
    val jenis_kelamin: String = "",
    val nomor_telpon: String = "",
    val wafat: String = "",
    val sebab: String = "",
    val alamatm: String = "",
    val alamatw: String = "",
    val email: String = "",
    val rtm: String = "",
    val rwm: String = "",
    val rtw: String = "",
    val rww: String = "",
    val kelurahanm : String = "",
    val kecamatanm : String = "",
    val kelurahanw : String = "",
    val kecamatanw : String = "",
    val hubungan : String = "",
    val agama : String = "",
    val kewarganegaraan : String = "",
    val nama_bapak : String = "",
    val nama_ibu : String = "",
    val suami_atau_istri : String = "",
    val anak : String = "",
)

data class BlokMakam(
    val id: String = "-----",
    val nama_blok: String = "-----",
    val maximal: Int = 0,
    val isi: Int = 0
)

data class IdMakam(
    val id: String = "-----",
    val code_makam: String = "-----",
    val status: Boolean = false,
    val nama_blok: String = "-----",
    val namaAlmarhum: String = "-----"
)

data class UserData(
    val username: String,
    val email: String,
    val akses: String,
    val profil: String
)

data class Berita(
    val title: String = "",
    val desc: String = "",
    val img_url: String = ""
)