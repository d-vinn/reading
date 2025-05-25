package com.example.reading
import androidx.compose.foundation.shape.RoundedCornerShape

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
import androidx.compose.material3.Text // androidx.compose.material.Text 대신 androidx.compose.material3.Text 사용
import androidx.lifecycle.viewmodel.compose.viewModel // viewModel 함수 임포트

// TODO: UserRecommendationProfileViewModel 클래스 필요 (이전 단계 참고)
// TODO: R.drawable.character 이미지 리소스 확인 필요

@Composable
fun HomeScreen(navController: NavHostController) {
    // Shared ViewModel 인스턴스를 Composable 내부에서 가져옵니다.
    // MainActivity에서 ViewModelStoreOwner를 지정했으므로 동일 인스턴스를 가져옵니다.
    val viewModel: UserRecommendationProfileViewModel = viewModel()

    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                    selected = true,
                    // 현재 화면이 Home이므로 클릭 시 특별한 네비게이션 필요 없을 수 있습니다.
                    // 하지만 Home 버튼 클릭 시 스택을 비우고 Home으로 돌아오게 하려면 popUpTo 사용 가능
                    onClick = { /* 현재 화면이므로 네비게이션 없음 */ }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.LibraryBooks, contentDescription = "Bookshelf") },
                    selected = false,
                    // TODO: MainActivity의 NavHost에 정의된 실제 라우트 이름으로 변경 ("minilib" 또는 "minilib_screen")
                    onClick = { navController.navigate("minilib") }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Email, contentDescription = "Notes") },
                    selected = false,
                    // TODO: MainActivity의 NavHost에 정의된 실제 라우트 이름으로 변경 ("notes" 또는 "read_book_list_screen")
                    onClick = { navController.navigate("notes") }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Settings, contentDescription = "Settings") },
                    selected = false,
                    onClick = { /* settings navigation */ } // TODO: 설정 화면 라우트
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally // 중앙 정렬 적용
        ) {
            // Top character + speech bubble
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth() // Row가 너비를 채우도록 설정
            ) {
                Image(
                    painter = painterResource(id = R.drawable.character), // 리소스 ID 확인
                    contentDescription = "Character",
                    modifier = Modifier.size(64.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Surface(
                    shape = MaterialTheme.shapes.medium,
                    tonalElevation = 2.dp,
                    modifier = Modifier.weight(1f) // 남은 공간 채우도록 설정
                ) {
                    // TODO: "OO" 부분에 사용자 이름 등을 ViewModel에서 가져와 표시 가능
                    Text(
                        text = buildAnnotatedString {
                            withStyle(style = SpanStyle(color = Color.Blue, fontWeight = FontWeight.Bold)) {
                                append("OO") // TODO: ViewModel에서 사용자 이름 가져와 표시
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
                text = "오늘의 베스트 도서", // TODO: 이 데이터도 ViewModel에서 가져오거나 API 호출 결과로 표시
                fontSize = 24.sp,
                style = MaterialTheme.typography.titleMedium,
                // modifier.align(Alignment.CenterHorizontally) // Column에 이미 HorizontalAlignment가 적용됨
            )

            Spacer(modifier = Modifier.height(16.dp))

            // TODO: 베스트 도서 목록 표시 (데이터는 ViewModel 또는 API 호출 결과로 받아와야 함)
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                // 예시 데이터. 실제로는 ViewModel의 StateFlow 등을 관찰하여 데이터 변경 시 업데이트
                items(5) { index ->
                    Box(
                        modifier = Modifier
                            .size(width = 120.dp, height = 160.dp)
                            .background(MaterialTheme.colorScheme.secondaryContainer, shape = RoundedCornerShape(8.dp)) // 예시 색상, 둥근 모서리 추가
                            // TODO: BookContents 라우트가 인자를 받도록 수정되었다면, 여기에 책 ID를 전달하는 로직 필요
                            // 현재 MainActivity는 BookContents 라우트가 인자를 받지 않도록 되어 있음
                            .clickable { navController.navigate("BookContents") }, // MainActivity에 정의된 라우트 이름 사용
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "책 ${index + 1}", // 예시 텍스트. 실제로는 책 제목 등 표시
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // '오늘의 추천도서 바로가기' 버튼 클릭 시 추천 API 호출 트리거 및 화면 이동
            TextButton(
                onClick = {
                    // 1. ViewModel에서 추천 API 호출을 트리거하는 함수를 실행합니다.
                    // 이 함수는 ViewModel에 저장된 사용자 정보를 사용합니다.
                    viewModel.fetchRecommendations()

                    // 2. 추천 결과를 표시할 화면으로 네비게이션합니다.
                    // MainActivity의 NavHost에서 정의된 라우트 이름과 일치시켜야 합니다.
                    // 현재 MainActivity에서는 "TodayRec"으로 정의되어 있습니다.
                    navController.navigate("TodayRec")
                },
                // modifier.align(Alignment.CenterHorizontally) // Column에 이미 HorizontalAlignment가 적용됨
            ) {
                Text("오늘의 추천도서 바로가기 >",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF073042) // 예시 색상
                )
            }
        }
    }
}