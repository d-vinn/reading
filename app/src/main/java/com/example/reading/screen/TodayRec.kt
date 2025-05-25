package com.example.reading

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.clickable
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale // Coil 이미지 스케일링
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LibraryBooks
import androidx.compose.material.icons.filled.Settings
import androidx.lifecycle.viewmodel.compose.viewModel // ViewModel 사용
import coil.compose.AsyncImage // Coil 이미지 로딩

// TODO: UserRecommendationProfileViewModel, RecommendationState, Book 클래스 필요

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TodayRecScreen(navController: NavController, viewModel: UserRecommendationProfileViewModel) { // <--- 이 줄이 핵심 변경
    // Shared ViewModel 인스턴스 가져오기
    // MainActivity에서 ViewModelStoreOwner를 지정했으므로 동일 인스턴스를 가져옵니다.
//    val viewModel: UserRecommendationProfileViewModel = viewModel()

    // ViewModel의 추천 상태 관찰
    val recommendationState by viewModel.recommendationState.collectAsState()

    // LazyRow 스크롤 상태
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    // 현재 중앙에 가까운 아이템의 인덱스 계산 및 해당 아이템 정보 가져오기
    val centeredBookIndex by remember {
        derivedStateOf {
            // LazyRow의 첫 보이는 아이템 인덱스와 오프셋을 사용하여 중앙에 가까운 아이템 추정
            val layoutInfo = listState.layoutInfo
            if (layoutInfo.visibleItemsInfo.isEmpty()) return@derivedStateOf -1
            val viewportCenter = layoutInfo.viewportEndOffset / 2
            val centerItem = layoutInfo.visibleItemsInfo.minByOrNull {
                kotlin.math.abs((it.offset + it.size / 2) - viewportCenter)
            }
            centerItem?.index ?: -1
        }
    }

    // ViewModel 상태에 따라 표시될 추천 도서 목록 또는 빈 목록
    val recommendedBooks = remember(recommendationState) {
        when (recommendationState) {
            is RecommendationState.Success -> (recommendationState as RecommendationState.Success).recommendedBooks
            else -> emptyList()
        }
    }

    // 현재 중앙에 있는 책의 추천 이유
    val currentRecommendationReason = remember(recommendedBooks, centeredBookIndex) {
        if (centeredBookIndex != -1 && centeredBookIndex < recommendedBooks.size) {
            recommendedBooks[centeredBookIndex].recommendation_reason
        } else {
            null // 또는 기본 메시지
        }
    }


    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                    selected = false, // 현재 화면이 추천 결과 화면이므로 Home은 false
                    onClick = { navController.navigate("home") {
                        popUpTo("home") { inclusive = true } // Home으로 돌아가고 이전 스택 비우기
                    } }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.LibraryBooks, contentDescription = "Bookshelf") },
                    selected = false,
                    onClick = { navController.navigate("minilib") } // TODO: 라우트 이름 컨벤션: minilib_screen
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Email, contentDescription = "Notes") },
                    selected = false,
                    onClick = { navController.navigate("notes") } // TODO: 라우트 이름 컨벤션: read_book_list_screen
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
                .padding(innerPadding) // Scaffold innerPadding 적용
                .padding(horizontal = 24.dp), // 좌우 패딩
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp)) // 상단 여백

            Text(
                text = "OO이를 위한", // TODO: ViewModel에서 사용자 이름 가져와 표시
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "오늘의 추천도서",
                fontSize = 20.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(24.dp))

            // --- API 호출 상태에 따른 UI 분기 ---
            when (recommendationState) {
                is RecommendationState.Idle -> {
                    // 초기 상태 (HomeScreen에서 이미 호출했으므로 이 상태는 짧거나 나타나지 않을 수 있음)
                    Text("추천 정보를 불러오는 준비중...")
                }
                is RecommendationState.Loading -> {
                    // 로딩 중 UI
                    CircularProgressIndicator(modifier = Modifier.size(50.dp))
                    Text("추천 도서를 찾는 중...")
                }
                is RecommendationState.Error -> {
                    // 오류 발생 UI
                    val errorMessage = (recommendationState as RecommendationState.Error).message
                    Text("추천 오류 발생: $errorMessage", color = MaterialTheme.colorScheme.error)
                }
                is RecommendationState.Success -> {
                    // 성공 시 추천 도서 목록 표시 UI
                    if (recommendedBooks.isNotEmpty()) {
                        // 가로 슬라이드 (LazyRow)
                        LazyRow(
                            state = listState,
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(16.dp) // 아이템 간 간격
                        ) {
                            items(
                                items = recommendedBooks,
                                key = { it.book_id } // 책 고유 ID (ISBN13)를 키로 사용
                            ) { book ->
                                // 개별 추천 책 아이템 UI
                                RecommendedBookItem(
                                    book = book,
                                    navController = navController // BookContents 이동을 위해 전달
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(32.dp))

                        Text(
                            text = "추천 이유",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.SemiBold
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        // 현재 중앙에 있는 책의 추천 이유 표시
                        val reasonText = currentRecommendationReason ?: "이 책에 대한 추천 이유가 없습니다."
                        Text(
                            text = reasonText,
                            fontSize = 16.sp,
                            // TODO: 텍스트 내용이 길 경우 스크롤 가능한 영역으로 만들거나 줄 수를 제한
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f) // 남은 공간 채우도록 설정
                        )

                    } else {
                        // 성공했으나 추천된 책 목록이 비어있을 경우
                        Text("아쉽게도 추천 결과를 찾지 못했습니다.", fontSize = 18.sp)
                    }
                }
            }
            // TODO: 추천 결과 새로고침 버튼 등 추가 가능

        }
    }
}

