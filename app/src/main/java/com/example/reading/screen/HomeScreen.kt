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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.collectAsState // collectAsState를 사용하여 StateFlow 관찰
import androidx.compose.runtime.getValue // by 키워드를 사용하기 위해 필요
import android.util.Log // Logcat 출력을 위해 추가


// TODO: UserRecommendationProfileViewModel 클래스 필요 (이전 단계 참고)
// TODO: R.drawable.character 이미지 리소스 확인 필요

@Composable
fun HomeScreen(navController: NavHostController, viewModel: UserRecommendationProfileViewModel) { // <--- 이 줄이 핵심 변경
//    // Shared ViewModel 인스턴스를 Composable 내부에서 가져옵니다.
//    val viewModel: UserRecommendationProfileViewModel = viewModel()

    // ViewModel에서 사용자 이름을 관찰합니다.
    val userName by viewModel.userName.collectAsState() // <-- ViewModel의 userName 상태를 관찰

    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                    selected = true,
                    onClick = { /* 현재 화면이므로 네비게이션 필요 없을 수 있음 */ }
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
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Top character + speech bubble
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
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
                                // ViewModel에서 가져온 사용자 이름을 표시하거나, 이름이 없을 경우 기본값("사용자")을 표시
                                append(userName ?: "사용자") // <-- 'OO' 대신 userName 변수 사용
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
                        style = MaterialTheme.typography.bodyMedium.copy(fontSize = 16.sp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "오늘의 베스트 도서",
                fontSize = 24.sp,
                style = MaterialTheme.typography.titleMedium,
            )

            Spacer(modifier = Modifier.height(16.dp))

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(5) { index ->
                    Box(
                        modifier = Modifier
                            .size(width = 120.dp, height = 160.dp)
                            .background(MaterialTheme.colorScheme.secondaryContainer, shape = RoundedCornerShape(8.dp))
                            .clickable { navController.navigate("BookContents") },
                        contentAlignment = Alignment.Center
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
                onClick = {
                    Log.d("HomeScreen", "추천 버튼 클릭. fetchRecommendations() 호출 전.")
                    viewModel.fetchRecommendations()
                    Log.d("HomeScreen", "fetchRecommendations() 호출 후. TodayRec으로 이동.")
                    navController.navigate("TodayRec")
                },
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