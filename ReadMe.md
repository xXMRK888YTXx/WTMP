
# [En] Application Description

WTMP is an application designed to prevent unauthorized access to your device.
Application functionality:
- Tracking device unlocks
- Tracking of incorrect password entries
- Tracking openings of selected applications.
- Tracking device power on
  Once you get information about the selected event, you can take a picture of the intruder and send it to yourself in Telegram.

## 🔐 Security & Verification

To ensure the authenticity of the application, please verify the build signature. Official builds are signed with one of the following keys:

-   **Google Play Build:** SHA-256 Fingerprint: B5:A6:BA:22:F4:28:BC:49:0E:7A:F2:9B:E3:6C:E8:4A:41:1E:B9:31:7F:19:92:16:BD:DE:5E:B3:C6:3F:FA:76
-   **Personal Builds:** Signed with the personal key. You can find the valid signature in my [GitHub Profile ReadMe](https://github.com/xXMRK888YTXx).

> [!WARNING]
> Applications signed with any other keys are not built by me. Using such builds is highly discouraged unless you fully trust the provider.

# Project structure description

## [core] 
This module is used to store other modules with dependencies and common code that are often
are used. This module should not contain any code. Only necessary dependencies.

## [core:core-Android]
This module stores core dependencies and common code for Android, testing, viewModels

## [core:core-Compose]
This module includes [core:core-Android], used to store core dependencies + core Compose

## [AdminReceiver]  
This module contains a receiver that handles events that are not correct password and screen unlock

## [Camera]
The module contains the implementation of the interface for receiving photos from the device's camera

## [Api-Telegram]
This module contains api for sending messages through a bot to Telegram

## [Workers]
Workers are stored in this module to perform work in the background

## [MainScreen]
This module contains the main screen of the application

## [EventListScreen] 
This module contains a screen in which all events collected are displayed application

## [PackageInfoProvider]
This module contains the codebase for getting information about others applications

## [SettingsScreen]
This module contains the screen where the application settings are located

## [TelegramSetupScreen]
Telegram bot data entry screen

## [CryptoManager]
The module contains a class for data encryption

## [Database] 
Module with application database

## [BootReceiver]
The module contains a receiver to receive information about the device startup

## [EnterPasswordScreen]
The module contains a basic screen for entering a password to enter the application

## [EventDetailsScreen]
The module contains a screen that shows the details of events collected by the application

## [EventDeviceTracker]
The module contains a service that collects device events (screen unlock, application opening)

## [SelectTrackedAppScreen]
The module contains a screen for selecting applications to be tracked application

## [SetupAppPasswordScreen]
The module contains a specially configured [EnterPasswordScreen] to set / remove a password to enter the application


# [Ru] Описание приложения

WTMP — это приложение, предназначенное для предотвращения несанкционированного доступа к вашему устройству.
Функциональность приложения:
- Отслеживание разблокировок устройства
- Отслеживания неверных вводов пароля
- Отслеживание открытий выбранных вами приложений.
- Отслеживание включения устройства
Получив информацию о выбранном событии, вы можете сделать фотографию злоумышленника и отправить себе в Телеграм.

## 🔐 Безопасность и проверка

Для обеспечения подлинности приложения проверьте подпись сборки. Официальные версии подписываются одним из следующих ключей:

-   **Версия из Google Play:** SHA-256 Fingerprint: 11:B8:C9:72:70:0E:87:EB:4E:B9:00:4E:15:68:D8:72:32:87:2C:9F:0F:73:A5:3D:30:E7:CD:CF:49:AF:88:85
-   **Персональные сборки:** Подписаны личным ключом. Актуальную сигнатуру можно найти в моем [GitHub профиле](https://github.com/xXMRK888YTXx).

> [!WARNING]
> Приложения, подписанные любыми другими ключами, собраны не мной. Использование таких сборок крайне не рекомендуется, если вы не доверяете автору на 100%.


# Описание структуры проекта

## [core]
Этот модуль используется для хранения других модулей с зависимостями и общим кодом которые часто используются. В этом модуле,не должен находится какой либо код.Только необходимые зависимости.

## [core:core-Android]
В этом модуле хранятся core завивисимости и общий код для Android, тестирования, viewModels

## [core:core-Compose]
Этот модуль включает в себя [core:core-Android], используется для хранения core зависимостей + core Compose

## [AdminReceiver]
Этот модуль в котором находится ресивер который обрабатывает события не правильного пароля и разблокировки экрана

## [Camera]
В модуле находится реализация интерфейса для получение фотографий с камеры устройства

## [Api-Telegram]
В этом модуле находится api для отправки сообщений через бота в Telegram

## [Workers]
В этом модуле хранятся worker`ы для выполнение работы в фоне

## [MainScreen]
В этом модуле находится главный экран приложения

## [EventListScreen]
В этом модуле находится экран в котором, отображается всё события собранные приложением

## [PackageInfoProvider]
В этом модуле находится кодовая база для получения информации о других приложениях

## [SettingsScreen]
В этом модуле находится экран в котором, находятся настройки приложения

## [CryptoManager]
В модуле находится класс для шифрования данных

## [TelegramSetupScreen]
Экран для ввода данных бота Telegram

## [Database]
Модуль с базой данных приложения

## [BootReceiver]
В модуле находится ресивер для получения информации о запуске устройства

## [EnterPasswordScreen]
В модуле находится базовый экран для ввода пароля для входа в приложение

## [EventDetailsScreen]
В модуле находится экран который показывает подробности, событий собираемые приложением

## [EventDeviceTracker]
В модуле находится сервис который собирает события устройства(разблокировка экрана,открытие приложений)

## [SelectTrackedAppScreen]
В модуле находится экран для выбора приложений, которые будут отслеживаться приложением

## [SetupAppPasswordScreen]
В модуле находится специально настроенный [EnterPasswordScreen] для установки/удаление пароля для входа в приложение