// 개별 추천 책 아이템 UI 컴포저블
@Composable
fun RecommendedBookItem(
    book: Book,
    navController: NavController // 네비게이션을 위해 필요
) {
    // A4 비율 (1:√2)을 따르는 대략적인 비율로 너비/높이 설정
    val itemWidth = 140.dp // 아이템 너비
    val itemHeight = itemWidth * 1.414f // A4 비율 높이

    Column(
        modifier = Modifier
            .width(itemWidth)
            .clickable {
                // 책 클릭 시 상세 화면으로 이동
                // BookContents 라우트가 bookId를 인자로 받도록 네비게이션 그래프에 설정해야 합니다.
                // 예: composable("book_contents_screen/{bookId}") { ... }
                // book.book_id는 ISBN13입니다. URL 인코딩이 필요할 수 있습니다.
                val bookId = book.book_id
                if (bookId.isNotBlank()) {
                    // TODO: URLEncoder.encode 사용 고려 (ISBN은 안전한 문자만 포함하는 경우가 많음)
                    navController.navigate("BookContents/${bookId}") // TODO: 라우트 이름 컨벤션: book_contents_screen
                }
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 책 커버 이미지 로딩 및 표시
        AsyncImage(
            model = book.cover_image_url, // 이미지 URL
            contentDescription = "${book.title} 커버 이미지", // 접근성 설명
            modifier = Modifier
                .fillMaxWidth() // 컬럼 너비에 맞춤
                .height(itemHeight) // 계산된 높이 적용
                .clip(RoundedCornerShape(8.dp)) // 모서리 둥글게
                .background(Color.Gray), // 로딩 중 또는 이미지 없을 때 배경색
            contentScale = ContentScale.Crop // 이미지를 잘라서 비율 유지
        )

        Spacer(modifier = Modifier.height(8.dp))

        // 책 제목
        Text(
            text = book.title ?: "제목 정보 없음",
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            maxLines = 2, // 제목이 길 경우 두 줄까지 표시
            overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis, // 넘칠 경우 ... 처리
            textAlign = androidx.compose.ui.text.style.TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        // TODO: 저자, 출판사 등 추가 정보 표시 가능
        // Text(text = book.author ?: "저자 정보 없음", fontSize = 12.sp)

    }
}