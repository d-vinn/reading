package com.example.reading

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.withStyle
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MiniLibScreen(navController: NavController, viewModel: UserRecommendationProfileViewModel) {
    // 책장 목록을 저장하는 상태
    val defaultBookShelves = listOf("간단한 책이 담긴 책장", "잔잔한 책이 담긴 책장")
    val kidFont = FontFamily(Font(R.font.uhbee_puding))
    val selectedBgColor = Color(0xFFB9D99A) // #B9D99A
    val unselectedColor = Color.Gray
    val userName by viewModel.userName.collectAsState()


    var selectedIndex by remember { mutableStateOf(1) }
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
                        text = buildAnnotatedString {
                            withStyle(
                                style = SpanStyle(
                                    color = colorResource(id = R.color.light_green),
                                    fontWeight = FontWeight.Bold
                                )
                            ) {
                                // ViewModel에서 가져온 사용자 이름을 표시하거나, 이름이 없을 경우 기본값("사용자")을 표시
                                append(userName ?: "사용자") // <-- 'OO' 대신 userName 변수 사용
                            }
                            append("이의 미니 도서관📖.\n")
                        }
                    )
                }
            )
        },
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 10.dp)
                    .shadow(12.dp, RoundedCornerShape(24.dp))
                    .clip(RoundedCornerShape(24.dp))
                    .background(Color.White)
            ) {
                NavigationBar(
                    containerColor = Color.Transparent,
                    tonalElevation = 0.dp,
                    modifier = Modifier.height(70.dp)
                ) {
                    NavigationBarItem(
                        selected = selectedIndex == 0,
                        onClick = { selectedIndex = 0
                                  navController.navigate("home")},
                        icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                        label = { Text("home", fontSize = 11.sp) },
                        alwaysShowLabel = true,
                        colors = NavigationBarItemDefaults.colors(
                            indicatorColor = if (selectedIndex == 0) selectedBgColor else Color.Transparent,
                            selectedIconColor = Color.White,
                            selectedTextColor = Color.Black,
                            unselectedIconColor = unselectedColor,
                            unselectedTextColor = unselectedColor
                        )
                    )
                    NavigationBarItem(
                        selected = selectedIndex == 1,
                        onClick = {
                            selectedIndex = 1
                            navController.navigate("minilib")
                        },
                        icon = { Icon(Icons.Default.LibraryBooks, contentDescription = "Library") },
                        label = { Text("library", fontSize = 11.sp) },
                        alwaysShowLabel = true,
                        colors = NavigationBarItemDefaults.colors(
                            indicatorColor = if (selectedIndex == 1) selectedBgColor else Color.Transparent,
                            selectedIconColor = Color.White,
                            selectedTextColor = Color.Black,
                            unselectedIconColor = unselectedColor,
                            unselectedTextColor = unselectedColor
                        )
                    )
                    NavigationBarItem(
                        selected = selectedIndex == 2,
                        onClick = {
                            selectedIndex = 2
                            navController.navigate("notes")
                        },
                        icon = { Icon(Icons.Default.Email, contentDescription = "Books") },
                        label = { Text("books", fontSize = 11.sp) },
                        alwaysShowLabel = true,
                        colors = NavigationBarItemDefaults.colors(
                            indicatorColor = if (selectedIndex == 2) selectedBgColor else Color.Transparent,
                            selectedIconColor = Color.White,
                            selectedTextColor = Color.Black,
                            unselectedIconColor = unselectedColor,
                            unselectedTextColor = unselectedColor
                        )
                    )
                    NavigationBarItem(
                        selected = selectedIndex == 3,
                        onClick = {
                            selectedIndex = 3
                            navController.navigate("set")},
                        icon = { Icon(Icons.Default.Settings, contentDescription = "Setting") },
                        label = { Text("setting", fontSize = 11.sp) },
                        alwaysShowLabel = true,
                        colors = NavigationBarItemDefaults.colors(
                            indicatorColor = if (selectedIndex == 3) selectedBgColor else Color.Transparent,
                            selectedIconColor = Color.White,
                            selectedTextColor = Color.Black,
                            unselectedIconColor = unselectedColor,
                            unselectedTextColor = unselectedColor
                        )
                    )
                }
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