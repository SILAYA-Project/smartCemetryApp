package com.silcare.css.page.loginDanRegistrasi

import android.content.Intent
import android.os.Build
import android.widget.Toast
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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.silcare.css.Component.textFieldCustom.TextFieldCustom
import com.silcare.css.MainActivity
import com.silcare.css.api.*
import com.silcare.css.R

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun LoginPage(navController: NavController , viewModel: MakamViewModel,) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current
    var errorMessage by remember { mutableStateOf<String?>(null) }
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
                        placeholder = "Masukan Password Anda",
                        trailingIcon = {

                        },
                        onValueChange = {password = it},
                        usePasswordToggle = true
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
                                modifier = Modifier.clickable {
                                    navController.navigate("registrasi")
                                },
                                text = " Registrasi",
                                color = Color(0xFF203086),
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
                                        viewModel.login(
                                            email = email,
                                            password = password,
                                            onSuccess = {
                                                val intent = Intent(context, MainActivity::class.java)
                                                context.startActivity(intent)
                                            },
                                            onError = { msg ->
                                                errorMessage = msg
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
            if (errorMessage != null) {
                AlertDialog(
                    onDismissRequest = { errorMessage = null },
                    confirmButton = {
                        TextButton(onClick = { errorMessage = null }) {
                            Text("OK")
                        }
                    },
                    title = { Text("Login Gagal") },
                    text = { Text(errorMessage ?: "") }
                )
            }
        }
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
private fun LoginPagePrev() {
    LoginPage(navController = rememberNavController(), viewModel = viewModel())
}