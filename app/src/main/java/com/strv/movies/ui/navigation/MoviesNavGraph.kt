package com.strv.movies.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.strv.movies.data.OfflineMoviesProvider
import com.strv.movies.ui.moviedetail.MovieDetail
import com.strv.movies.ui.movieslist.MoviesList
import com.strv.movies.ui.navigation.MoviesDestinations.MOVIES_LIST
import com.strv.movies.ui.navigation.MoviesDestinations.MOVIE_DETAIL
import com.strv.movies.ui.navigation.MoviesNavArguments.MOVIE_ID_KEY

@Composable
fun MoviesNavGraph(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = MOVIES_LIST
    ) {
        composable(MOVIES_LIST) {
            MoviesList(
                movies = OfflineMoviesProvider.getMovies(),
                onMovieClick = { movieId ->
                    navController.navigate("${MOVIE_DETAIL}/$movieId")
                }
            )
        }
        composable(
            route = "$MOVIE_DETAIL/{$MOVIE_ID_KEY}",
            arguments = listOf(
                navArgument(MOVIE_ID_KEY) {
                    type = NavType.IntType
                }
            )
        ) {
            it.arguments?.getInt(MOVIE_ID_KEY)?.let {
                MovieDetail(movie = OfflineMoviesProvider.getMovieDetail(it))
            }
        }
    }
}