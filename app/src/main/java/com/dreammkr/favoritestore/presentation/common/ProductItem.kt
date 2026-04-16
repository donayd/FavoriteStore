package com.dreammkr.favoritestore.presentation.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.dreammkr.favoritestore.R
import com.dreammkr.favoritestore.domain.model.Product
import com.dreammkr.favoritestore.presentation.theme.LightBlue
import com.dreammkr.favoritestore.presentation.theme.Yellow

@Composable
fun ProductItem(
    product: Product,
    onProductClick: () -> Unit,
    onFavoriteClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onProductClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary),
        shape = RoundedCornerShape(28.dp)
    ) {
        Column {
            Box {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    color = Color.White,
                    shape = RoundedCornerShape(bottomStart = 28.dp, bottomEnd = 28.dp)
                ) {
                    AsyncImage(
                        model = product.image,
                        contentDescription = product.title,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(24.dp),
                        contentScale = ContentScale.Fit
                    )
                }

                Surface(
                    modifier = Modifier.align(Alignment.TopEnd),
                    color = LightBlue,
                    shape = RoundedCornerShape(bottomStart = 32.dp, topEnd = 28.dp)
                ) {
                    IconButton(
                        onClick = onFavoriteClick,
                        modifier = Modifier.padding(0.dp)
                    ) {
                        Icon(
                            imageVector = if (product.isFavorite) Icons.Default.Star else Icons.Default.StarBorder,
                            contentDescription = stringResource(R.string.favorite),
                            tint = if (product.isFavorite) Yellow else MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            }

            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = product.title,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "$${product.price}",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.White.copy(alpha = 0.8f),
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}