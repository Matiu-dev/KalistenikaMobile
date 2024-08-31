package pl.matiu.kalistenika.history

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import pl.matiu.kalistenika.ui.theme.InsideLevel1
import pl.matiu.kalistenika.ui.theme.Smola
import pl.matiu.kalistenika.ui.theme.ZielonyNapis

@Composable
fun HistoryScreen() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = InsideLevel1,
    ) {

        //TODO https://m3.material.io/components/date-pickers/overview
        //https://developer.android.com/develop/ui/compose/components/snackbar po dodaniu nowego treningu
//        Column(modifier = Modifier.fillMaxWidth()) {
//            Text(
//                text = "Historia",
//                fontSize = 50.sp,
//                color = Smola,
//                textAlign = TextAlign.Center,
//                modifier = Modifier.fillMaxWidth()
//            )
//        }

    }
}