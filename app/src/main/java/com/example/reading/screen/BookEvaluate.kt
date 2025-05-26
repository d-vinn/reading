package com.example.reading

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.navigation.NavController

@Composable
fun BookEvaluateScreen(navController: NavController) {
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
                .fillMaxSize()
                .padding(innerPadding)
                .padding(24.dp),
            verticalArrangement = Arrangement.Top
        ) {
            // 도서 정보 및 표지
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(text = "도서명: ___", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "저자: ___", fontSize = 16.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "간략줄거리: ___", fontSize = 16.sp)
                }
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .border(1.dp, Color.Black),
                    contentAlignment = Alignment.Center
                ) {
                    Text("표지")
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // 평가 박스
            Text(
                text = "이 책이 마음에 드셨나요?",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(12.dp))

            val labels = listOf("완전!", "괜찮아!", "읽을 만했어", "그냥 그래..", "별로야ㅠㅠ")
            var selectedLabel by remember { mutableStateOf<String?>(null) }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                labels.forEach { label ->
                    Button(
                        onClick = {
                            selectedLabel = label
                        },
                        modifier = Modifier
                            .weight(1f)
                            .height(40.dp), // 높이 지정
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (selectedLabel == label) Color(0xFF90CAF9) else Color.LightGray
                        ),
                        contentPadding = PaddingValues(horizontal = 4.dp)
                    ) {
                        Text(
                            text = label,
                            fontSize = 12.sp,
                            maxLines = 1, // 한 줄로 표시
                            softWrap = false // 줄바꿈 금지
                        )
                    }
                }
            }



            Spacer(modifier = Modifier.height(32.dp))

            // 하단 활동 버튼 3개
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = { navController.navigate("report") },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("간단 독후감 쓰기 >", fontSize = 16.sp)
                }
                Button(
                    onClick = { navController.navigate("img") },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("책의 한 장면 그리기 >", fontSize = 16.sp)
                }
                Button(
                    onClick = { navController.navigate("quiz") },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("퀴즈 풀어보기 >", fontSize = 16.sp)
                }
            }
        }
    }
}
