package theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

val Purple200 = Color(0xFFBB86FC)
val Purple500 = Color(0xFF6200EE)
val Purple700 = Color(0xFF3700B3)
val Teal200 = Color(0xFF03DAC5)
val primaryFontColor = Color(0xFFFFFFFF)
val secondoryFontColor = Color.Gray
val BackGroundColor = Color(0xFF1B252D)
val cardColor = Color(0xFF25313D)
val enableAppButtonColor = Brush.linearGradient(listOf(Color(0xFF5849C2),Color(0xFF4871CC)))
val disableAppButtonColor = Brush.linearGradient(listOf(cardColor,cardColor))
val enableAppButtonFontColor = primaryFontColor
val disableAppButtonFontColor = Color(0xFFC64851)
val timeTextColor = Color(0xFFEAEAEA)
val settingsSeparatorLineColor = Color(0xFF303F4F).copy(0.7f)
val checkedSettingsSwitch = Color(0xFF5849C2)
val uncheckedSettingsSwitch = Color(0xFFE6E6E6)
val snackbarColor = Color(0xFF2C3947)
val okColor = Color(0xFF35862B)
val errorColor = Color(0xFFC64851)
val floatButtonColor = checkedSettingsSwitch
val dropDownColor = Color(0xFF2C3947)
val selectedRadioButtonColor = Color(0xFF5849C2)
val unselectedRadioButtonColor = Color(0xFF303F4F)