import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.remember

@Composable
inline fun <reified T : Any> T.remember() = remember { this }

inline fun <reified T> MutableState<T>.toState() : State<T> = this