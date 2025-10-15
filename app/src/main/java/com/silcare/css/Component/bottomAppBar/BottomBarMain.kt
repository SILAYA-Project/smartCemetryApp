package com.silcare.css.Component.bottomAppBar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
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
fun BottomBarMain(
    navController: NavController,
    onDirectionChange: (Int) -> Unit
) {
    var selectedIndex by remember { mutableStateOf(0) }
    val items = listOf("home", "database", "notifikasi")

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .navigationBarsPadding()
            .background(Color.White)
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            items.forEachIndexed { index, route ->
                Icon(
                    painter = painterResource(
                        id = when (route) {
                            "home" -> if (selectedIndex == index) R.drawable.iconhomefill else R.drawable.iconhome
                            "database" -> if (selectedIndex == index) R.drawable.icondatabasefill else R.drawable.icondatabase
                            "notifikasi" -> if (selectedIndex == index) R.drawable.iconnotifikasifill else R.drawable.iconnotifikasi
                            else -> R.drawable.iconhome
                        }
                    ),
                    contentDescription = route,
                    tint = Color(0xFF38008B),
                    modifier = Modifier
                        .size(25.dp)
                        .clickable {
                            if (selectedIndex != index) {
                                val oldIndex = selectedIndex
                                selectedIndex = index
                                onDirectionChange(if (index > oldIndex) 1 else -1)
                                navController.navigate(route) {
                                    launchSingleTop = true
                                }
                            }
                        }
                )
            }
        }
    }
}

@Preview
@Composable
private fun BottomBarMainPrev() {
    BottomBarMain(navController = rememberNavController(), onDirectionChange = {})
}