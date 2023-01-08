package com.xxmrk888ytxx.eventdetailsscreen

import MutliUse.LazySpacer
import SharedInterfaces.Navigator
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.xxmrk888ytxx.coredeps.models.DeviceEvent
import com.xxmrk888ytxx.coredeps.toDateString
import com.xxmrk888ytxx.coredeps.toTimeString
import remember
import theme.cardColor
import theme.floatButtonColor
import theme.openSansFont
import theme.primaryFontColor

@Composable
fun EventDetailsScreen(eventDetailsViewModel: EventDetailsViewModel, navigator: Navigator) {
    val screenState = eventDetailsViewModel.screenState.remember()
    Scaffold(
        topBar = {
            TopBar(navigator)
        },
        backgroundColor = Color.Transparent,
        floatingActionButton = {
            if (screenState.value is ScreenState.ShowEvent
                &&
                (screenState.value as ScreenState.ShowEvent).image != null
            ) {
                FloatingActionButton(
                    onClick = { eventDetailsViewModel.openInGalleryCurrentImage() },
                    backgroundColor = floatButtonColor
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_open_image),
                        contentDescription = "",
                        tint = primaryFontColor,
                        modifier = Modifier.size(25.dp)
                    )
                }
            }
        }
    ) { paddings ->
        Column(
            modifier = Modifier.padding(
                start = paddings.calculateStartPadding(LocalLayoutDirection.current),
                end = paddings.calculateEndPadding(LocalLayoutDirection.current),
                bottom = paddings.calculateBottomPadding(),
                top = paddings.calculateTopPadding()
            )
        ) {
            when (screenState.value) {

                is ScreenState.ShowEvent -> {
                    LazySpacer(30)
                    ShowEventInfo((screenState.value as ScreenState.ShowEvent).event)
                    CreatedImageViewer(eventDetailsViewModel)
                }

                is ScreenState.Loading -> {

                }
            }
        }
    }
}

@Composable
fun CreatedImageViewer(eventDetailsViewModel: EventDetailsViewModel) {
    if (eventDetailsViewModel.screenState.value is ScreenState.ShowEvent
        && (eventDetailsViewModel.screenState.value as ScreenState.ShowEvent).image != null
    ) {
        AsyncImage(
            model = (eventDetailsViewModel.screenState.value as? ScreenState.ShowEvent)?.image,
            contentDescription = "",
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
        )
    } else {
        PhotoStub()
    }
}

@Composable
internal fun PhotoStub() {
    Box(
        Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_no_photography),
                contentDescription = "",
                tint = primaryFontColor,
                modifier = Modifier.size(50.dp)
            )

            LazySpacer(10)

            Text(
                text = stringResource(R.string.Photo_missing),
                fontFamily = openSansFont,
                fontSize = 22.sp,
                fontWeight = FontWeight.W600,
                color = primaryFontColor
            )
        }
    }
}

@Composable
internal fun EventCardInfo(event: DeviceEvent) {
    val defaultIcon: Painter = when (event) {

        is DeviceEvent.AttemptUnlockDevice -> when (event) {
            is DeviceEvent.AttemptUnlockDevice.Failed -> painterResource(R.drawable.ic_phone_lock)
            is DeviceEvent.AttemptUnlockDevice.Succeeded -> painterResource(R.drawable.ic_lock_open)
        }

        is DeviceEvent.AppOpen -> painterResource(R.drawable.default_icon)

        is DeviceEvent.DeviceLaunch -> painterResource(R.drawable.ic_off)
    }

    val text = when (event) {
        is DeviceEvent.AttemptUnlockDevice -> when (event) {
            is DeviceEvent.AttemptUnlockDevice.Failed -> stringResource(R.string.Failed_to_unlock)
            is DeviceEvent.AttemptUnlockDevice.Succeeded -> stringResource(R.string.Device_unlocked)
        }

        is DeviceEvent.AppOpen -> event.appName ?: event.packageName

        is DeviceEvent.DeviceLaunch -> stringResource(R.string.Device_loaded)
    }
    val context = LocalContext.current

    Card(
        backgroundColor = cardColor,
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = 15.dp,
                end = 15.dp,
            ),
        shape = RoundedCornerShape(10.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(10.dp)
        ) {
            if (event is DeviceEvent.AppOpen) {
                AsyncImage(
                    model = event.icon ?: R.drawable.default_icon,
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(60.dp)
                )
            } else {
                Icon(
                    painter = defaultIcon,
                    contentDescription = "",
                    tint = primaryFontColor,
                    modifier = Modifier.size(35.dp)
                )
            }

            Text(
                text = text,
                color = primaryFontColor,
                fontFamily = openSansFont,
                fontSize = 20.sp,
                fontWeight = FontWeight.W600,
            )

            Text(
                text = event.time.toDateString(context),
                color = primaryFontColor,
                fontWeight = FontWeight.W600,
                fontSize = 20.sp,
                fontFamily = openSansFont
            )

            Text(
                text = event.time.toTimeString(),
                color = primaryFontColor,
                fontWeight = FontWeight.W600,
                fontSize = 18.sp,
                fontFamily = openSansFont
            )
        }
    }


}

@Composable
internal fun ShowEventInfo(event: DeviceEvent) {
    EventCardInfo(event)

}

@Composable
internal fun TopBar(navigator: Navigator) {
    Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        IconButton(onClick = navigator::navigateUp) {
            Icon(
                painter = painterResource(R.drawable.ic_back_arrow),
                contentDescription = "",
                tint = primaryFontColor,
                modifier = Modifier.size(25.dp)
            )
        }

        LazySpacer(width = 15)

        Text(
            stringResource(R.string.Event_details),
            fontSize = 27.sp,
            fontWeight = FontWeight.W600,
            color = Color.White,
            fontFamily = openSansFont,
        )
    }
}
