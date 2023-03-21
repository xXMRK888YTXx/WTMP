import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.xxmrk888ytxx.coredeps.models.WeekDay
import com.xxmrk888ytxx.coredeps.models.WeekDay.Companion.toStringLiteral

@Composable
inline fun <reified T : Any> T.remember() = remember { this }

inline fun <reified T> MutableState<T>.toState() : State<T> = this

@Composable
fun WeekDay.toStringLiteral() : String {
    val context = LocalContext.current

    return toStringLiteral(context)
}