package com.silcare.css.Component.bottomAppBar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.silcare.css.R

@Composable
fun BottomBarMain(navController: NavController) {
    var selectedIndex by remember { mutableStateOf(0) }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = if (selectedIndex == 0) painterResource(R.drawable.iconhomefill)
                else painterResource(R.drawable.iconhome),
                contentDescription = "Home",
                tint = Color(0xFF38008B),
                modifier = Modifier
                    .size(25.dp)
                    .clickable {
                        selectedIndex = 0
                        navController.navigate("Home")
                    }
            )

            Icon(
                painter = if (selectedIndex == 1) painterResource(R.drawable.icondatabasefill)
                else painterResource(R.drawable.icondatabase),
                contentDescription = "Database",
                tint = Color(0xFF38008B),
                modifier = Modifier
                    .size(25.dp)
                    .clickable {
                        selectedIndex = 1
                        navController.navigate("Database")
                    }
            )

            Icon(
                painter = if (selectedIndex == 2) painterResource(R.drawable.iconnotifikasifill)
                else painterResource(R.drawable.iconnotifikasi),
                contentDescription = "Notifikasi",
                tint = Color(0xFF38008B),
                modifier = Modifier
                    .size(25.dp)
                    .clickable {
                        selectedIndex = 2
                        navController.navigate("Notifikasi")
                    }
            )

            Icon(
                painter = if (selectedIndex == 3) painterResource(R.drawable.iconakunfill) else painterResource(
                    R.drawable.iconakun
                ),
                contentDescription = "Akun",
                tint = Color(0xFF38008B),
                modifier = Modifier
                    .size(25.dp)
                    .clickable {
                        selectedIndex = 3
                        navController.navigate("Akun")
                    }
            )
        }
    }
}

@Preview
@Composable
private fun BottomBarMainPrev() {
    BottomBarMain(navController = rememberNavController())
}