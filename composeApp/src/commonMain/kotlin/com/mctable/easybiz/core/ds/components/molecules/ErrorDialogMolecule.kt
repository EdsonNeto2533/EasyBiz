package com.mctable.easybiz.core.ds.components.molecules

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mctable.easybiz.core.ds.components.atoms.ButtonAtom
import com.mctable.easybiz.core.ds.theme.EasyBizTheme

@Composable
fun ErrorDialogMolecule(
    title: String = "Algo deu errado",
    description: String = "Não foi possível completar sua solicitação.",
    buttonLabel: String = "Tentar novamente",
    onTryAgain: () -> Unit
) {

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(24.dp))

            ButtonAtom(
                text = buttonLabel,
                onClick = onTryAgain,
                modifier = Modifier.padding(horizontal = 32.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun GenericErrorDialogPreview() {
    EasyBizTheme {
        ErrorDialogMolecule(
            title = "Algo deu errado",
            description = "Não conseguimos completar sua solicitação agora.",
            onTryAgain = {}
        )
    }
}