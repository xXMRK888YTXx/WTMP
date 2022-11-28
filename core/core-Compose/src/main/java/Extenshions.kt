import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.remember

@Composable
inline fun <T> State<T>.remember() = remember { this }

inline fun <T> MutableState<T>.toState() : State<T> = this