package com.example.testxml.presentation.activities.movie_details_screen

import android.content.Context
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testxml.common.StateMachine
import com.example.testxml.common.StateMachineWithoutData
import com.example.testxml.data.remote.dto.Movie
import com.example.testxml.data.remote.dto.MovieDetailDto
import com.example.testxml.data.remote.dto.MoviePageDto
import com.example.testxml.data.remote.dto.Person
import com.example.testxml.data.remote.dto.PersonResponseDto
import com.example.testxml.data.remote.dto.ReviewDetail
import com.example.testxml.data.remote.dto.UserReviewDto
import com.example.testxml.data.remote.dto.toMovieDetail
import com.example.testxml.data.remote.dto.toProfile
import com.example.testxml.data.room.entities.Friend
import com.example.testxml.domain.models.MovieDetail
import com.example.testxml.domain.models.Profile
import com.example.testxml.domain.models.UserReview
import com.example.testxml.domain.use_case.add_favorite_use_case.AddFavoriteUseCase
import com.example.testxml.domain.use_case.add_review_use_case.AddReviewUseCase
import com.example.testxml.domain.use_case.database_use_cases.friends_use_cases.AddFriendUseCase
import com.example.testxml.domain.use_case.database_use_cases.friends_use_cases.GetFriendsUseCase
import com.example.testxml.domain.use_case.database_use_cases.genre_use_cases.AddGenreUseCase
import com.example.testxml.domain.use_case.database_use_cases.genre_use_cases.DeleteGenreUseCase
import com.example.testxml.domain.use_case.database_use_cases.genre_use_cases.GetGenreUseCase
import com.example.testxml.domain.use_case.delete_favorite_use_case.DeleteFavoriteUseCase
import com.example.testxml.domain.use_case.delete_review_use_case.DeleteReviewUseCase
import com.example.testxml.domain.use_case.edit_review_use_case.EditReviewUseCase
import com.example.testxml.domain.use_case.get_favorite_use_case.GetFavoriteMoviesUseCase
import com.example.testxml.domain.use_case.get_movie_info_use_case.GetMovieInfoUseCase
import com.example.testxml.domain.use_case.get_person_use_case.GetPersonUseCase
import com.example.testxml.domain.use_case.get_profile_use_case.GetProfileUseCase
import com.example.testxml.domain.use_case.movie_details_use_case.GetMovieDetailsUseCase
import com.example.testxml.domain.use_case.movie_details_use_case.GetMovieReviewsUseCase
import com.example.testxml.presentation.activities.feed_screen.util.MovieStateHandler
import com.example.testxml.presentation.activities.sign_up_activity.util.Month
import com.example.testxml.presentation.utils.StateHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MovieDetailsViewModel constructor(
    val id:String,
    val userLogin: String,
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase = GetMovieDetailsUseCase(),
    private val getPersonUseCase: GetPersonUseCase = GetPersonUseCase(),
    private val getMovieInfoUseCase: GetMovieInfoUseCase = GetMovieInfoUseCase(),
    private val getFavoriteMoviesUseCase: GetFavoriteMoviesUseCase = GetFavoriteMoviesUseCase(),
    private val addFavoritesUseCase: AddFavoriteUseCase = AddFavoriteUseCase(),
    private val deleteFavoriteUseCase: DeleteFavoriteUseCase = DeleteFavoriteUseCase(),
    private val addReviewUseCase: AddReviewUseCase = AddReviewUseCase(),
    private val editReviewUseCase: EditReviewUseCase = EditReviewUseCase(),
    private val deleteReviewUseCase: DeleteReviewUseCase = DeleteReviewUseCase(),
    private val getProfileUseCase: GetProfileUseCase = GetProfileUseCase(),
    private val getMovieReviewsUseCase: GetMovieReviewsUseCase = GetMovieReviewsUseCase(),
    private val addFriendUseCase: AddFriendUseCase = AddFriendUseCase(),
    private val addGenreUseCase: AddGenreUseCase = AddGenreUseCase(),
    private val getGenreUseCase: GetGenreUseCase = GetGenreUseCase(),
    private val deleteGenreUseCase: DeleteGenreUseCase = DeleteGenreUseCase(),
    private val getFriendsUseCase: GetFriendsUseCase = GetFriendsUseCase()
):ViewModel() {
    private val _mainState = MutableStateFlow(StateHandler<MovieDetail>())
    val mainState = _mainState.asStateFlow()

    private val _personState = MutableStateFlow(StateHandler<String>())
    val personState = _personState.asStateFlow()

    private val _ratingState = MutableStateFlow(StateHandler<List<String>>())
    val ratingState = _ratingState.asStateFlow()

    private val _favoritesState = MutableStateFlow(StateHandler<Boolean>())
    val favoriteState = _favoritesState.asStateFlow()

    private val _deletedReviewState = MutableLiveData(StateHandler<Unit>())
    val deletedReviewState:LiveData<StateHandler<Unit>> = _deletedReviewState

    private val _addedReviewState = MutableLiveData(StateHandler<Unit>())
    val addedReviewState:LiveData<StateHandler<Unit>> = _addedReviewState

    private val _editedReviewState = MutableLiveData(StateHandler<Unit>())
    val editedReviewState:LiveData<StateHandler<Unit>> = _editedReviewState

    private val _friendsState = MutableStateFlow(mutableListOf<Friend>())
    val friendsState = _friendsState.asStateFlow()

    fun emitNothingDeleted(){
        _deletedReviewState.value = StateHandler(isNothing = true)
    }

    fun emitNothingAdded(){
        _deletedReviewState.value = StateHandler(isNothing = true)
    }

    fun emitNothingEdited(){
        _deletedReviewState.value = StateHandler(isNothing = true)
    }

    private val _profileState = MutableStateFlow(StateHandler<String>())
    val profileState = _profileState

    private val _reviews = MutableStateFlow(listOf<ReviewDetail>())
    val reviews = _reviews.asStateFlow()

    private var _genres = MutableStateFlow(listOf<String>())
    val genres = _genres.asStateFlow()

    init {
        getMovieDetails(id)
        getFavorites()
        getProfile()
        getGenres()
    }
    fun getMovieDetails(id:String){
        viewModelScope.launch {
            getMovieDetailsUseCase(id).collect{curState->
                when(curState){
                    is StateMachine.Error -> _mainState.value = StateHandler(isErrorOccured = false, message = curState?.message ?: "")
                    is StateMachine.Loading -> _mainState.value = StateHandler(isLoading = true)
                    is StateMachine.Success -> {
                        _mainState.value = StateHandler(isSuccess = true, value = curState.data?.toMovieDetail())

                        getPerson(curState.data?.director.toString().split(",")[0])
                        curState.data?.let {
                            getMovieInfo(
                                it.name,
                                it.year
                            )
                        }

                        curState?.data?.reviews?.forEach {it.createDateTime = castDate(it.createDateTime) }
                        _reviews.value = curState?.data?.reviews!!
                        getFriends()
                    }
                }
            }
        }
    }

    fun updateReviewsOnly(){
        viewModelScope.launch {
            getMovieReviewsUseCase(id).collect{curState->
                when(curState){
                    is StateMachine.Error -> Unit
                    is StateMachine.Loading -> Unit
                    is StateMachine.Success -> {
                        curState?.data?.forEach {it.createDateTime = castDate(it.createDateTime) }
                        _reviews.value = curState?.data!!
                    }
                }
            }
        }
    }

    fun getPerson(name:String){
        viewModelScope.launch {
            getPersonUseCase(name).collect{curState->
                _personState.value = when(curState){
                    is StateMachine.Error -> StateHandler(isErrorOccured = false, message = curState?.message ?: "")
                    is StateMachine.Loading -> StateHandler(isLoading = true)
                    is StateMachine.Success -> StateHandler(isSuccess = true, value = curState.data?.items?.get(0)?.posterUrl)
                }
            }
        }
    }

    fun getMovieInfo(title:String,year:Int){
        viewModelScope.launch {
            getMovieInfoUseCase(title,year).collect{curState->
                _ratingState.value = when(curState){
                    is StateMachine.Error -> StateHandler(isErrorOccured = false, message = curState?.message ?: "")
                    is StateMachine.Loading -> StateHandler(isLoading = true)
                    is StateMachine.Success -> StateHandler(isSuccess = true, value = listOf(
                        curState.data?.items?.get(0)?.ratingKinopoisk.toString(),curState.data?.items?.get(0)?.ratingImdb.toString() ))
                }
            }
        }
    }

    fun getFavorites(){
        viewModelScope.launch {
            getFavoriteMoviesUseCase().collect{curState->
                _favoritesState.value = when(curState){
                    is StateMachine.Error -> StateHandler(isErrorOccured = true, message = curState?.message ?: "")
                    is StateMachine.Loading -> StateHandler(isLoading = true)
                    is StateMachine.Success -> StateHandler(isSuccess = true, value = curState?.data?.any { it.id == id })
                }
            }
        }
    }

    fun addFavorites(id:String){
        viewModelScope.launch {
            addFavoritesUseCase(id).collect{curState->
                when(curState){
                    is StateMachineWithoutData.Error -> Unit
                    is StateMachineWithoutData.Loading -> Unit
                    is StateMachineWithoutData.Success -> _favoritesState.value = StateHandler(isSuccess = true, value = true)
                }
            }
        }
    }

    fun deleteFavorites(id:String){
        viewModelScope.launch {
            deleteFavoriteUseCase(id).collect{curState->
                when(curState){
                    is StateMachineWithoutData.Error -> Unit
                    is StateMachineWithoutData.Loading -> Unit
                    is StateMachineWithoutData.Success -> _favoritesState.value = StateHandler(isSuccess = true, value = false)
                }
            }
        }
    }

    fun addReview(movieId:String, isAnonymous:Boolean, rating:Int,reviewText:String){
        viewModelScope.launch {
            addReviewUseCase(movieId, UserReviewDto(isAnonymous,rating,reviewText)).collect{curState->
                when(curState){
                    is StateMachineWithoutData.Error -> Unit
                    is StateMachineWithoutData.Loading -> Unit
                    is StateMachineWithoutData.Success -> _addedReviewState.value = StateHandler(isSuccess = true)
                }
            }
        }
    }

    fun editReview(movieId:String, reviewId:String, isAnonymous:Boolean, rating:Int,reviewText:String){
        viewModelScope.launch {
            editReviewUseCase(movieId,reviewId, UserReviewDto(isAnonymous,rating,reviewText)).collect{curState->
                when(curState){
                    is StateMachineWithoutData.Error -> Unit
                    is StateMachineWithoutData.Loading -> Unit
                    is StateMachineWithoutData.Success -> _editedReviewState.value = StateHandler(isSuccess = true)
                }
            }
        }
    }

    fun deleteReview(movieId:String, reviewId:String){
        viewModelScope.launch {
            deleteReviewUseCase(movieId, reviewId).collect{curState->
                when(curState){
                    is StateMachineWithoutData.Error -> Unit
                    is StateMachineWithoutData.Loading -> Unit
                    is StateMachineWithoutData.Success -> _deletedReviewState.value = StateHandler(isSuccess = true)
                }

            }
        }
    }

    fun getProfile(){
        viewModelScope.launch {
            getProfileUseCase().collect{curState->
                _profileState.value = when(curState){
                    is StateMachine.Error -> StateHandler(isErrorOccured = true, message = curState.message.toString())
                    is StateMachine.Loading -> StateHandler(isLoading = true)
                    is StateMachine.Success -> StateHandler(isSuccess = true, value = curState.data?.toProfile()?.id)
                }
            }
        }
    }

    fun castDate(date:String):String{
        val dateTime = LocalDateTime.parse(date)

        val formatter = DateTimeFormatter.ofPattern("dd MM yyyy")
        val formattedDate = dateTime.format(formatter)

        val month = dateTime.monthValue
        val monthName = Month.values()[month-1]

        val finalDate = dateTime.dayOfMonth.toString()+ " " + monthName + " " + dateTime.year.toString()

        return finalDate
    }

    fun addFriend(userId:String, friendId:String, avatarLink:String?, name:String){
        viewModelScope.launch {
            addFriendUseCase(userId, friendId, avatarLink, name).collect()
            getFriends()
        }
    }

    fun addGenre(genreName:String){
        viewModelScope.launch {
            addGenreUseCase(userLogin,genreName).collect()
            getGenres()
        }
    }

    fun deleteGenre(genreName: String){
        viewModelScope.launch {
            deleteGenreUseCase(userLogin,genreName).collect()
            getGenres()
        }
    }

    fun getGenres(){
        viewModelScope.launch {
            getGenreUseCase(userLogin).collect{ curState ->
                when(curState){
                    is StateMachine.Error -> Unit
                    is StateMachine.Loading -> Unit
                    is StateMachine.Success -> {
                        _genres.value = curState.data?.map{it.genreName}?.toMutableList()!!
                    }
                }
            }
        }
    }

    fun getFriends(){
        viewModelScope.launch{
            getFriendsUseCase(userLogin).collect{curState->
                when(curState){
                    is StateMachine.Error -> Unit
                    is StateMachine.Loading -> Unit
                    is StateMachine.Success -> {
                        _friendsState.value.clear()
                        curState.data?.forEach { friend ->
                            val friendReview = _reviews.value.find { it.author.userId == friend.friendId }
                            if(friendReview!=null && friendReview.rating > 6){
                                _friendsState.value.add(friend)
                            }
                        }
                    }
                }
            }
        }
    }
}