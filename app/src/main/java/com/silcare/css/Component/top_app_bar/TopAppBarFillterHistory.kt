package com.silcare.css.Component.top_app_bar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.silcare.css.R

@Composable
fun TopAppBarFillterHistory(onFillter : () -> Unit,onHistory : () -> Unit) {
    Row (
        modifier = Modifier.fillMaxWidth().height(80.dp),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically,
        content = {
            Icon(
                modifier = Modifier.size(25.dp).clickable { onFillter() },
                painter = painterResource(R.drawable.filter),
                contentDescription = null,
                tint = Color(0xFF38008B)
            )
            Spacer(modifier = Modifier.size(20.dp))
//            Icon(
//                modifier = Modifier.size(25.dp).clickable { onHistory() },
//                painter = painterResource(R.drawable.iconhistory),
//                contentDescription = null,
//                tint = Color(0xFF38008B)
//            )
            Spacer(modifier = Modifier.size(20.dp))
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun TopAppBarFillterHistoryPrev() {
    TopAppBarFillterHistory(onFillter = {}, onHistory = {})
}