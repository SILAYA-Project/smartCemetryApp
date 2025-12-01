package com.silcare.css.Component.textFieldCustom

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.firebase.Timestamp
import com.silcare.css.api.toTimestamp
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalDateTime

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DateTimePickerBox(
    modifier: Modifier = Modifier,
    title: String,
    placeholder: String = "-----------",
    onDateTimeSelected: (Timestamp) -> Unit
) {
    var selectedDateTime by remember { mutableStateOf<LocalDateTime?>(null) }
    val dateDialogState = rememberMaterialDialogState()
    val timeDialogState = rememberMaterialDialogState()

    Box(
        modifier = modifier.clickable {
            dateDialogState.show()
        },
        contentAlignment = Alignment.CenterStart
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .border(1.dp, Color(0x6138008b), RoundedCornerShape(5.dp))
                .padding(20.dp),
            text = selectedDateTime?.toString() ?: placeholder,
            color = if (selectedDateTime == null) Color.Gray else Color.Black,
            style = MaterialTheme.typography.bodyMedium
        )

        CompCustomTextField(
            title = title,
            modifier = Modifier.offset(x = 10.dp, y = (-25).dp),
            backgroundColor = Color.White,
            textColor = Color(0xEEDDFFFF)
        )
    }

    MaterialDialog(
        dialogState = dateDialogState,
        buttons = {
            positiveButton("OK") {
                timeDialogState.show() // setelah tanggal, lanjut pilih jam
            }
            negativeButton("Batal")
        }
    ) {
        datepicker { date ->
            selectedDateTime = LocalDateTime.of(date, LocalDateTime.now().toLocalTime())
        }
    }

    MaterialDialog(
        dialogState = timeDialogState,
        buttons = {
            positiveButton("OK") { onDateTimeSelected(selectedDateTime.toTimestamp()!!) }
            negativeButton("Batal")
        }
    ) {
        timepicker { time ->
            selectedDateTime = selectedDateTime?.withHour(time.hour)?.withMinute(time.minute)
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
private fun DatePickerPreview() {
    MaterialTheme {
        DateTimePickerBox(
            title = "Tanggal Lahir",
            onDateTimeSelected = {}
        )
    }
}