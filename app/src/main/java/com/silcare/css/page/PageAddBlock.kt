package com.silcare.css.page

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.silcare.css.Component.textFieldCustom.TextFieldCustom
import com.silcare.css.api.MakamViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PageAddBlock(
    viewModel: MakamViewModel,
    onDismiss: () -> Unit
) {
    var namaBlok by remember { mutableStateOf("") }
    var kapasitas by remember { mutableStateOf("") }

    AlertDialog(
        modifier = Modifier,
        title = {
            Text(
                text = "menambahkan Blog pada makam",
                color = Color(0xFF38008B),
            )
        },
        text = {
            Column {
                TextFieldCustom(
                    value = namaBlok,
                    onValueChange = {namaBlok = it},
                    title = "Nama Blog",
                    placeholder = "Masukan Nama Blog",
                    trailingIcon = {},
                    isError = false
                )
                Spacer(modifier = Modifier.size(20.dp))
                TextFieldCustom(
                    value = kapasitas,
                    onValueChange = {kapasitas = it},
                    title = "kapasitas",
                    placeholder = "max kapasitas",
                    trailingIcon = {},
                    isError = false
                )
            }
        },
        shape = RoundedCornerShape(10.dp),
        containerColor = Color.White,
        confirmButton = {
            Button(
                content = {
                    Text(
                        text = "Konfirmasi"
                    )
                },
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF38008B),
                    contentColor = Color.White
                ),
                onClick = {
                    val kapasitasInt = kapasitas.toIntOrNull() ?: 0
                    Log.d( "PageAddBlock kapasitas : ", kapasitas)
                    if (namaBlok.isNotBlank() && kapasitasInt > 0) {
                        viewModel.tambahBlokMakam(namaBlok, kapasitasInt) { success ->
                            if (success) {
                                onDismiss()
                            }
                        }
                    }
                },
            )
        },
        dismissButton = {
            TextButton(
                content = {
                    Text(
                        text = "Batal",
                        color = Color(0xFF38008B)
                    )
                },
                onClick = {
                    onDismiss()
                }
            )
        },
        onDismissRequest = {
            onDismiss()
        },
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
private fun PageAddBlockPrev() {
    PageAddBlock(
        viewModel = MakamViewModel(),
        onDismiss = {}
    )
}