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
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.runtime.*
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape


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
                    onClick = { "set" }
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

//@Composable
//fun HomeScreen(navController: NavHostController) {
//    val tabs = listOf("베스트", "추천")
//    var selectedTabIndex by remember { mutableStateOf(0) }
//
//    // 말풍선 내용 조건에 따라 변경
//    val speechText = when (selectedTabIndex) {
//        0 -> buildAnnotatedString {
//            withStyle(style = SpanStyle(color = Color.Blue, fontWeight = FontWeight.Bold)) {
//                append("OO")
//            }
//            append("아 안녕!\n나는 너의 책읽기를 도와줄 리딩이야.\n오늘의 ")
//
//            withStyle(style = SpanStyle(color = Color.Blue, fontWeight = FontWeight.Bold)) {
//                append("인기 있는 책")
//            }
//
//            append("을 소개해줄게!")
//        }
//
//        1 -> buildAnnotatedString {
//            withStyle(style = SpanStyle(color = Color.Blue, fontWeight = FontWeight.Bold)) {
//                append("OO")
//            }
//            append("아 안녕!\n나는 너의 책읽기를 도와줄 리딩이야.\n")
//
//            withStyle(style = SpanStyle(color = Color.Blue, fontWeight = FontWeight.Bold)) {
//                append("너만의 추천 책")
//            }
//
//            append("을 소개해줄게!")
//        }
//
//        else -> AnnotatedString("")
//    }
//
//    Scaffold(
//        bottomBar = {
//            NavigationBar {
//                NavigationBarItem(
//                    icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
//                    selected = true,
//                    onClick = { navController.navigate("home") }
//                )
//                NavigationBarItem(
//                    icon = { Icon(Icons.Default.LibraryBooks, contentDescription = "Bookshelf") },
//                    selected = false,
//                    onClick = { navController.navigate("minilib") }
//                )
//                NavigationBarItem(
//                    icon = { Icon(Icons.Default.Email, contentDescription = "Notes") },
//                    selected = false,
//                    onClick = { navController.navigate("notes") }
//                )
//                NavigationBarItem(
//                    icon = { Icon(Icons.Default.Settings, contentDescription = "Settings") },
//                    selected = false,
//                    onClick = { navController.navigate("set") }
//                )
//            }
//        }
//    ) { innerPadding ->
//        Column(
//            modifier = Modifier
//                .padding(innerPadding)
//                .fillMaxSize()
//                .padding(16.dp)
//        ) {
//            // 캐릭터 + 말풍선 (텍스트만 조건에 따라 변경됨)
//            Row(verticalAlignment = Alignment.CenterVertically) {
//                Image(
//                    painter = painterResource(id = R.drawable.character),
//                    contentDescription = "Character",
//                    modifier = Modifier.size(64.dp)
//                )
//                Spacer(modifier = Modifier.width(8.dp))
//                Surface(
//                    shape = MaterialTheme.shapes.medium,
//                    tonalElevation = 2.dp,
//                    modifier = Modifier.weight(1f)
//                ) {
//                    Text(
//                        text = speechText,
//                        modifier = Modifier.padding(13.dp),
//                        style = MaterialTheme.typography.bodyMedium.copy(fontSize = 16.sp)
//                    )
//                }
//            }
//
//            Spacer(modifier = Modifier.height(16.dp))
//
//            // 탭 바
//            TabRow(
//                selectedTabIndex = selectedTabIndex,
//                modifier = Modifier.fillMaxWidth()
//            ) {
//                tabs.forEachIndexed { index, title ->
//                    Tab(
//                        text = { Text(title) },
//                        selected = selectedTabIndex == index,
//                        onClick = { selectedTabIndex = index }
//                    )
//                }
//            }
//
//            Spacer(modifier = Modifier.height(16.dp))
//
//            // 콘텐츠 변경
//            when (selectedTabIndex) {
//                0 -> BestBooksSection(navController)
//                1 -> RecommendedBooksSection(navController)
//            }
//        }
//    }
//}
//
//@Composable
//fun BestBooksSection(navController: NavHostController) {
//    Column(
//        modifier = Modifier.fillMaxWidth(),
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Text(
//            text = "오늘의 베스트 도서",
//            fontSize = 24.sp,
//            style = MaterialTheme.typography.titleMedium
//        )
//    }
//
//    Spacer(modifier = Modifier.height(16.dp))
//
//    LazyRow(
//        horizontalArrangement = Arrangement.spacedBy(12.dp),
//        modifier = Modifier.fillMaxWidth()
//    ) {
//        items(7) { index ->
//            Box(
//                modifier = Modifier
//                    .size(width = 120.dp, height = 160.dp)
//                    .background(MaterialTheme.colorScheme.secondaryContainer)
//                    .clickable { navController.navigate("BookContents") }
//            ) {
//                Text(
//                    text = "책 ${index + 1}",
//                    modifier = Modifier.align(Alignment.Center)
//                )
//            }
//        }
//    }
//}
//
//@Composable
//fun RecommendedBooksSection(navController: NavHostController) {
//    val books = listOf("추천 1", "추천 2", "추천 3", "추천 4", "추천 5")
//    val listState = rememberLazyListState()
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(24.dp),
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Text(
//            text = "OO이를 위한",
//            fontSize = 28.sp,
//            fontWeight = FontWeight.Bold
//        )
//        Text(
//            text = "오늘의 추천도서",
//            fontSize = 20.sp,
//            color = Color.Gray
//        )
//
//        Spacer(modifier = Modifier.height(24.dp))
//
//        LazyRow(
//            state = listState,
//            modifier = Modifier.fillMaxWidth(),
//            horizontalArrangement = Arrangement.spacedBy(16.dp)
//        ) {
//            items(books.size) { index ->
//                val center = listState.firstVisibleItemIndex + 1
//                val isCenter = index == center
//
//                Box(
//                    modifier = Modifier
//                        .width(if (isCenter) 160.dp else 120.dp)
//                        .height(if (isCenter) 226.dp else 170.dp)
//                        .background(Color.LightGray, shape = RoundedCornerShape(12.dp))
//                        .clickable {
//                            navController.navigate("BookContents")
//                        },
//                    contentAlignment = Alignment.Center
//                ) {
//                    Text(text = books[index])
//                }
//            }
//        }
//
//        Spacer(modifier = Modifier.height(32.dp))
//
//        Text(
//            text = "추천 이유",
//            fontSize = 18.sp,
//            fontWeight = FontWeight.SemiBold
//        )
//
//        Spacer(modifier = Modifier.height(8.dp))
//
//        Text(text = "---", fontSize = 16.sp)
//    }
//}
