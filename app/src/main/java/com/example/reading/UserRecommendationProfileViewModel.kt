package com.example.reading

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import java.util.concurrent.TimeUnit // TimeUnit 임포트 추가

// TODO: 이 클래스들은 UserRecommendationProfileViewModel.kt 또는 별도의 파일에 정의되어 있어야 합니다.
data class UserProfileData(
    val userId: String?,
    val userName: String?,
    val age: String?,
    val gender: String?,
    val userInterest: String?, // 오늘 있었던 일
    val selectedCategories: List<String>, // 선택된 카테고리
    val otherInterest: String? // 기타 관심사
)

sealed class RecommendationState {
    object Idle : RecommendationState()
    object Loading : RecommendationState()
    data class Success(val recommendedBooks: List<Book>) : RecommendationState()
    data class Error(val message: String) : RecommendationState()
}

data class Book(
    val book_id: String, // ISBN13
    val title: String?,
    val author: String?,
    val publisher: String?,
    val pubDate: String?,
    val isbn13: String?,
    val cover_image_url: String?,
    val link: String?,
    val recommendation_reason: String?
)

class UserRecommendationProfileViewModel : ViewModel() {

    // OkHttpClient에 타임아웃 설정을 추가합니다.
    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(60, TimeUnit.SECONDS) // 연결 타임아웃 60초 (필요에 따라 조정)
        .readTimeout(60, TimeUnit.SECONDS)    // 응답 읽기 타임아웃 60초 (필요에 따라 조정)
        .writeTimeout(60, TimeUnit.SECONDS)   // 요청 쓰기 타임아웃 60초 (필요에 따라 조정)
        .build()

    private val RECOMMENDATION_API_URL = "http://ec2-54-206-132-114.ap-southeast-2.compute.amazonaws.com:5000/recommend"

    // 사용자 프로필 데이터를 저장하는 StateFlows
    private val _userId = MutableStateFlow<String?>(null)
    val userId: StateFlow<String?> = _userId.asStateFlow()

    private val _userName = MutableStateFlow<String?>(null)
    val userName: StateFlow<String?> = _userName.asStateFlow()

    private val _age = MutableStateFlow<String?>(null)
    val age: StateFlow<String?> = _age.asStateFlow()

    private val _gender = MutableStateFlow<String?>(null)
    val gender: StateFlow<String?> = _gender.asStateFlow()

    private val _userInterest = MutableStateFlow<String?>(null)
    val userInterest: StateFlow<String?> = _userInterest.asStateFlow()

    private val _selectedCategories = MutableStateFlow<List<String>>(emptyList())
    val selectedCategories: StateFlow<List<String>> = _selectedCategories.asStateFlow()

    private val _otherInterest = MutableStateFlow<String?>(null)
    val otherInterest: StateFlow<String?> = _otherInterest.asStateFlow()

    // 추천 상태를 관리하는 StateFlow
    private val _recommendationState = MutableStateFlow<RecommendationState>(RecommendationState.Idle)
    val recommendationState: StateFlow<RecommendationState> = _recommendationState.asStateFlow()


    // --- 사용자 프로필 데이터 설정 함수들 ---
    fun setUserId(id: String) {
        _userId.value = id
        Log.d("UserViewModel", "UserId set: $id")
    }

    fun setUserName(name: String) {
        _userName.value = name
        Log.d("UserViewModel", "UserName set: $name")
    }

    fun setAgeAndGender(age: String, gender: String) {
        _age.value = age
        _gender.value = gender
        Log.d("UserViewModel", "Age set: $age, Gender set: $gender")
    }

    fun setUserInterest(interest: String) {
        _userInterest.value = interest
        Log.d("UserViewModel", "UserInterest set: $interest")
    }

    fun setSelectedCategories(categories: List<String>) {
        _selectedCategories.value = categories
        Log.d("UserViewModel", "SelectedCategories set: $categories")
    }

    fun setOtherInterest(interest: String) {
        _otherInterest.value = interest
        Log.d("UserViewModel", "OtherInterest set: $interest")
    }

    // 현재 프로필 데이터를 캡슐화하여 반환하는 함수
    fun getUserProfileData(): UserProfileData {
        return UserProfileData(
            userId = _userId.value,
            userName = _userName.value,
            age = _age.value,
            gender = _gender.value,
            userInterest = _userInterest.value,
            selectedCategories = _selectedCategories.value,
            otherInterest = _otherInterest.value
        )
    }

