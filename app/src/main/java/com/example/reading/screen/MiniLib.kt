package com.example.reading

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LibraryBooks
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MiniLibScreen(navController: NavController) {
    // 책장 목록을 저장하는 상태
    val defaultBookShelves = listOf("간단한 책이 담긴 책장", "잔잔한 책이 담긴 책장")
    var bookShelves by remember { mutableStateOf(defaultBookShelves) }

    // 새 책장 추가 다이얼로그 표시 상태
    var showDialog by remember { mutableStateOf(false) }

    // 새 책장 이름 입력 상태
    var newBookshelfName by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "OO이의 미니도서관",
                        fontWeight = FontWeight.Bold
                    )
                }
            )
        },
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                    selected = false,
                    onClick = { navController.navigate("home") }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.LibraryBooks, contentDescription = "Bookshelf") },
                    selected = true,
                    onClick = { /* 현재 화면 */ }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Email, contentDescription = "Notes") },
                    selected = false,
                    onClick = { /* notes navigation */ }
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
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // 책장 목록
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // 새 책장 추가 버튼
                item {
                    OutlinedButton(
                        onClick = { showDialog = true },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "Add"
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "+ 새 책장",
                                fontSize = 16.sp,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }

                // 기존 책장 버튼들
                items(bookShelves) { bookshelf ->
                    Button(
                        onClick = {
                            // URL 인코딩을 사용하여 책장 이름을 안전하게 전달
                            val encodedName = URLEncoder.encode(bookshelf, StandardCharsets.UTF_8.toString())
                            navController.navigate("bookCase/$encodedName")
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Book,
                                contentDescription = "Bookshelf"
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = bookshelf,
                                fontSize = 16.sp,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }

                // 하단 여백 추가
                item {
                    Spacer(modifier = Modifier.height(50.dp))
                }
            }
        }

        // 새 책장 추가 다이얼로그
        if (showDialog) {
            AlertDialog(
                onDismissRequest = {
                    showDialog = false
                    newBookshelfName = ""
                },
                title = { Text("새 책장 만들기") },
                text = {
                    Column {
                        Text("새 책장의 이름을 입력해주세요.")
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(
                            value = newBookshelfName,
                            onValueChange = { newBookshelfName = it },
                            label = { Text("책장 이름") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            if (newBookshelfName.isNotBlank()) {
                                bookShelves = bookShelves + "$newBookshelfName 책이 담긴 책장"
                                newBookshelfName = ""
                                showDialog = false
                            }
                        }
                    ) {
                        Text("확인")
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = {
                            showDialog = false
                            newBookshelfName = ""
                        }
                    ) {
                        Text("취소")
                    }
                }
            )
        }
    }
}