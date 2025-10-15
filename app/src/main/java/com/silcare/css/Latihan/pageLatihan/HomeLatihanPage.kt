package com.silcare.css.Latihan.pageLatihan

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun HomeLatihanPage(modifier: Modifier = Modifier) {
    Box(
        modifier = Modifier.fillMaxSize(),
        content = {

        }
    )
}

@Preview(showBackground = true)
@Composable
private fun HomeLatihanPagePrev() {
    HomeLatihanPage()
}