package com.mctable.easybiz.features.search_business.presentation.ui.organisms

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
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
    modifier: Modifier = Modifier
) {

    Card(
        modifier = Modifier.fillMaxWidth().clickable(
            true,
            "Conversar",
            onClick = onChatClick
        ).then(modifier),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {

        ListItem(
            headlineContent = {
                Text(name)
            },
            supportingContent = {
                Column {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = AppIcons.LocationOn(),
                            contentDescription = null,
                            modifier = Modifier.size(16.dp),
                            tint = Color.Blue
                        )

                        Spacer(Modifier.width(4.dp))

                        Text(distance)

                    }

                    Spacer(Modifier.height(4.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Text(profession)

                        Spacer(Modifier.width(6.dp))

                        Text("•")

                        Spacer(Modifier.width(6.dp))

                        Icon(
                            painter = AppIcons.Star(),
                            contentDescription = null,
                            modifier = Modifier.size(16.dp),
                            tint = Color.Yellow.copy(alpha = 0.7f)
                        )

                        Spacer(Modifier.width(4.dp))

                        Text(rating.toString())
                    }

                }
            },

            trailingContent = {
                imageUrl?.let {
                    AsyncImage(
                        model = imageUrl,
                        contentDescription = name,
                        modifier = Modifier
                            .size(72.dp)
                    )
                }
            }
        )
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
            onChatClick = {}
        )
    }
}