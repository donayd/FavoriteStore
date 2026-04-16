package com.dreammkr.favoritestore

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.NavigationRailItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffoldDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteType
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.dreammkr.favoritestore.presentation.detail.ProductDetailScreen
import com.dreammkr.favoritestore.presentation.detail.ProductDetailState
import com.dreammkr.favoritestore.presentation.detail.ProductDetailViewModel
import com.dreammkr.favoritestore.presentation.favorites.FavoritesScreen
import com.dreammkr.favoritestore.presentation.products.ProductListScreen
import com.dreammkr.favoritestore.presentation.profile.ProfileScreen
import com.dreammkr.favoritestore.presentation.theme.FavoriteStoreTheme
import com.dreammkr.favoritestore.presentation.theme.Yellow
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FavoriteStoreTheme {
                MainScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val suiteItemColors = NavigationSuiteDefaults.itemColors(
        navigationBarItemColors = NavigationBarItemDefaults.colors(
            selectedIconColor = MaterialTheme.colorScheme.primary,
            selectedTextColor = MaterialTheme.colorScheme.primary,
            unselectedIconColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
            unselectedTextColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
            indicatorColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
        ),
        navigationRailItemColors = NavigationRailItemDefaults.colors(
            selectedIconColor = MaterialTheme.colorScheme.primary,
            selectedTextColor = MaterialTheme.colorScheme.primary,
            unselectedIconColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
            unselectedTextColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
            indicatorColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
        )
    )

    val productsLabel = stringResource(id = R.string.products)
    val favoritesLabel = stringResource(id = R.string.favorites)
    val profileLabel = stringResource(id = R.string.profile)

    val isDetailScreen = currentDestination?.route == "detail/{productId}"
    val adaptiveInfo = currentWindowAdaptiveInfo()

    NavigationSuiteScaffold(
        layoutType = if (isDetailScreen) {
            NavigationSuiteType.None
        } else {
            NavigationSuiteScaffoldDefaults.calculateFromAdaptiveInfo(adaptiveInfo)
        },
        navigationSuiteItems = {
            val items = listOf(
                Screen.Products(productsLabel),
                Screen.Favorites(favoritesLabel),
                Screen.Profile(profileLabel)
            )
            items.forEach { screen ->
                item(
                    icon = { Icon(screen.icon, contentDescription = screen.label) },
                    label = { Text(screen.label) },
                    selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                    colors = suiteItemColors,
                    onClick = {
                        navController.navigate(screen.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        },
        navigationSuiteColors = NavigationSuiteDefaults.colors(
            navigationBarContainerColor = Color.White,
            navigationRailContainerColor = Color.White
        )
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = if (isDetailScreen) stringResource(id = R.string.product_detail)
                            else stringResource(id = R.string.app_name),
                            color = Color.White
                        )
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    ),
                    navigationIcon = {
                        if (isDetailScreen) {
                            IconButton(onClick = { navController.popBackStack() }) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                    contentDescription = stringResource(id = R.string.back),
                                    tint = Color.White
                                )
                            }
                        }
                    },
                    actions = {
                        if (isDetailScreen) {
                            val detailViewModel: ProductDetailViewModel =
                                hiltViewModel(navBackStackEntry!!)
                            val state by detailViewModel.state.collectAsState()

                            if (state is ProductDetailState.Success) {
                                val product = (state as ProductDetailState.Success).product
                                IconButton(onClick = { detailViewModel.toggleFavorite() }) {
                                    Icon(
                                        imageVector = if (product.isFavorite) Icons.Default.Star else Icons.Default.StarBorder,
                                        contentDescription = stringResource(id = R.string.favorite),
                                        tint = if (product.isFavorite) Yellow else Color.White
                                    )
                                }
                            }
                        }
                    },
                )
            }
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = "products",
                modifier = Modifier.padding(innerPadding),
                enterTransition = {
                    fadeIn(animationSpec = tween(300)) + slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Left,
                        animationSpec = tween(300)
                    )
                },
                exitTransition = {
                    fadeOut(animationSpec = tween(300)) + slideOutOfContainer(
                        AnimatedContentTransitionScope.SlideDirection.Left,
                        animationSpec = tween(300)
                    )
                },
                popEnterTransition = {
                    fadeIn(animationSpec = tween(300)) + slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Right,
                        animationSpec = tween(300)
                    )
                },
                popExitTransition = {
                    fadeOut(animationSpec = tween(300)) + slideOutOfContainer(
                        AnimatedContentTransitionScope.SlideDirection.Right,
                        animationSpec = tween(300)
                    )
                }
            ) {
                composable("products") {
                    ProductListScreen(
                        onProductClick = { productId ->
                            navController.navigate("detail/$productId")
                        }
                    )
                }
                composable("favorites") {
                    FavoritesScreen(
                        onProductClick = { productId ->
                            navController.navigate("detail/$productId")
                        }
                    )
                }
                composable("profile") {
                    ProfileScreen()
                }
                composable(
                    route = "detail/{productId}",
                    arguments = listOf(navArgument("productId") { type = NavType.IntType })
                ) {
                    ProductDetailScreen()
                }
            }
        }
    }
}

sealed class Screen(val route: String, val label: String, val icon: ImageVector) {
    class Products(label: String) : Screen("products", label, Icons.Default.Home)
    class Favorites(label: String) : Screen("favorites", label, Icons.Default.Star)
    class Profile(label: String) : Screen("profile", label, Icons.Default.AccountCircle)
}
