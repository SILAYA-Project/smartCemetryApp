package com.silcare.css.PopUp

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PopUpKonfirmasi(title: String = "",descripsion: String = "",onClickSucceed: () -> Unit,onClikeCencel: () -> Unit) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .background(Color.White)
            .width(300.dp)
            .border(width = 1.dp, color = Color(0xFFEEDDFF), shape = RoundedCornerShape(10.dp)),
        contentAlignment = Alignment.Center,
        content = {
            Column (
                modifier = Modifier.padding(25.dp),
                content = {
                    Text(
                        text = title,
                        fontSize = 25.sp,
                        color = Color(0xFFD00000)
                    )
                    Spacer(modifier = Modifier.height(15.dp))
                    Text(
                        text = descripsion,
                        fontSize = 15.sp
                    )
                    Spacer(modifier = Modifier.height(15.dp))
                    Row (
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End,
                        content = {
                            Button(
                                modifier = Modifier.width(100.dp),
                                onClick = onClikeCencel,
                                content = {
                                    Text(
                                        text = "Tidak"
                                    )
                                },
                                shape = RoundedCornerShape(10.dp),
                                colors = ButtonDefaults.buttonColors(
                                    Color(0xFFB889FF)
                                )
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Button(
                                modifier = Modifier.width(100.dp),
                                onClick = onClickSucceed,
                                content = {
                                    Text(
                                        text = "Ya",
                                        color = Color(0xFFFF0000)
                                    )
                                },
                                border = BorderStroke(1.dp, Color(0xFFFF0000)),
                                shape = RoundedCornerShape(10.dp),
                                colors = ButtonDefaults.buttonColors(
                                    Color.Transparent
                                )
                            )
                        }
                    )
                }
            )
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun PopUpKonfirmasiPrev() {
    Box(
        modifier = Modifier.fillMaxSize().background(Color.Black.copy(alpha = 0.4f)),
        contentAlignment = Alignment.Center,
        content = {
            PopUpKonfirmasi(
                title = "Perhatian",
                descripsion = "Kamu Yakin? Data Yang mau di konfimasi masih kurang lengkap!",
                onClickSucceed = {},
                onClikeCencel = {}
            )
        }
    )
}