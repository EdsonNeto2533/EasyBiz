package com.mctable.easybiz.features.search_business.presentation.ui.organisms

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mctable.easybiz.core.ds.components.atoms.AvatarAtom
import com.mctable.easybiz.core.ds.components.atoms.PillAtom
import com.mctable.easybiz.core.ds.components.atoms.PillType
import com.mctable.easybiz.core.ds.components.atoms.RatingAtom
import com.mctable.easybiz.core.ds.theme.Dimens
import com.mctable.easybiz.core.ds.theme.EasyBizTheme
import com.mctable.easybiz.core.ds.utils.AppIcons

@Composable
fun ProfessionalCardOrganism(
    name: String,
    profession: String,
    rating: Double,
    distance: String,
    imageUrl: String?,
    onChatClick: () -> Unit,
    onFavoriteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var isFavorite by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                true,
                "Conversar",
                onClick = onChatClick
            )
            .then(modifier),
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = Dimens.cardElevation)
    ) {
        Row(
            modifier = Modifier.padding(Dimens.cardPadding),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AvatarAtom(
                imageUrl = imageUrl,
                contentDescription = name,
                size = Dimens.avatarSizeLg,
                showOnlineIndicator = true
            )

            Spacer(Modifier.width(Dimens.spacingMd))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = name,
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(Modifier.height(Dimens.spacingXs))

                PillAtom(
                    pillType = PillType.Ghost,
                    text = profession
                )

                Spacer(Modifier.height(Dimens.spacingSm))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = AppIcons.locationOn(),
                        contentDescription = null,
                        modifier = Modifier.size(Dimens.iconSizeSm),
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(Modifier.width(4.dp))
                    Text(
                        text = distance,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(Modifier.width(Dimens.spacingMd))

                    RatingAtom(rating = rating)
                }
            }

            IconButton(onClick = onFavoriteClick) {
                Icon(painter = AppIcons.star(), "")
            }
        }
    }
}


@Preview
@Composable
private fun ProfessionalCardOrganismPreview() {

    EasyBizTheme {

        ProfessionalCardOrganism(
            name = "Carlos Silva",
            profession = "Mecânico",
            rating = 4.8,
            distance = "2km de você",
            imageUrl = "https://picsum.photos/200",
            onChatClick = {},
            onFavoriteClick = {}
        )
    }
}
