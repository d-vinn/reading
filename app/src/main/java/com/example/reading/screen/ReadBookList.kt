package com.example.reading
// ReadBookList.kt
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun ReadBookListScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Title
        Text(
            text = "OO 이가 읽은 책들",
            fontSize = 24.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            color = Color.Black
        )

        // Bookshelf
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            repeat(3) { shelfRow ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                        .background(Color.Black) // 책장 선
                ) {
                    if (shelfRow == 0) {
                        Row(
                            modifier = Modifier
                                .padding(start = 8.dp, top = 8.dp)
                                .height(64.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.Bottom
                        ) {
                            BookButton("책 1")
                            BookButton("책 2")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BookButton(title: String) {
    Button(
        onClick = { /* TODO */ },
        modifier = Modifier
            .width(24.dp)
            .height(64.dp),
        shape = RoundedCornerShape(2.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
    ) {
        // 책 제목 생략하거나 나중에 아이콘이나 이미지로 대체 가능
    }
}
