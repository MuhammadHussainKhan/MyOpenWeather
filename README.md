# Weather
[![Kotlin](https://img.shields.io/badge/kotlin-1.9.0-blue.svg?logo=kotlin)](http://kotlinlang.org)
[![Gradle](https://img.shields.io/badge/Gradle-8-green?style=flat)](https://gradle.org)
<a href="https://github.com/MuhammadHussainKhan"><img alt="License" src="https://img.shields.io/static/v1?label=GitHub&message=MuhammadHussainKhan&color=C51162"/></a><br>
Weather is an Android app for Weather with Jetpack Compose. It uses [openweathermap API](https://openweathermap.org/api). It is single screen app which displays weather data for a city.
There is a search function in the top app bar to search for any city and display its weather data.
# Weather App

## 📐✏️ Architecture

The Weather App is built using the **Clean Architecture** pattern, ensuring a modular, maintainable, and testable codebase. This architecture pattern separates the application into multiple layers, each with a distinct responsibility, promoting separation of concerns and making the app easier to scale and maintain.

### Layers of Clean Architecture

1. **Presentation Layer**
  - Contains the UI components and `ViewModel` classes.
  - The `ViewModel` acts as a bridge between the UI and the domain layer, exposing data through `StateFlow` or `LiveData` and handling user interactions.
  - Jetpack Compose is used to build the UI, displaying weather data and user interactions.

2. **Domain Layer**
  - This is the core layer of the app, containing business logic and application-specific rules.
  - It consists of **Use Cases (Interactors)**, which encapsulate specific business logic and coordinate the flow of data between the repository and the `ViewModel`.
  - The domain layer is independent of any other layer, making it reusable and easy to test.

3. **Data Layer**
  - The data layer is responsible for managing application data and includes data sources such as remote APIs, local databases, and the **Repository**.
  - The repository pattern is used to provide a clean API for data access to the domain layer, abstracting away the complexity of data fetching and caching.

### Data Flow in the App

1. **User Interaction**:
  - The user interacts with the app through the UI (e.g., searching for a city’s weather).

2. **ViewModel**:
  - The `ViewModel` processes user actions and calls the appropriate Use Case from the domain layer to fetch or update data.

3. **Use Cases**:
  - Use Cases execute business logic and request data from the repository.

4. **Repository**:
  - The repository handles data operations, such as fetching from the OpenWeatherMap API or a local database, and returns the data to the Use Case.

5. **Data Back to ViewModel**:
  - The Use Case returns the processed data back to the `ViewModel`, which then updates the UI state.

6. **Update UI**:
  - The `ViewModel` provides the updated data to the UI, which is then displayed to the user using Jetpack Compose.

### Benefits of Clean Architecture

- **Separation of Concerns**: Each layer has a single responsibility, making the code easier to understand and maintain.
- **Testability**: The separation of business logic from UI components makes it easier to write unit tests for Use Cases and ViewModels.
- **Scalability**: Adding new features or changing existing ones is easier, as each layer is independent and can evolve separately.
- **Flexibility**: The data layer can switch between different data sources (e.g., remote API, local database) without affecting the domain or presentation layers.

### Technology Stack

- **Kotlin**: Primary programming language.
- **Jetpack Compose**: For building declarative UI components.
- **ViewModel & StateFlow/LiveData**: For managing and observing UI-related data.
- **Retrofit**: For making network requests to the OpenWeatherMap API.
- **Hilt**: For dependency injection.
- **Coroutines**: For handling asynchronous operations.

### How to Run the App

## 🛠 Tech Stack
- [Kotlin](https://developer.android.com/kotlin) - Most of the Android community uses Kotlin as their preferred choice of language.
- Jetpack:
    - [Jetpack Compose](https://developer.android.com/jetpack/compose) - Jetpack Compose is Android’s modern toolkit for building native UI. It simplifies and accelerates UI development on Android. Quickly bring your app to life with less code, powerful tools, and intuitive Kotlin APIs.
    - [Android KTX](https://developer.android.com/kotlin/ktx.html) - Android KTX is a set of Kotlin extensions that are included with Android Jetpack and other Android libraries. KTX extensions provide concise, idiomatic Kotlin to Jetpack, Android platform, and other APIs.
    - [AndroidX](https://developer.android.com/jetpack/androidx) - The androidx namespace comprises the Android Jetpack libraries. It's a major improvement to the original Android [Support Library](https://developer.android.com/topic/libraries/support-library/index), which is no longer maintained.
    - [Lifecycle](https://developer.android.com/topic/libraries/architecture/lifecycle) - Lifecycle-aware components perform actions in response to a change in the lifecycle status of another component, such as activities and fragments. These components help you produce better-organized, and often lighter-weight code, that is easier to maintain.
    - [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - The ViewModel class is a business logic or screen level state holder. It exposes state to the UI and encapsulates related business logic. Its principal advantage is that it caches state and persists it through configuration changes.
    - [SharedPrefrence](https://developer.android.com/reference/android/content/SharedPreferences) - SharedPreferences is best suited to storing data about how the user prefers to experience the app.
- [Dagger Hilt](https://developer.android.com/training/dependency-injection/hilt-android) - A dependency injection library for Android that reduces the boilerplate of doing manual dependency injection in your project.
- [Kotlin Coroutines](https://developer.android.com/kotlin/coroutines) - A concurrency design pattern that you can use on Android to simplify code that executes asynchronously and it's the recommended way for asynchronous programming on Android.
- [Kotlin Flow](https://developer.android.com/kotlin/flow) - In coroutines, a flow is a type that can emit multiple values sequentially, as opposed to suspend functions that return only a single value.
- [Retrofit](https://square.github.io/retrofit) - Retrofit is a REST client for Java/ Kotlin and Android by Square. Its a simple network library that is used for network transactions.
- [OkHttp](https://github.com/square/okhttp) - OkHttp is an HTTP client. It perseveres when the network is troublesome as it will silently recover from common connection problems.
- [GSON](https://github.com/google/gson) - JSON Parser, used to parse requests on the data layer for Entities and understands Kotlin non-nullable and default parameters.
- [Logging Interceptor](https://github.com/square/okhttp/blob/master/okhttp-logging-interceptor/README.md) - An OkHttp interceptor which logs HTTP request and response data.
- [Coil](https://coil-kt.github.io/coil/compose/)- An image loading library for Android backed by Kotlin Coroutines.
- [Timber](https://github.com/JakeWharton/timber)- A logger with a small, extensible API which provides utility on top of Android's normal Log class.
- [Mockk](https://github.com/mockk/mockk)- A mocking library for Kotlin
- [Turbine](https://github.com/cashapp/turbine)- A small testing library for kotlinx.coroutines Flow

1. Clone the repository:
   ```bash
   git clone https://github.com/MuhammadHussainKhan/MyOpenWeather.git

## 📱 Screenshots

<img src="https://github.com/MuhammadHussainKhan/MyOpenWeather/blob/master/assets/Screenshot_20240922_192628.png" width="40%"/></a>


## Demo

https://github.com/MuhammadHussainKhan/MyOpenWeather/blob/master/assets/screen-recording-myopenweather.mp4



