package com.silcare.css.Component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.silcare.css.R

@Composable
fun TopSearchBar(
    value: String,
    onValueChange: (String) -> Unit,
) {
    val colorScheme = MaterialTheme.colorScheme
    val focusManager = LocalFocusManager.current

    Row(
        modifier = Modifier
            .padding(start = 20.dp, end = 20.dp, top = 20.dp, bottom = 15.dp)
            .shadow(4.dp, shape = CircleShape, clip = false)
            .clip(CircleShape)
            .height(54.dp)
            .fillMaxWidth()
            .background(colorScheme.surface)
            .border(
                width = 1.dp,
                color = colorScheme.outline.copy(alpha = 0.2f),
                shape = CircleShape
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = {
                Text(
                    text = "Search...",
                    color = colorScheme.onSurface.copy(alpha = 0.4f)
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 20.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                disabledContainerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                cursorColor = colorScheme.primary,
                unfocusedTextColor = colorScheme.onSurface,
                focusedTextColor = colorScheme.onSurface
            ),
            maxLines = 1,
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                }
            ),
            trailingIcon = {
                Row(
                    content = {
                        IconButton(
                            onClick = {
                                focusManager.clearFocus()
                            },
                            content = {
                                Icon(
                                    painter = painterResource(id = R.drawable.iconsearch),
                                    contentDescription = null,
                                    modifier = Modifier.size(15.dp),
                                    tint = Color(0xffb889ff)
                                )
                            }
                        )
                        IconButton(
                            onClick = {
                                focusManager.clearFocus()
                            },
                            content = {
                                Icon(
                                    painter = painterResource(id = R.drawable.filter),
                                    contentDescription = null,
                                    modifier = Modifier.size(15.dp),
                                    tint = Color(0xffb889ff)
                                )
                            }
                        )
                    }
                )
            }
        )
    }
}


@Preview(showBackground = true)
@Composable
private fun TopSearchBarPrev() {
    var value by remember { mutableStateOf("") }
    Box(
        modifier = Modifier.fillMaxSize(),
        content = {
            TopSearchBar(value = value, onValueChange = { value = it })
        }
    )
}