    // --- 책 추천 요청 함수 ---
    fun fetchRecommendations() {
        Log.d("RecViewModel", "fetchRecommendations() 호출 시작")

        if (_recommendationState.value is RecommendationState.Loading) {
            Log.d("RecViewModel", "이미 로딩 중이므로 요청 중단.")
            return
        }

        val currentProfile = getUserProfileData()
        Log.d("RecViewModel", "현재 프로필: userId=${currentProfile.userId}, age=${currentProfile.age}, userInterest=${currentProfile.userInterest}")
        // 서버의 필수 입력값 (user_id, user_interests_summary, age) 누락 검증
        if (currentProfile.userId.isNullOrBlank() || currentProfile.age.isNullOrBlank() || currentProfile.userInterest.isNullOrBlank()) {
            _recommendationState.value = RecommendationState.Error("추천에 필요한 사용자 정보(ID, 나이, 오늘 있었던 일)가 부족합니다.")
            Log.e("RecViewModel", "필수 사용자 정보 부족. 요청 취소됨.")
            return
        }

        _recommendationState.value = RecommendationState.Loading
        Log.d("RecViewModel", "추천 요청 로딩 상태 시작.")

        viewModelScope.launch {
            var response: Response? = null
            var responseText: String? = null // 응답 본문을 읽기 위한 변수

            try {
                // 사용자 관심사 및 활동 정보를 통합하여 하나의 문자열로 만듭니다.
                // 서버가 'user_interests_summary' 필드를 기대하므로 이 이름으로 데이터를 보냅니다.
                val interestSummary = buildString {
                    append("오늘 있었던 일: ${currentProfile.userInterest}")
                    if (currentProfile.selectedCategories.isNotEmpty()) {
                        append(". 관심사 카테고리: ${currentProfile.selectedCategories.joinToString(", ")}")
                    }
                    if (!currentProfile.otherInterest.isNullOrBlank()) {
                        append(". 기타 관심사: ${currentProfile.otherInterest}")
                    }
                }

                // 요청 본문 JSON 구성
                val jsonBody = JSONObject().apply {
                    put("user_id", currentProfile.userId)
                    // 서버가 'user_interests_summary'를 기대하므로 이 필드 이름을 사용합니다.
                    put("user_interests_summary", interestSummary) // <-- 이 필드 이름은 변경하지 않습니다.
                    put("age", currentProfile.age)
                    put("gender", currentProfile.gender ?: "") // 서버가 필요로 하지 않는다면 제거를 고려하거나, 서버와 협의
                }
                Log.d("RecViewModel", "요청 URL: $RECOMMENDATION_API_URL")
                Log.d("RecViewModel", "요청 Body: ${jsonBody.toString(2)}") // JSON 예쁘게 출력

                val requestBody = RequestBody.create(
                    "application/json; charset=utf-8".toMediaTypeOrNull(),
                    jsonBody.toString()
                )

                val request = Request.Builder()
                    .url(RECOMMENDATION_API_URL)
                    .post(requestBody)
                    .build()

                Log.d("RecViewModel", "OkHttp 요청 실행 전.")

                // 네트워크 작업 (요청 실행 및 응답 본문 읽기) 전체를 IO 디스패처에서 실행합니다.
                withContext(Dispatchers.IO) {
                    response = okHttpClient.newCall(request).execute()
                    responseText = response?.body?.string() // 응답 본문을 여기서 읽습니다.
                }

                Log.d("RecViewModel", "OkHttp 응답 수신. 코드: ${response?.code}, 성공여부: ${response?.isSuccessful}")

                if (response?.isSuccessful == true) { // 2xx 응답 코드
                    Log.d("RecViewModel", "응답 성공: $responseText")

                    if (responseText != null) {
                        try {
                            val jsonResponse = JSONObject(responseText)
                            if (jsonResponse.has("recommended_books")) {
                                val booksArray = jsonResponse.getJSONArray("recommended_books")
                                val recommendedBooks = mutableListOf<Book>()
                                for (i in 0 until booksArray.length()) {
                                    val bookJson = booksArray.getJSONObject(i)
                                    recommendedBooks.add(
                                        Book(
                                            book_id = bookJson.optString("book_id", ""),
                                            title = bookJson.optString("title"),
                                            author = bookJson.optString("author"),
                                            publisher = bookJson.optString("publisher"),
                                            pubDate = bookJson.optString("pubDate"),
                                            isbn13 = bookJson.optString("isbn13"),
                                            cover_image_url = bookJson.optString("cover_image_url"),
                                            link = bookJson.optString("link"),
                                            recommendation_reason = bookJson.optString("recommendation_reason")
                                        )
                                    )
                                }
                                _recommendationState.value = RecommendationState.Success(recommendedBooks)
                            } else {
                                _recommendationState.value = RecommendationState.Error("서버 응답 형식 오류: 'recommended_books' 키가 없습니다. 응답: $responseText")
                            }
                        } catch (e: Exception) {
                            _recommendationState.value = RecommendationState.Error("응답 파싱 중 오류 발생: ${e.localizedMessage ?: "Unknown parsing error"} \n응답: $responseText")
                        }
                    } else {
                        _recommendationState.value = RecommendationState.Error("서버 응답 본문이 비어 있습니다. 코드: ${response?.code}")
                    }
                } else {
                    Log.e("RecViewModel", "응답 실패: ${response?.code}, ${responseText}")
                    val errorBody = responseText ?: "응답 본문 없음"
                    try {
                        val jsonError = JSONObject(errorBody)
                        val errorMessage = jsonError.optString("error", "알 수 없는 서버 오류")
                        _recommendationState.value = RecommendationState.Error("서버 오류 (${response?.code}): $errorMessage")
                    } catch (e: Exception) {
                        // 서버에서 JSON이 아닌 일반 텍스트 오류를 보낼 경우
                        _recommendationState.value = RecommendationState.Error("서버 오류 (${response?.code}): $errorBody")
                    }
                }

            } catch (e: IOException) {
                // SocketTimeoutException도 IOException의 하위 클래스입니다.
                Log.e("RecViewModel", "네트워크 오류 발생: ${e.message}", e)
                _recommendationState.value = RecommendationState.Error("네트워크 오류 발생: ${e.localizedMessage ?: e.message ?: "Unknown network error"}")
            } catch (e: Exception) {
                Log.e("RecViewModel", "예기치 않은 오류 발생: ${e.message}", e)
                _recommendationState.value = RecommendationState.Error("예기치 않은 오류 발생: ${e.localizedMessage ?: e.message ?: "Unknown error"}")
            } finally {
                // OkHttp ResponseBody는 항상 닫아주어야 리소스 누수를 방지합니다.
                response?.body?.close()
                Log.d("RecViewModel", "fetchRecommendations() 호출 종료.")
            }
        }
    }
}