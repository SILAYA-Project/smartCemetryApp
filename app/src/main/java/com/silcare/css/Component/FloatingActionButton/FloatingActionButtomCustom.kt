package com.silcare.css.Component.FloatingActionButton

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.silcare.css.R

@Composable
fun FloatingActionButtonCustom(
    onClikAddKuburan: () -> Unit,
    onClikAddJenazah: () -> Unit,
    onClikAddBerita: () -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }
    val rotationAngle by animateFloatAsState(if (expanded) 45f else 0f)

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomEnd,
        content = {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.spacedBy(8.dp),
                content = {
                    AnimatedVisibility(
                        visible = expanded,
                        enter = slideInVertically(
                            initialOffsetY = { it },
                            animationSpec = tween(durationMillis = 300)
                        ),
                        exit = slideOutVertically(
                            targetOffsetY = { it },
                            animationSpec = tween(durationMillis = 300)
                        ),
                        content = {
                            IconButton(
                                onClick = {
                                    onClikAddKuburan()
                                    expanded = false
                                },
                                modifier = Modifier
                                    .clip(RoundedCornerShape(10.dp))
                                    .width(120.dp)
                                    .background(Color(0xffF3EDF7))
                                    .zIndex(if (expanded) 1f else 0f),
                                content = {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceEvenly,
                                        content = {
                                            Icon(
                                                painter = painterResource(id = R.drawable.iconadd),
                                                contentDescription = null,
                                                tint = Color(0xFF38008B),
                                                modifier = Modifier.size(10.dp)
                                            )
                                            Text(text = "Item Kuburan")
                                        }
                                    )
                                }
                            )
                        }
                    )

                    AnimatedVisibility(
                        visible = expanded,
                        enter = slideInVertically(
                            initialOffsetY = { it },
                            animationSpec = tween(durationMillis = 300, delayMillis = 50)
                        ),
                        exit = fadeOut() + shrinkOut(),
                        content = {
                            IconButton(
                                onClick = {
                                    onClikAddJenazah()
                                    expanded = false
                                },
                                modifier = Modifier
                                    .clip(RoundedCornerShape(10.dp))
                                    .width(100.dp)
                                    .background(Color(0xffF3EDF7))
                                    .zIndex(if (expanded) 1f else 0f),
                                content = {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceEvenly,
                                        content = {
                                            Icon(
                                                painter = painterResource(id = R.drawable.iconadd),
                                                contentDescription = null,
                                                tint = Color(0xFF38008B),
                                                modifier = Modifier.size(10.dp)
                                            )
                                            Text(text = "Jenazah")
                                        }
                                    )
                                }
                            )
                        }
                    )

                    AnimatedVisibility(
                        visible = expanded,
                        enter = slideInVertically(
                            initialOffsetY = { it },
                            animationSpec = tween(durationMillis = 300, delayMillis = 50)
                        ),
                        exit = fadeOut() + shrinkOut(),
                        content = {
                            IconButton(
                                onClick = {
                                    onClikAddBerita()
                                    expanded = false
                                },
                                modifier = Modifier
                                    .clip(RoundedCornerShape(10.dp))
                                    .width(90.dp)
                                    .background(Color(0xffF3EDF7))
                                    .zIndex(if (expanded) 1f else 0f),
                                content = {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceEvenly,
                                        content = {
                                            Icon(
                                                painter = painterResource(R.drawable.iconadd),
                                                contentDescription = null,
                                                tint = Color(0xFF38008B),
                                                modifier = Modifier.size(10.dp)
                                            )
                                            Text(text = "Berita")
                                        }
                                    )
                                }
                            )
                        }
                    )

                    FloatingActionButton(
                        onClick = { expanded = !expanded },
                        modifier = Modifier
                            .graphicsLayer(rotationZ = rotationAngle)
                            .zIndex(1f),
                        shape = CircleShape,
                        containerColor = Color(0xFFF3EDF7),
                        contentColor = Color(0xFF38008B),
                        content = {
                            Icon(Icons.Default.Add, contentDescription = "Add")
                        }
                    )
                }
            )
        }
    )
}