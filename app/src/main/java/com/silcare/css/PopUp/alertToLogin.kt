package com.silcare.css.PopUp

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun AlertToLogin(onClick: () -> Unit = {},onDismis: () -> Unit = {}) {
    AlertDialog(
        title = {
            Text(text = "Anda Belum Bisa Mengakses Halaman Ini")
        },
        text = {
            Text(text = "Silahkan Login Terlebih dahulu")
        },
        onDismissRequest = {onDismis()},
        confirmButton = {
            TextButton(onClick = { onClick() }) {
                Text(
                    "Login",
                    color = Color(0xFF38008B),
                    fontWeight = FontWeight.Bold
                )
            }
        }
    )
}

@Preview
@Composable
private fun AlertToLoginPrev() {
    AlertToLogin()
}