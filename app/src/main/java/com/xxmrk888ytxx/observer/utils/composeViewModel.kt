import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * [Ru]
 * Данная функция предназначена для, того что бы созданная viewModel
 * была привязана к жизненному циклу экрана(не Activity, а к NavBackStackEntry, или если ещё проще,
 * то что бы viewModel жила пока пользователь окончательно не уйдет с экрана)
 */
/**
 * [En]
 * This function is intended to ensure that the created viewModel
 * was tied to the screen life cycle (not Activity, but to NavBackStackEntry, or even simpler,
 * so that the viewModel would live until the user finally leaves the screen)
 */
@Suppress("UNCHECKED_CAST")
@Composable
inline fun <reified T : ViewModel> composeViewModel(
    key: String? = null,
    crossinline viewModelInstanceCreator: () -> T
): T = androidx.lifecycle.viewmodel.compose.viewModel (
    modelClass = T::class.java,
    key = key,
    factory = object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return viewModelInstanceCreator() as T
        }
    }
)