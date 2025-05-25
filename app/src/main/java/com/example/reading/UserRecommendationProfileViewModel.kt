package com.example.reading // 실제 프로젝트의 패키지명으로 변경해야 합니다.

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import org.json.JSONObject
import java.io.IOException
import java.util.concurrent.TimeUnit
import java.util.UUID
import org.json.JSONArray

// API 호출 상태를 나타내는 Sealed Class (기존과 동일)
sealed class RecommendationState {
    object Idle : RecommendationState()
    object Loading : RecommendationState()
    data class Success(val recommendedBooks: List<Book>) : RecommendationState()
    data class Error(val message: String) : RecommendationState()
}

// 추천 도서 정보를 담을 데이터 클래스 (기존과 동일)
data class Book(
    val book_id: String,
    val title: String?,
    val author: String?,
    val publisher: String?,
    val pubDate: String?,
    val isbn13: String?,
    val cover_image_url: String?,
    val link: String?,
    val recommendation_reason: String?
)

// 사용자 프로필 데이터를 담을 데이터 클래스 (기존과 동일, userId 필드 추가됨)
data class UserProfileData(
    val userId: String?, // 사용자 고유 ID
    val age: String?, // SignupScreen의 나이
    val gender: String?, // SignupScreen의 성별
    val userInterest: String?, // TodayDoScreen의 입력값 ('오늘 있었던 일')
    val selectedCategories: List<String>, // CategoryScreen의 선택 카테고리 ('관심사' 일부)
    val otherInterest: String? // CategoryScreen의 기타 입력값 ('관심사' 일부)
)


// 여러 화면에서 공유될 ViewModel (서버 호출 로직 수정 - 관심사 통합)
class UserRecommendationProfileViewModel : ViewModel() {

