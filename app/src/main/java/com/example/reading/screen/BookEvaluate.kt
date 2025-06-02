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
import androidx.compose.ui.text.TextStyle
import androidx.navigation.NavController
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.Font
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.colorResource

@Composable
fun BookEvaluateScreen(navController: NavController) {
    val kidFont = FontFamily(Font(R.font.uhbee_puding))
    var selectedIndex by remember { mutableStateOf(2) }
    val selectedBgColor = Color(0xFFB9D99A) // #B9D99A
    val unselectedColor = Color.Gray
    Scaffold(
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
                        onClick = { selectedIndex = 0 },
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
                .padding(24.dp),
            verticalArrangement = Arrangement.Top
        ) {
            // 도서 정보 및 표지
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(text = "도서명: 책 먹는 여우", fontSize = 24.sp, fontWeight = FontWeight.Bold, style = TextStyle(fontFamily = kidFont))
                    Spacer(modifier = Modifier.height(14.dp))
                    Text(text = "저자: 프란치스카 비어만", fontSize = 24.sp, style = TextStyle(fontFamily = kidFont))
                    Spacer(modifier = Modifier.height(14.dp))
                    Text(text = "간략줄거리: 책을 쓰는 인간보다\n더 책을 사랑하는 여우를 통해,\n책이 어떤 의미와 가치를 갖는가를\n다소 희극적으로 재미있게 이야기한다.", fontSize = 24.sp, style = TextStyle(fontFamily = kidFont))
                }
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .border(1.dp, Color.Black),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.book6),
                        contentDescription = "책 표지",
                        modifier = Modifier.fillMaxSize()
                    )
                }

            }

            Spacer(modifier = Modifier.height(32.dp))

            // 평가 박스
            Text(
                text = "이 책이 마음에 드셨나요?",
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold,
                style = TextStyle(fontFamily = kidFont)
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
                            softWrap = false, // 줄바꿈 금지,
                            style = TextStyle(fontFamily = kidFont)
                        )
                    }
                }
            }



            Spacer(modifier = Modifier.height(38.dp))

            // 하단 활동 버튼 3개
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = { navController.navigate("report") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = R.color.light_green)
                    )

                ) {
                    Text("간단 독후감 쓰기 >", fontSize = 24.sp, style = TextStyle(fontFamily = kidFont))
                }
                Button(
                    onClick = { navController.navigate("img") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = R.color.light_green)
                    )
                ) {
                    Text("책의 한 장면 그리기 >", fontSize = 24.sp, style = TextStyle(fontFamily = kidFont))
                }
                Button(
                    onClick = { navController.navigate("quiz") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = R.color.light_green)
                    )
                ) {
                    Text("퀴즈 풀어보기 >", fontSize = 24.sp, style = TextStyle(fontFamily = kidFont))
                }
            }
        }
    }
}
