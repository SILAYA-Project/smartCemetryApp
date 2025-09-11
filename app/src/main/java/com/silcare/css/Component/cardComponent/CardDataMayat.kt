package com.silcare.css.Component.cardComponent

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.silcare.css.api.Mayat

@Composable
fun CardDataMayat(
    data: Mayat
) {
    var expanded by remember { mutableStateOf(false) }
    Box(
        modifier = Modifier
            .border(width = 1.dp, shape = RoundedCornerShape(10.dp), color = Color(0xFFEEDDFF))
            .background(Color.White)
            .clickable { expanded = !expanded },
        content = {
            Column(
                modifier = Modifier.padding(15.dp),
                content = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        content = {
                            Text(
                                text = data.nama_mayat,
                                fontSize = 15.sp,
                                color = Color(0xFF38008B),
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = data.jenis_kelamin,
                                fontSize = 13.sp,
                            )
                        }
                    )
                    Spacer(modifier = Modifier.size(10.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        content = {
                            Text(
                                text = data.tanggal_di_makamkan,
                                fontSize = 13.sp,
                            )
                            Text(
                                text = "${data.usia} Tahun",
                                fontSize = 13.sp,
                            )
                        }
                    )
                    if (expanded) {
                        Spacer(modifier = Modifier.size(10.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            content = {
                                Row {
                                    Text(
                                        text = "Blok Makam    ",
                                        color = Color(0xFF7C7C7C),
                                        fontSize = 13.sp
                                    )
                                    Text(text = data.blok_makam, fontSize = 13.sp)
                                }
                                Row {
                                    Text(
                                        text = "ID Makam    ",
                                        color = Color(0xFF7C7C7C),
                                        fontSize = 13.sp
                                    )
                                    Text(
//                                        text = if (data.idMakam != 0) data.idMakam.toString() else "-----",
                                        text = data.id_makam,
                                        fontSize = 13.sp
                                    )
                                }
                            }
                        )
                        Spacer(modifier = Modifier.size(10.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            content = {
                                Row {
                                    Text(
                                        text = "Meninggal Di    ",
                                        color = Color(0xFF7C7C7C),
                                        fontSize = 13.sp
                                    )
                                    Text(text = data.meninggal_di, fontSize = 13.sp)
                                }
                                Row {
                                    Text(
                                        text = "Sebab    ",
                                        color = Color(0xFF7C7C7C),
                                        fontSize = 13.sp
                                    )
                                    Text(
                                        text = data.sebab,
                                        fontSize = 13.sp
                                    )
                                }
                            }
                        )
                        Spacer(modifier = Modifier.size(10.dp))
                        Row {
                            Text(
                                text = "Wafat    ",
                                color = Color(0xFF7C7C7C),
                                fontSize = 13.sp
                            )
                            Text(text = data.wafat.toString(), fontSize = 13.sp)
                        }
                        Spacer(modifier = Modifier.size(10.dp))
                        Row {
                            Text(
                                text = "Tanggal dan Tempat Lahir    ",
                                color = Color(0xFF7C7C7C),
                                fontSize = 13.sp
                            )
                            Text(text = data.tempat_dan_tanggal_lahir, fontSize = 13.sp)
                        }
                        Spacer(modifier = Modifier.size(10.dp))
                        Row {
                            Text(
                                text = "Alamat    ",
                                color = Color(0xFF7C7C7C),
                                fontSize = 13.sp
                            )
                            Text(text = data.alamat, fontSize = 13.sp)
                        }
                        Spacer(modifier = Modifier.size(10.dp))
                        Divider(color = Color(0xFFEEDDFF))
                        Spacer(modifier = Modifier.size(10.dp))
                        Row {
                            Text(
                                text = "Di Wakilkan Oleh    ",
                                color = Color(0xFF7C7C7C),
                                fontSize = 13.sp,
                            )
                            Text(text = data.di_wakilkan_oleh, fontSize = 13.sp,fontWeight = FontWeight.Bold)
                        }
                        Spacer(modifier = Modifier.size(10.dp))
                        Row {
                            Text(
                                text = "NIK    ",
                                color = Color(0xFF7C7C7C),
                                fontSize = 13.sp
                            )
                            Text(text = if (data.nomor_nik != 0) data.nomor_nik.toString() else "-----", fontSize = 13.sp)
                        }
                        Spacer(modifier = Modifier.size(10.dp))
                        Row {
                            Text(
                                text = "Nomor KK    ",
                                color = Color(0xFF7C7C7C),
                                fontSize = 13.sp
                            )
                            Text(text = if (data.nomor_kk != 0) data.nomor_kk.toString() else "-----", fontSize = 13.sp)
                        }
                        Spacer(modifier = Modifier.size(10.dp))
                        Row {
                            Text(
                                text = "Email    ",
                                color = Color(0xFF7C7C7C),
                                fontSize = 13.sp
                            )
                            Text(text = data.email, fontSize = 13.sp)
                        }
                        Spacer(modifier = Modifier.size(10.dp))
                        Row {
                            Text(
                                text = "Nomor Telpom    ",
                                color = Color(0xFF7C7C7C),
                                fontSize = 13.sp
                            )
                            Text(text = data.nomor_telpon, fontSize = 13.sp)
                        }
                    }
                }
            )
        }
    )
}

@Preview
@Composable
private fun CardDataMayatPrev() {
    CardDataMayat(
        data = Mayat(
            nama_mayat = "MS ALAMSYAH CHATIB BASA",
            usia = 65,
            tanggal_di_makamkan = "Rabu, 01/01/2003, 17:00",
            jenis_kelamin = "Laki-Laki"
        )
    )
}