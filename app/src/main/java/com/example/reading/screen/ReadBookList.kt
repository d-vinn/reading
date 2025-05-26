package com.example.reading

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun ReadBookListScreen(navController: NavController) {
    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                    selected = false,
                    onClick = { navController.navigate("home") }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.LibraryBooks, contentDescription = "Bookshelf") },
                    selected = false,
                    onClick = { navController.navigate("minilib") }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Email, contentDescription = "Notes") },
                    selected = true,
                    onClick = { navController.navigate("notes") }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Settings, contentDescription = "Settings") },
                    selected = false,
                    onClick = { navController.navigate("set") }
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            Text(
                text = "OO 이가 읽은 책들",
                fontSize = 24.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                color = Color.Black
            )

            // 책장이 3단 구성
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(360.dp), // 각 선 높이 120
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                repeat(3) { rowIndex ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp)
                    ) {
                        // 책 Row
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 8.dp)
                                .align(Alignment.BottomCenter),
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            verticalAlignment = Alignment.Bottom
                        ) {
                            BookSpine("책 A")
                            BookSpine("책 B")
                            BookSpine("책 C")
                        }

                        // 책장 선 (나무판)
                        Divider(
                            color = Color(0xFF8B4513), // 갈색
                            thickness = 12.dp,
                            modifier = Modifier.align(Alignment.BottomCenter)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun BookSpine(title: String) {
    Box(
        modifier = Modifier
            .width(40.dp)
            .height(80.dp)
            .background(Color(0xFF37474F), RoundedCornerShape(4.dp)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = title,
            color = Color.White,
            fontSize = 10.sp,
            maxLines = 1
        )
    }
}


@Composable
fun BookButton(title: String) {
    Button(
        onClick = { /* TODO: 책 클릭 처리 */ },
        modifier = Modifier
            .width(80.dp) // 너비 키움
            .height(64.dp), // 높이는 기존 유지
        shape = RoundedCornerShape(2.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray) // 더 보기 쉬운 색
    ) {
        Text(
            text = title,
            color = Color.White,
            fontSize = 12.sp
        )
    }
}

