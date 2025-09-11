package com.silcare.css.page

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.silcare.css.Component.textFieldCustom.TextFieldCustom
import com.silcare.css.R

@Composable
fun LoginPage() {
    Box(
        contentAlignment = Alignment.Center,
        content = {
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = painterResource(R.drawable.bglogin),
                contentDescription = null,
                contentScale = ContentScale.FillBounds
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 30.dp, end = 30.dp),
                content = {
                    Text(
                        text = "Masuk",
                        fontSize = 30.sp,
                        color = Color(0xFF38008B)
                    )
                    Text(
                        text = "Silahkan Masukan Akun Anda",
                        fontSize = 15.sp
                    )
                    Spacer(modifier = Modifier.size(30.dp))
                    TextFieldCustom(
                        value = "",
                        title = "Email",
                        isError = false,
                        placeholder = "Masukan Email Anda",
                        onValueChange = {}
                    )
                    Spacer(modifier = Modifier.size(30.dp))
                    TextFieldCustom(
                        value = "",
                        title = "Password",
                        isError = false,
                        placeholder = "Masukan Password Anda",
                        trailingIcon = {

                        },
                        onValueChange = {}
                    )
                    Spacer(modifier = Modifier.size(10.dp))
                    Row(
                        content = {
                            Text(
                                text = "Jika Belum memiliki Akun?",
                                color = Color(0x54000000),
                                fontSize = 12.sp
                            )
                            Text(
                                text = " Registrasi",
                                color = Color(0xFF002FFF),
                                fontSize = 12.sp
                            )
                        }
                    )
                    Spacer(modifier = Modifier.size(30.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End,
                        content = {
                            Box(
                                modifier = Modifier
                                    .clickable { }
                                    .clip(RoundedCornerShape(5.dp))
                                    .background(Color(0xFF38008B))
                                    .width(180.dp)
                                    .height(48.dp),
                                contentAlignment = Alignment.Center,
                                content = {
                                    Text(
                                        text = "Masuk",
                                        color = colorScheme.onPrimary,
                                        style = typography.bodyLarge,
                                        textAlign = TextAlign.Center
                                    )
                                }
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
private fun LoginPagePrev() {
    LoginPage()
}