package com.silcare.css.Component.textFieldCustom

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextFieldDropDown(
    title: String,
    placeholder: String = "-----------"
) {
    var kecamatan by remember { mutableStateOf("") }
    var expandedKecamatan by remember { mutableStateOf(false) }
    val kecamatanList = listOf("Batam Kota", "Bengkong", "Sekupang", "Lubuk Baja", "Sei Beduk")

    ExposedDropdownMenuBox(
//        modifier = Modifier.border(width = 1.dp, shape = RoundedCornerShape(10.dp), color = Color(0xFFEEDDFF)),
        expanded = expandedKecamatan,
        onExpandedChange = { expandedKecamatan = !expandedKecamatan },
        content = {
            Box(
                contentAlignment = Alignment.CenterStart,
                content = {
                    TextField(
                        value = kecamatan,
                        onValueChange = {},
                        readOnly = true,
                        placeholder = { Text(placeholder) },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedKecamatan) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor()
                            .clip(RoundedCornerShape(10.dp))
                            .border(
                                width = 1.dp,
                                color = Color(0xFFEEDDFF),
                                shape = RoundedCornerShape(10.dp)
                            ),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color(0xffffffff),
                            unfocusedContainerColor = Color(0xffffffff),
                            disabledContainerColor = Color(0xffffffff),
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        )
                    )
                    ExposedDropdownMenu(
                        expanded = expandedKecamatan,
                        onDismissRequest = { expandedKecamatan = false }
                    ) {
                        kecamatanList.forEach { item ->
                            DropdownMenuItem(
                                text = { Text(item) },
                                onClick = {
                                    kecamatan = item
                                    expandedKecamatan = false
                                }
                            )
                        }
                    }
                    CompCustomTextField(
                        title = title,
                        modifier = Modifier.offset(x = 10.dp, y = (-25).dp),
                        backgroundColor = Color.White,
                        textColor = Color(0xEEDDFFFF)
                    )
                }
            )
        }
    )
}


@Preview
@Composable
private fun DataUserPreview() {
    MaterialTheme {
        TextFieldDropDown(
            title = "Kecamatan"
        )
    }
}