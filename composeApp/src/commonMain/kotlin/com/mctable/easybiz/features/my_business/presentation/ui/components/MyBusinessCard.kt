package com.mctable.easybiz.features.my_business.presentation.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.mctable.easybiz.core.ds.theme.EasyBizTheme
import com.mctable.easybiz.core.ds.utils.AppIcons
import com.mctable.easybiz.features.my_business.domain.entity.MyBusinessEntity

@Composable
fun MyBusinessCard(
    business: MyBusinessEntity,
    onClick: () -> Unit,
    onEditClick: () -> Unit
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp)
    ) {

        Row(
            modifier = Modifier
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            business.logoUrl?.let {
                AsyncImage(
                    model = it,
                    contentDescription = null,
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            } ?: run {
                Icon(
                    AppIcons.contactEmail(),
                    null,
                    modifier = Modifier
                        .size(120.dp),
                )
            }

            Spacer(Modifier.width(12.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {

                Text(
                    text = business.name,
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(Modifier.height(4.dp))

                Text(
                    text = business.category,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            TextButton(
                onClick = onEditClick
            ) {
                Text("Editar")
            }
        }
    }
}

@Preview
@Composable
fun MyBusinessCardPreview() {

    val business = MyBusinessEntity(
        id = 1,
        name = "Mecânica do Ricardo",
        category = "Mecânico",
        userId = 10,
        userName = "Ricardo",
        active = true,
        latitude = -23.561684,
        longitude = -46.625378,
        completeAddress = "Av. Paulista, 1500 - Bela Vista - São Paulo - SP",
        averageRating = 4.8,
        logoUrl = "https://randomuser.me/api/portraits/men/32.jpg",
        distanceKm = 2.5,
        description = "Especialista em motores e revisão completa.",
        telephone = "(11) 99999-9999",
        minimumPrice = 150.0,
        yearsOfExperience = 10,
        workingHours = "08:00 - 18:00",
        totalReviews = 124,
        totalCompletedOrders = 320,
        highlightReview = "Excelente atendimento!",
        highlightReviewAuthor = "João Silva",
        isFavorited = true
    )

    EasyBizTheme {

        MyBusinessCard(
            business = business,
            onClick = {},
            onEditClick = {}
        )
    }
}