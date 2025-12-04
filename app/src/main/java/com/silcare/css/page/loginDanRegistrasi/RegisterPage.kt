package com.silcare.css.page.loginDanRegistrasi

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.silcare.css.Component.textFieldCustom.TextFieldCustom
import com.silcare.css.R
import com.silcare.css.api.MakamViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RegisterPage(
    viewModel: MakamViewModel,
    navController: NavController,
) {
    var nama by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    val context = LocalContext.current

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
                        text = "Registrasi Akun Anda",
                        fontSize = 30.sp,
                        color = Color(0xFF38008B)
                    )
                    Text(
                        text = "Buat Akun Pertama Anda",
                        fontSize = 15.sp
                    )
                    Spacer(modifier = Modifier.size(30.dp))
                    TextFieldCustom(
                        value = nama,
                        title = "Nama",
                        isError = false,
                        placeholder = "Masukan Nama Anda",
                        onValueChange = {nama = it}
                    )
                    Spacer(modifier = Modifier.size(30.dp))
                    TextFieldCustom(
                        value = email,
                        title = "Email",
                        isError = false,
                        placeholder = "Masukan Email Anda",
                        onValueChange = {email = it}
                    )
                    Spacer(modifier = Modifier.size(30.dp))
                    TextFieldCustom(
                        value = password,
                        title = "Password",
                        isError = false,
                        usePasswordToggle = true,
                        placeholder = "Masukan Password Anda",
                        trailingIcon = {

                        },
                        onValueChange = {password = it}
                    )
                    Spacer(modifier = Modifier.size(30.dp))
                    TextFieldCustom(
                        value = confirmPassword,
                        title = "Password",
                        isError = false,
                        placeholder = "Masukan Password Anda",
                        trailingIcon = {

                        },
                        onValueChange = {confirmPassword = it}
                    )
                    Spacer(modifier = Modifier.size(10.dp))
                    Row(
                        content = {
                            Text(
                                text = "Sudah memiliki Akun?",
                                color = Color(0x54000000),
                                fontSize = 12.sp
                            )
                            Text(  modifier = Modifier.clickable {
                                navController.popBackStack()
                            },
                                text = " Masuk",
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
                                    .clickable {
                                        viewModel.register(
                                            nama = nama,
                                            email = email,
                                            password = password,
                                            onSuccess = {
                                                navController.popBackStack()
                                            },
                                            onError = { error ->

                                            },
                                            context = context
                                        )
                                    }
                                    .clip(RoundedCornerShape(5.dp))
                                    .background(Color(0xFF38008B))
                                    .width(180.dp)
                                    .height(48.dp),
                                contentAlignment = Alignment.Center,
                                content = {
                                    Text(
                                        text = "Registrasi",
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

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
private fun RegisterPagePrev() {
    RegisterPage(MakamViewModel(), NavController(LocalContext.current))
}