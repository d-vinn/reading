package com.example.reading

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import androidx.lifecycle.viewmodel.compose.viewModel
import android.util.Log
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.collectAsState

@Composable
fun BookContentsScreen(navController: NavController, bookId: String?) {
    val viewModel: UserRecommendationProfileViewModel = viewModel()

    // 추천 상태를 직접 관찰
    val recommendationState by viewModel.recommendationState.collectAsState() // <-- 이 줄 추가

    var bookDetail: Book? by remember { mutableStateOf(null) }

    // LaunchedEffect의 key에 bookId와 recommendationState를 모두 포함
    // 이렇게 하면 bookId가 변경되거나, recommendationState가 변경될 때마다 블록이 다시 실행됩니다.
    LaunchedEffect(bookId, recommendationState) { // <-- 여기에 recommendationState 추가
        Log.d("BookContentsScreen", "LaunchedEffect triggered for bookId: $bookId, state: $recommendationState")

        if (bookId != null) {
            // 이제 'recommendationState'는 LaunchedEffect의 인자로 전달된 최신 값입니다.
            if (recommendationState is RecommendationState.Success) {
                val foundBook = (recommendationState as RecommendationState.Success).recommendedBooks.find { it.book_id == bookId }
                bookDetail = foundBook
                Log.d("BookContentsScreen", "Found Book: $foundBook")
            } else {
                Log.d("BookContentsScreen", "RecommendationState is not Success (current state: $recommendationState).")
                bookDetail = null // 로딩 중 또는 오류 상태이므로 책 정보 초기화
            }
        } else {
            Log.d("BookContentsScreen", "bookId is null.")
            bookDetail = null
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "책 상세 정보", fontSize = 24.sp)
        Spacer(modifier = Modifier.height(16.dp))

        if (bookId == null) {
            Text(text = "오류: 책 ID를 찾을 수 없습니다.", fontSize = 18.sp)
        } else if (bookDetail == null) {
            // 로딩 중이거나 데이터를 찾지 못했을 때의 UI
            Text(text = "책 정보를 불러오는 중...", fontSize = 18.sp)
            // recommendationState가 Loading일 때 로딩 인디케이터를 보여줄 수도 있습니다.
            if (recommendationState is RecommendationState.Loading) {
                CircularProgressIndicator(modifier = Modifier.size(50.dp))
            }
        } else {
            bookDetail?.let { book ->
                AsyncImage(
                    model = book.cover_image_url,
                    contentDescription = "${book.title} 커버",
                    modifier = Modifier
                        .height(250.dp)
                        .padding(bottom = 16.dp)
                )
                Text(text = "제목: ${book.title ?: "정보 없음"}", fontSize = 20.sp)
                Text(text = "저자: ${book.author ?: "정보 없음"}", fontSize = 16.sp)
                Text(text = "출판사: ${book.publisher ?: "정보 없음"}", fontSize = 16.sp)
                Text(text = "출판일: ${book.pubDate ?: "정보 없음"}", fontSize = 16.sp)
                Text(text = "ISBN13: ${book.isbn13 ?: "정보 없음"}", fontSize = 16.sp)
                Spacer(modifier = Modifier.height(16.dp))

                Text(text = "추천 이유:", fontSize = 18.sp)
                Text(text = book.recommendation_reason ?: "추천 이유가 없습니다.", fontSize = 16.sp)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
        Button(onClick = { navController.popBackStack() }) {
            Text("뒤로 가기")
        }
    }
}