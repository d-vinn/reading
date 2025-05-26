package com.example.reading

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import retrofit2.http.GET
import retrofit2.http.Query

interface YoutubeApiService {
    @GET("search")
    suspend fun searchVideos(
        @Query("part") part: String = "snippet",
        @Query("maxResults") maxResults: Int = 10,
        @Query("q") query: String, // 검색어
        @Query("type") type: String = "video",
        @Query("key") apiKey: String // 발급받은 API 키
    ): YoutubeSearchResponse
}


// 유튜브 썸네일, 영상 ID, 제목, 설명 데이터 클래스
data class YoutubeVideo(
    val videoId: String,
    val title: String,
    val description: String
)

data class YoutubeSearchResponse(
    val items: List<YoutubeSearchItem>
)

data class YoutubeSearchItem(
    val id: YoutubeVideoId,
    val snippet: YoutubeSnippet
)

data class YoutubeVideoId(
    val videoId: String
)

data class YoutubeSnippet(
    val title: String,
    val description: String
)


// 썸네일 URL 생성 함수
fun getYoutubeThumbnailUrl(videoId: String) =
    "https://img.youtube.com/vi/$videoId/hqdefault.jpg"

val retrofit = Retrofit.Builder()
    .baseUrl("https://www.googleapis.com/youtube/v3/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()

val youtubeApi = retrofit.create(YoutubeApiService::class.java)



class ChooseThumbnailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            YoutubeVideoListScreen(keyword = "공룡", apiKey = "AIzaSyCfhQrQjgKPn8oHPlgYiEsJynNkUkuDlRo")
        }
    }
}

@Composable
fun YoutubeVideoListScreen(keyword: String, apiKey: String) {
    var videos by remember { mutableStateOf<List<YoutubeVideo>>(emptyList()) }
    var isLoading by remember { mutableStateOf(false) }

    LaunchedEffect(keyword) {
        isLoading = true
        try {
            val response = youtubeApi.searchVideos(query = keyword, apiKey = apiKey)
            videos = response.items.map {
                YoutubeVideo(
                    videoId = it.id.videoId,
                    title = it.snippet.title,
                    description = it.snippet.description
                )
            }
        } catch (e: Exception) {
            // 에러 처리
            videos = emptyList()
        }
        isLoading = false
    }

    if (isLoading) {
        CircularProgressIndicator()
    } else {
        // 위에서 만든 ChooseThumbnailScreen과 비슷하게 videos를 넘겨서 사용
        ChooseThumbnailScreen(videos)
    }
}

fun ChooseThumbnailScreen() {
    var currentIndex by remember { mutableStateOf(0) }
    val videos = yourDynamicVideoList

    // 영상 정보 저장 함수 (여기서 DynamoDB 연동)
    fun saveVideoInfoToDynamoDB(video: YoutubeVideo) {
        // TODO: 실제 DynamoDB 저장 코드 구현 필요
        println("저장됨: ${video.title}")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 안내 문구
        Text(
            text = "안 끌린다면 왼쪽으로 넘기고,\n궁금하다면 오른쪽으로 넘기세요!",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        Spacer(modifier = Modifier.height(40.dp))

        if (currentIndex < videos.size) {
            val video = videos[currentIndex]

            Box(
                modifier = Modifier
                    .size(300.dp)
                    .pointerInput(currentIndex) {
                        detectHorizontalDragGestures { change, dragAmount ->
                            if (dragAmount > 50) {
                                // 오른쪽 스와이프: 저장 후 다음 썸네일
                                saveVideoInfoToDynamoDB(video)
                                currentIndex++
                            } else if (dragAmount < -50) {
                                // 왼쪽 스와이프: 저장 없이 다음 썸네일
                                currentIndex++
                            }
                        }
                    }
                    .background(MaterialTheme.colorScheme.surface, shape = RoundedCornerShape(16.dp)),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = getYoutubeThumbnailUrl(video.videoId),
                    contentDescription = video.title,
                    modifier = Modifier.fillMaxSize()
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "${currentIndex + 1} / ${videos.size}",
                fontSize = 16.sp
            )
        } else {
            Text(
                text = "모든 썸네일을 확인하셨습니다!",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
