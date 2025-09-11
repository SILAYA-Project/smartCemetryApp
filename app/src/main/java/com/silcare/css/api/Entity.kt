package com.silcare.css.api

import java.time.LocalDate
import java.util.Date

data class Mayat(
    val id_mayat: String = "-----",
    val di_wakilkan_oleh: String = "-----",
    val usia: Int = 0,
    val nama_mayat: String = "-----",
    val blok_makam: String = "-----",
    val id_makam: String = "-----",
    val meninggal_di: String = "-----",
    val skm: String = "-----",
    val nomor_kk: Int = 0,
    val nomor_nik: Int = 0,
    val tanggal_di_makamkan: String = "-----",
    val tempat_dan_tanggal_lahir: String = "-----",
    val jenis_kelamin: String = "-----",
    val nomor_telpon: String = "-----",
    val wafat: String = "-----",
    val sebab: String = "-----",
    val alamat: String = "-----",
    val email: String = "-----",
)

data class AdminNotifikasi(
    val status: Boolean = false,
    val inputData: Boolean = false,
    val fotoKtpPerwakilan: Boolean = false,
    val fotoKtp: Boolean = false,
    val fotoKk: Boolean = false,
    val suratKematian: Boolean = false,
    val statusNotifikasi: Boolean = false,

    val id_mayat: String = "",
    val di_wakilkan_oleh: String = "",
    val usia: Int = 0,
    val nama_mayat: String = "",
    val blok_makam: String = "",
    val id_makam: String = "",
    val meninggal_di: String = "",
    val skm: String = "",
    val nomor_kk: Int = 0,
    val nomor_nik: Int = 0,
    val tanggal_di_makamkan: LocalDate? = null,
    val tanggal_meninggal: LocalDate? = null,
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