    // --- OkHttp 클라이언트 설정 ---
    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS) // 연결 타임아웃 30초
        .readTimeout(60, TimeUnit.SECONDS)    // 읽기 타임아웃 60초
        .writeTimeout(30, TimeUnit.SECONDS)   // 쓰기 타임아웃 30초
        .build()

    // TODO: 실제 AWS EC2 Flask 서버의 추천 API 엔드포인트 URL로 변경해야 합니다.
    private val RECOMMENDATION_API_URL = "http://ec2-54-206-132-114.ap-southeast-2.compute.amazonaws.com:5000/recommend"

    // --- 내부에서 관리할 변경 가능한 상태 ---

    private val _userId = MutableStateFlow<String?>(null)
    private val _age = MutableStateFlow<String?>(null)
    private val _gender = MutableStateFlow<String?>(null)
    private val _userInterest = MutableStateFlow<String?>(null) // '오늘 있었던 일'
    private val _selectedCategories = MutableStateFlow<List<String>>(emptyList()) // '관심사' 일부
    private val _otherInterest = MutableStateFlow<String?>(null) // '관심사' 일부

    private val _recommendationState = MutableStateFlow<RecommendationState>(RecommendationState.Idle)


    // --- 외부에서 읽기 전용으로 접근할 상태 ---

    val userId: StateFlow<String?> = _userId.asStateFlow()
    val age: StateFlow<String?> = _age.asStateFlow()
    val gender: StateFlow<String?> = _gender.asStateFlow()
    val userInterest: StateFlow<String?> = _userInterest.asStateFlow()
    val selectedCategories: StateFlow<List<String>> = _selectedCategories.asStateFlow()
    val otherInterest: StateFlow<String?> = _otherInterest.asStateFlow()

    val recommendationState: StateFlow<RecommendationState> = _recommendationState.asStateFlow()


    // --- 데이터를 설정하는 함수 ---

    fun setUserId(id: String) {
        _userId.value = id.trim()
    }

    fun setAgeAndGender(age: String?, gender: String?) {
        _age.value = age?.trim()
        _gender.value = gender?.trim()
    }

    fun setUserInterest(interest: String?) {
        _userInterest.value = interest?.trim()
    }

    fun setCategoryAndOther(categories: List<String>, other: String?) {
        _selectedCategories.value = categories.toList()
        _otherInterest.value = other?.trim()
    }

    // --- 수집된 데이터를 한 번에 가져오는 함수 ---

    fun getUserProfileData(): UserProfileData {
        return UserProfileData(
            userId = _userId.value,
            age = _age.value,
            gender = _gender.value,
            userInterest = _userInterest.value,
            selectedCategories = _selectedCategories.value,
            otherInterest = _otherInterest.value
        )
    }

    // --- 추천 API 호출 로직 ---

    /**
     * 수집된 사용자 프로필 데이터를 바탕으로 책 추천 API를 호출합니다.
     * API 호출 상태는 recommendationState StateFlow를 통해 외부에 노출됩니다.
     */
    fun fetchRecommendations() {
        if (_recommendationState.value is RecommendationState.Loading) {
            return
        }

        val currentProfile = getUserProfileData()
        // 추천에 필요한 최소 정보 확인: ID, 나이, 오늘 있었던 일 (userInterest)
        // 카테고리나 기타 입력은 필수가 아닐 수 있으므로 여기서는 필수로 간주하지 않음
        if (currentProfile.userId.isNullOrBlank() || currentProfile.age.isNullOrBlank() || currentProfile.userInterest.isNullOrBlank()) {
            _recommendationState.value = RecommendationState.Error("추천에 필요한 사용자 정보(ID, 나이, 오늘 있었던 일)가 부족합니다.")
            return
        }

        _recommendationState.value = RecommendationState.Loading

        viewModelScope.launch {
            var response: Response? = null
            try {
                // 사용자 관심사 및 활동 정보를 통합하여 하나의 문자열로 만듭니다.
                val interestSummary = buildString {
                    append("오늘 있었던 일: ${currentProfile.userInterest}")
                    if (currentProfile.selectedCategories.isNotEmpty()) {
                        append(". 관심사 카테고리: ${currentProfile.selectedCategories.joinToString(", ")}")
                    }
                    if (!currentProfile.otherInterest.isNullOrBlank()) {
                        append(". 기타 관심사: ${currentProfile.otherInterest}")
                    }
                }

                // 요청 본문 JSON 구성 (통합된 관심사 필드 사용)
                val jsonBody = JSONObject().apply {
                    put("user_id", currentProfile.userId)
                    // 통합된 관심사 및 활동 정보 필드로 전송
                    put("user_interests_summary", interestSummary) // 기존 user_recent_activity, selected_categories, other_interest 대체

                    put("age", currentProfile.age)
                    put("gender", currentProfile.gender ?: "")
                }

                val requestBody = RequestBody.create(
                    "application/json; charset=utf-8".toMediaTypeOrNull(),
                    jsonBody.toString()
                )

                val request = Request.Builder()
                    .url(RECOMMENDATION_API_URL)
                    .post(requestBody)
                    .build()

                response = okHttpClient.newCall(request).execute()
                val responseText = response.body?.string()

                if (response.isSuccessful) { // 2xx 응답 코드
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
                                _recommendationState.value = RecommendationState.Error("서버 응답 형식 오류: 추천 도서 목록 키가 없습니다. $responseText")
                            }
                        } catch (e: Exception) {
                            _recommendationState.value = RecommendationState.Error("응답 파싱 중 오류 발생: ${e.localizedMessage ?: "Unknown parsing error"} \n응답: $responseText")
                        }
                    } else {
                        _recommendationState.value = RecommendationState.Error("서버 응답 본문이 비어 있습니다. 코드: ${response.code}")
                    }
                } else { // 2xx 외 응답 코드 (오류 응답)
                    val errorBody = responseText ?: "응답 본문 없음"
                    try {
                        val jsonError = JSONObject(errorBody)
                        val errorMessage = jsonError.optString("error", "알 수 없는 서버 오류")
                        _recommendationState.value = RecommendationState.Error("서버 오류 (${response.code}): $errorMessage")
                    } catch (e: Exception) {
                        _recommendationState.value = RecommendationState.Error("서버 오류 (${response.code}): $errorBody")
                    }
                }

            } catch (e: IOException) {
                _recommendationState.value = RecommendationState.Error("네트워크 오류 발생: ${e.localizedMessage ?: e.message ?: "Unknown network error"}")
            } catch (e: Exception) {
                _recommendationState.value = RecommendationState.Error("예기치 않은 오류 발생: ${e.localizedMessage ?: e.message ?: "Unknown error"}")
            } finally {
                response?.body?.close()
            }
        }
    }

    fun resetRecommendationState() {
        _recommendationState.value = RecommendationState.Idle
    }

    override fun onCleared() {
        super.onCleared()
        okHttpClient.dispatcher.cancelAll()
    }

    // TODO: 사용자 ID 설정 로직은 앱 시작 시 또는 회원가입 완료 시점에 필요합니다.
    // 예: ViewModel을 처음 가져올 때 (SignupScreen이나 MainActivity 등) _userId.value가 null이면 생성 및 설정
}