package com.aulasandroid.navegacaofluxotelas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.animate
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.aulasandroid.navegacaofluxotelas.screens.LoginScreen
import com.aulasandroid.navegacaofluxotelas.screens.MenuScreen
import com.aulasandroid.navegacaofluxotelas.screens.PedidosScreen
import com.aulasandroid.navegacaofluxotelas.screens.PerfilScreen
import com.aulasandroid.navegacaofluxotelas.ui.theme.NavegacaoFluxoTelasTheme
import java.time.temporal.Temporal

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NavegacaoFluxoTelasTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = "login",
                        exitTransition = {
                            slideOutOfContainer(
                                towards = AnimatedContentTransitionScope.SlideDirection.Left,
                                animationSpec = tween(1000)
                            ) + fadeOut(animationSpec = tween(1000))
                        },
                        popEnterTransition = {
                            slideIntoContainer(
                                towards = AnimatedContentTransitionScope.SlideDirection.Right,
                                animationSpec = tween(500)
                            )
                        },

                        popExitTransition = {
                            slideOutOfContainer(
                                towards = AnimatedContentTransitionScope.SlideDirection.Right,
                                animationSpec = tween(500)
                            )
                        },
                        enterTransition = {
                            slideIntoContainer(
                                towards = AnimatedContentTransitionScope.SlideDirection.Left,
                                animationSpec = tween(
                                    durationMillis = 500
                                )
                            )
                        }
                    ) {
                        composable(route = "login") {
                            LoginScreen(navController)
                        }

                        composable(route = "menu") {
                            MenuScreen(navController)
                        }

                        composable(
                            route = "perfil/{name}/{idade}",
                            arguments = listOf(
                                navArgument(name = "name") {
                                    type = NavType.StringType
                                },
                                navArgument(name = "idade") {
                                    type = NavType.IntType
                                }
                            )
                        ) {
                            val name = it.arguments?.getString("name")
                            val idade = it.arguments?.getInt("idade")

                            PerfilScreen(
                                navController = navController,
                                name = name!!,
                                idade = idade!!
                            )
                        }

                        composable(
                            route = "pedidos?numeroPedido={numeroPedido}",
                            arguments = listOf(
                                navArgument("numeroPedido") {
                                    defaultValue = "Sem pedidos"
                                }
                            )
                        ) {
                            val numeroPedido = it.arguments?.getString("numeroPedido")

                            PedidosScreen(
                                navController,
                                numeroPedido = numeroPedido!!
                            )
                        }
                    }
                }
            }
        }
    }
}