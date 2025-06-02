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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.Font
import androidx.compose.runtime.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle


// TODO: UserRecommendationProfileViewModel 클래스 필요 (이전 단계 참고)
// TODO: R.drawable.character 이미지 리소스 확인 필요

@Composable
fun HomeScreen(navController: NavHostController, viewModel: UserRecommendationProfileViewModel) { // <--- 이 줄이 핵심 변경
//    // Shared ViewModel 인스턴스를 Composable 내부에서 가져옵니다.
//    val viewModel: UserRecommendationProfileViewModel = viewModel()

    // ViewModel에서 사용자 이름을 관찰합니다.
    val userName by viewModel.userName.collectAsState() // <-- ViewModel의 userName 상태를 관찰
    val kidFont = FontFamily(Font(R.font.uhbee_puding))
    val selectedBgColor = Color(0xFFB9D99A) // #B9D99A
    val unselectedColor = Color.Gray
    val shfont = FontFamily(Font(R.font.uhbee_se_hyun))
    var selectedIndex by remember { mutableStateOf(0) }
    val myGreen = Color(0xFF63A621)

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
                .padding(innerPadding)
                .fillMaxSize()
                .padding(24.dp),
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
                            withStyle(style = SpanStyle(color = colorResource(id = R.color.light_green), fontWeight = FontWeight.Bold)) {
                                // ViewModel에서 가져온 사용자 이름을 표시하거나, 이름이 없을 경우 기본값("사용자")을 표시
                                append(userName ?: "사용자") // <-- 'OO' 대신 userName 변수 사용
                            }
                            append("아 안녕!\n나는 너의 책읽기를 도와줄 리딩이야.\n")

                            append("오늘의 ")

                            withStyle(style = SpanStyle(color = colorResource(id = R.color.light_green), fontWeight = FontWeight.Bold)) {
                                append("인기 있는 책")
                            }

                            append("이나, \n")

                            withStyle(style = SpanStyle(color = colorResource(id = R.color.light_green), fontWeight = FontWeight.Bold)) {
                                append("너만의 추천도서")
                            }

                            append("를 소개해줄게!")
                        },
                        modifier = Modifier.padding(13.dp),
                        style = MaterialTheme.typography.bodyMedium.copy(fontSize = 24.sp), fontFamily = kidFont
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "오늘의 베스트 도서",
                fontSize = 24.sp,
                style = TextStyle(fontFamily = shfont),
            )

            Spacer(modifier = Modifier.height(24.dp))

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(5) { index ->
                    val imageResId = when (index) {
                        0 -> R.drawable.book1
                        1 -> R.drawable.book2
                        2 -> R.drawable.book3
                        3 -> R.drawable.book4
                        4 -> R.drawable.book5
                        else -> R.drawable.book1 // 혹시 모를 예외 처리
                    }

                    Box(
                        modifier = Modifier
                            .size(width = 150.dp, height = 200.dp)
                            .background(
                                MaterialTheme.colorScheme.secondaryContainer,
                                shape = RoundedCornerShape(8.dp)
                            )
                            .clickable { navController.navigate("BookContents") },
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = imageResId),
                            contentDescription = "책 이미지 ${index + 1}",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(RoundedCornerShape(8.dp))
                        )
//                        // 텍스트를 이미지 위에 오버레이하고 싶으면 아래 코드 유지
//                        Text(
//                            text = "책 ${index + 1}",
//                            modifier = Modifier.align(Alignment.BottomCenter),
//                            color = Color.White,
//                            style = MaterialTheme.typography.bodyMedium
//                        )
                    }
                }
            }


            Spacer(modifier = Modifier.height(36.dp))

            TextButton(
                onClick = {
                    Log.d("HomeScreen", "추천 버튼 클릭. fetchRecommendations() 호출 전.")
                    viewModel.fetchRecommendations()
                    Log.d("HomeScreen", "fetchRecommendations() 호출 후. TodayRec으로 이동.")
                    navController.navigate("TodayRec")
                },
            ) {
                Text("오늘의 추천도서 바로가기>",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = myGreen
                    ,
                    style = TextStyle(fontFamily = shfont)
                )
            }
        }
    }
}