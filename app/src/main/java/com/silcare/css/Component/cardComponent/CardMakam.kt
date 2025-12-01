package com.silcare.css.Component.cardComponent

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.silcare.css.R
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer
import com.valentinilk.shimmer.shimmer

@Composable
fun CardMakam(namaMakam: String, terisi: Int, max: Int,onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .clickable { onClick() }
            .fillMaxWidth()
            .height(80.dp)
            .border(
                width = 1.dp,
                color = Color(0x3238008b),
                shape = RoundedCornerShape(10.dp)
            ),
        content = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .background(Color.White)
                    .padding(start = 20.dp, end = 10.dp, top = 10.dp, bottom = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                content = {
                    Column(
                        modifier = Modifier.fillMaxHeight(),
                        verticalArrangement = Arrangement.Center,
                        content = {
                            Text(text = namaMakam, color = Color(0xFF38008B), fontSize = 15.sp, fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.height(5.dp))
                            Text(text = "$terisi / $max", fontSize = 13.sp)
                        }
                    )
                    Box(
                        modifier = Modifier
                            .clip(shape = RoundedCornerShape(10.dp))
                            .background(Color(0xFF38008B))
                            .size(60.dp),
                        contentAlignment = Alignment.Center,
                        content = {
                            Icon(
                                modifier = Modifier.size(15.dp),
                                painter = painterResource(R.drawable.petaicon),
                                contentDescription = null,
                                tint = Color(0xFFFEF7FF)
                            )
                        }
                    )
                }
            )
        }
    )
}

@Composable
fun CardMakamShimmer() {
    val shimmer = rememberShimmer(shimmerBounds = ShimmerBounds.View)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .border(
                width = 1.dp,
                color = Color(0x3238008b),
                shape = RoundedCornerShape(10.dp)
            )
            .shimmer(shimmer),
        content = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .background(Color.White)
                    .padding(20.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                content = {
                    Column(
                        modifier = Modifier.fillMaxHeight(),
                        verticalArrangement = Arrangement.Center,
                        content = {
                            Box(
                                modifier = Modifier
                                    .width(100.dp)
                                    .height(20.dp)
                                    .clip(RoundedCornerShape(5.dp))
                                    .background(Color.Gray)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Box(
                                modifier = Modifier
                                    .width(60.dp)
                                    .height(15.dp)
                                    .clip(RoundedCornerShape(5.dp))
                                    .background(Color.Gray)
                            )
                        }
                    )
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .background(Color.Gray)
                            .size(50.dp)
                    )
                }
            )
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun CardMakamPrev() {
    CardMakam(
        namaMakam = "Mawar",
        terisi = 34,
        max = 105,
        onClick = {}
    )
}