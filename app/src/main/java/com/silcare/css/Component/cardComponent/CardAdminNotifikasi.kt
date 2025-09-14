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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.silcare.css.R
import com.silcare.css.api.AdminNotifikasi

@Composable
fun CardAdminNotifikasi(
    data: AdminNotifikasi,
    onClickKonfirmasi: () -> Unit,
    onClickBatal: () -> Unit,
    onClickDitail: () -> Unit
) {
    Box(
        modifier = Modifier.background(if(!data.statusNotifikasi) Color(0xFFFEF7FF) else Color.White),
        content = {
            Column(
                modifier = Modifier.padding(
                    start = 20.dp,
                    end = 20.dp,
                    bottom = 10.dp,
                    top = 10.dp
                ),
                content = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        content = {
                            Text(
                                text = data.di_wakilkan_oleh,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color(0xFF38008B)
                            )
                            Text(
                                modifier = Modifier.clickable { onClickDitail() },
                                text = "Ditail",
                                color = Color(0xFF0059FF),
                                fontWeight = FontWeight.Light
                            )
                        }
                    )
                    Spacer(modifier = Modifier.size(10.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        content = {
                            Column(
                                content = {
                                    Text(
                                        text = "Nama Almarhum",
                                        fontWeight = FontWeight.Light
                                    )
                                    Spacer(modifier = Modifier.size(5.dp))
                                    Text(
                                        text = data.nama_mayat,
                                    )
                                }
                            )
                        }
                    )
                    Spacer(modifier = Modifier.size(10.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        content = {
                            Column(
                                content = {
                                    Row(
                                        content = {
                                            Row(
                                                verticalAlignment = Alignment.CenterVertically,
                                                content = {
                                                    Text(
                                                        text = "Input data",
                                                        color = Color(0xFF1D1B20),
                                                        fontWeight = FontWeight.Light
                                                    )
                                                    Spacer(modifier = Modifier.size(10.dp))
                                                    Icon(
                                                        modifier = Modifier.size(if (data.inputData) 20.dp else 10.dp),
                                                        painter = if (data.inputData) painterResource(
                                                            R.drawable.sudah
                                                        ) else painterResource(
                                                            R.drawable.belum
                                                        ),
                                                        contentDescription = null,
                                                        tint = if (data.inputData) Color(0xFF35A700) else Color(
                                                            0xFFCC0000
                                                        )
                                                    )
                                                }
                                            )
                                            Spacer(modifier = Modifier.size(10.dp))
                                            Row(
                                                verticalAlignment = Alignment.CenterVertically,
                                                content = {
                                                    Text(
                                                        text = "foto KTP perwakilan",
                                                        color = Color(0xFF1D1B20),
                                                        fontWeight = FontWeight.Light
                                                    )
                                                    Spacer(modifier = Modifier.size(10.dp))
                                                    Icon(
                                                        modifier = Modifier.size(if (data.fotoKtpPerwakilan) 20.dp else 10.dp),
                                                        painter = if (data.fotoKtpPerwakilan) painterResource(
                                                            R.drawable.sudah
                                                        ) else painterResource(R.drawable.belum),
                                                        contentDescription = null,
                                                        tint = if (data.fotoKtpPerwakilan) Color(
                                                            0xFF35A700
                                                        ) else Color(0xFFCC0000)
                                                    )
                                                }
                                            )

                                        }
                                    )

                                    Row(
                                        content = {
                                            Row(
                                                verticalAlignment = Alignment.CenterVertically,
                                                content = {
                                                    Text(
                                                        text = "Foto KTP",
                                                        color = Color(0xFF1D1B20),
                                                        fontWeight = FontWeight.Light
                                                    )
                                                    Spacer(modifier = Modifier.size(10.dp))
                                                    Icon(
                                                        modifier = Modifier.size(if (data.fotoKtp) 20.dp else 10.dp),
                                                        painter = if (data.fotoKtp) painterResource(
                                                            R.drawable.sudah
                                                        ) else painterResource(
                                                            R.drawable.belum
                                                        ),
                                                        contentDescription = null,
                                                        tint = if (data.fotoKtp) Color(0xFF35A700) else Color(
                                                            0xFFCC0000
                                                        )
                                                    )
                                                }
                                            )
                                            Spacer(modifier = Modifier.size(10.dp))
                                            Row(
                                                verticalAlignment = Alignment.CenterVertically,
                                                content = {
                                                    Text(
                                                        text = "Foto KK",
                                                        color = Color(0xFF1D1B20),
                                                        fontWeight = FontWeight.Light
                                                    )
                                                    Spacer(modifier = Modifier.size(10.dp))
                                                    Icon(
                                                        modifier = Modifier.size(if (data.fotoKk) 20.dp else 10.dp),
                                                        painter = if (data.fotoKk) painterResource(R.drawable.sudah) else painterResource(
                                                            R.drawable.belum
                                                        ),
                                                        contentDescription = null,
                                                        tint = if (data.fotoKk) Color(0xFF35A700) else Color(
                                                            0xFFCC0000
                                                        )
                                                    )
                                                }
                                            )
                                        }
                                    )
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        content = {
                                            Text(
                                                text = "Surat kematian",
                                                color = Color(0xFF1D1B20),
                                                fontWeight = FontWeight.Light
                                            )
                                            Spacer(modifier = Modifier.size(10.dp))
                                            Icon(
                                                modifier = Modifier.size(if (data.suratKematian) 20.dp else 10.dp),
                                                painter = if (data.suratKematian) painterResource(R.drawable.sudah) else painterResource(
                                                    R.drawable.belum
                                                ),
                                                contentDescription = null,
                                                tint = if (data.suratKematian) Color(0xFF35A700) else Color(
                                                    0xFFCC0000
                                                )
                                            )
                                        }
                                    )
                                }
                            )
                            Column(
                                content = {
                                    if (data.statusNotifikasi) {
                                        Text(
                                            modifier = Modifier.padding(end = 20.dp),
                                            text = if (data.status) "Di Terima"  else "Di Tolak",
                                            color = if (data.status) Color(0xFF35A700) else Color(0xFFCC0000),
                                            fontWeight = FontWeight.Bold
                                        )
                                    } else {
                                        Box(
                                            modifier = Modifier
                                                .border(
                                                    width = 1.dp,
                                                    color = Color(0xFFCC0000),
                                                    shape = RoundedCornerShape(5.dp)
                                                )
                                                .width(100.dp)
                                                .height(30.dp)
                                                .clickable { onClickBatal() },
                                            contentAlignment = Alignment.Center,
                                            content = {
                                                Text(text = "Tolak", color = Color(0xFFCC0000))
                                            }
                                        )
                                        Spacer(modifier = Modifier.size(5.dp))
                                        Box(
                                            modifier = Modifier
                                                .clip(RoundedCornerShape(5.dp))
                                                .background(Color(0xFF35A700))
                                                .width(100.dp)
                                                .height(30.dp)
                                                .clickable { onClickKonfirmasi() },
                                            contentAlignment = Alignment.Center,
                                            content = {
                                                Text(text = "Konfirmasi", color = Color.White)
                                            }
                                        )
                                    }
                                }
                            )
                        }
                    )
                }
            )
        }
    )
}

@Preview
@Composable
private fun CardAdminNotifikasiPrev() {
    CardAdminNotifikasi(
        data = AdminNotifikasi(statusNotifikasi = true),
        onClickKonfirmasi = {},
        onClickBatal = {},
        onClickDitail = {},
    )
}