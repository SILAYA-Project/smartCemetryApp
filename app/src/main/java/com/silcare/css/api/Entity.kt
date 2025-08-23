package com.silcare.css.api

import java.util.Date

data class Mayat(
    val waktuMeninggal: String = "-----",
    val namaAlm: String = "-----",
    val jenisKelamin: String = "-----",
    val umur: String = "-----",
    val idMakam: Int = 0,
    val blokMakam: String = "-----",
    val tempatTanggalLahir: String = "-----",
    val alamat: String = "-----",
    val wafat: Date = Date(),
    val meninggalDi: String = "-----",
    val sebab: String = "-----",
    val perwakilan: String = "-----",
    val nik: Int = 0,
    val kk: Int = 0,
    val email: String = "-----",
    val noHp: String = "-----"
)

data class AdminNotifikasi(
    val nama: String = "-----",
    val namaAlmarhum: String = "-----",
    val status: Boolean = false,
    val inputData: Boolean = false,
    val fotoKtpPerwakilan: Boolean = false,
    val fotoKtp: Boolean = false,
    val fotoKk: Boolean = false,
    val suratKematian: Boolean = false,
    val statusNotifikasi: Boolean = false,
)
data class noBlok(
    val no: Int = 0,
    val namaMakam: String = "-----",
    val status: Boolean = false,
    val namaAlm: String = "-----"
)