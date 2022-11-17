
object Project {
    const val App = ":app"
    sealed class libs(val route:String) {
        object core : libs(":libs:core-Android")
        object core_Compose : libs(":libs:core-Compose")
    }
}