package com.silcare.css.Component.textFieldCustom

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalDate

@Composable
fun DatePickerBox(
    modifier: Modifier = Modifier,
    title: String,
    placeholder: String = "-----------",
    onDateSelected: (LocalDate) -> Unit
) {
    var selectedDate by remember { mutableStateOf<LocalDate?>(null) }
    val dialogState = rememberMaterialDialogState()

    Box(
        modifier = modifier
            .clickable {
                Log.d("DatePicker", "Box clicked")
                dialogState.show()
            },
        contentAlignment = Alignment.CenterStart
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .border(1.dp, Color(0xFFEEDDFF), RoundedCornerShape(5.dp))
                .padding(20.dp),
            text = selectedDate?.toString() ?: placeholder,
            color = if (selectedDate == null) Color.Gray else Color.Black,
            style = MaterialTheme.typography.bodyMedium
        )

        CompCustomTextField(
            title = title,
            modifier = Modifier
                .offset(x = 10.dp, y = (-25).dp),
            backgroundColor = Color.White,
            textColor = Color(0xEEDDFFFF)
        )
    }

    MaterialDialog(
        dialogState = dialogState,
        backgroundColor = Color.White,
        buttons = {
            positiveButton("OK")
            negativeButton("Batal")
        }
    ) {
        datepicker { newDate ->
            selectedDate = newDate
            onDateSelected(newDate)
        }
    }
}
@Preview
@Composable
private fun DatePickerPreview() {
    MaterialTheme {
        DatePickerBox(
            title = "Tanggal Lahir",
            onDateSelected = {}
        )
    }
}