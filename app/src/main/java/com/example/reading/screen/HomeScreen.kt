package com.example.reading

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LibraryBooks
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.material.Text

@Composable
fun HomeScreen(navController: NavHostController) {
    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                    selected = true,
                    onClick = { navController.navigate("home") }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.LibraryBooks, contentDescription = "Bookshelf") },
                    selected = false,
                    onClick = { navController.navigate("minilib") }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Email, contentDescription = "Notes") },
                    selected = false,
                    onClick = { navController.navigate("notes") }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Settings, contentDescription = "Settings") },
                    selected = false,
                    onClick = { /* settings navigation */ }
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Top character + speech bubble
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.character),
                    contentDescription = "Character",
                    modifier = Modifier.size(64.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Surface(
                    shape = MaterialTheme.shapes.medium,
                    tonalElevation = 2.dp,
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = buildAnnotatedString {
                            withStyle(style = SpanStyle(color = Color.Blue, fontWeight = FontWeight.Bold)) {
                                append("OO")
                            }
                            append("아 안녕!\n나는 너의 책읽기를 도와줄 리딩이야.\n")

                            append("오늘의 ")

                            withStyle(style = SpanStyle(color = Color.Blue, fontWeight = FontWeight.Bold)) {
                                append("인기 있는 책")
                            }

                            append("이나, \n")

                            withStyle(style = SpanStyle(color = Color.Blue, fontWeight = FontWeight.Bold)) {
                                append("너만의 추천도서")
                            }

                            append("를 소개해줄게!")
                        },
                        modifier = Modifier.padding(13.dp),
                        style = MaterialTheme.typography.bodyMedium.copy(fontSize = 16.sp) // 필요시 전체 크기 조정
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "오늘의 베스트 도서",
                fontSize = 24.sp,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(16.dp))

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(7) { index ->
                    Box(
                        modifier = Modifier
                            .size(width = 120.dp, height = 160.dp)
                            .background(MaterialTheme.colorScheme.secondaryContainer)
                            .clickable { navController.navigate("BookContents") }
                    ) {
                        Text(
                            text = "책 ${index + 1}",
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            TextButton(
                onClick = { navController.navigate("TodayRec") },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text("오늘의 추천도서 바로가기 >",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF073042)
                    )
            }
        }
    }
}