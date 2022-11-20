
object Project {
    const val App = ":app"
    const val AdminReceiver = ":AdminReceiver"
    const val Camera = ":Camera"
    const val UserActivityStats = ":UserActivityStats"
    sealed class core(val route:String) {
        object core_Android : core(":core:core-Android")
        object core_Compose : core(":core:core-Compose")
    }
}