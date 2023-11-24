# Task Management App

This repository contains the source code for a Task Management App developed in Kotlin for Android using the MVVM architecture. The app leverages modern Android development principles, such as Room Database for local storage, Retrofit for network requests, Dagger Hilt for dependency injection, and WorkManager for periodic data synchronization.

## Architecture Overview

### Model-View-ViewModel (MVVM)

The app follows the MVVM architectural pattern, separating concerns between the UI (View), business logic (ViewModel), and data handling (Model). This helps maintain a clean and modular codebase.

### Room Database

The local storage is managed using Room Database, an SQLite object mapping library. The `CharacterModel` class represents the entity stored in the database, and the `CharacterDAO` interface defines the data access methods.

### Retrofit and API Service

Retrofit is used for handling network requests, and an `ApiService` interface defines the API endpoints. The `CustomRemoteDataSource` class utilizes Retrofit to authenticate users and fetch data from the remote server.

### WorkManager for Periodic Sync

WorkManager is employed to periodically synchronize data in the background. The `RefreshWorker` class, triggered by WorkManager, handles the authorization process and updates local data.

### Dagger Hilt for Dependency Injection

Dagger Hilt is utilized for dependency injection, providing a clean and modular way to manage dependencies. Modules such as `DatabaseModule` and `NetworkModule` are defined to provide instances of necessary components.

## Usage

### Features

- **Task List:** Display tasks with details such as title, description, and color code.
- **Search Functionality:** Search tasks based on various criteria like task name, description, and color code.
- **QR Code Scanner:** Integrate QR code scanning functionality using the device's camera.

### Getting Started

1. Clone the repository.
2. Open the project in Android Studio.
3. Build and run the app on an Android device or emulator.

### Dependencies

- **Room Database:** For local data storage and retrieval.
- **Retrofit:** For handling network requests.
- **Dagger Hilt:** For dependency injection.
- **WorkManager:** For background synchronization tasks.

## Development Environment

- **Language:** Kotlin
- **Minimum Android SDK:** 21
- **Android Studio Version:** 4.0 or higher

***********************************************************************************************

Hello dear Android dev prospect!

This repository is supposed to act as a playground for your submission.
Before getting started, please make sure to clone this repository on which you will commit and push your code regularly. Once you are ready, please mail us back the link to your repository. 

Below, you will find the **Task** definition. Happy Hacking :computer: and Good Luck :shamrock:

# Task

Write an Android application that connects to a remote API, downloads a certain set of resources, shows them in a list and provides some basic searching/filtering feature-set.
In particular, the app should:

- Request the resources located at `https://api.baubuddy.de/dev/index.php/v1/tasks/select` 
- Store them in an appropriate data structure that allows using the application offline
- Display all items in a list showing `task`, `title`, `description` and `colorCode` (which should be a view colored according to `colorCode`)
- The app should offer a search menu item that allows searching for any of the class properties (even those, that are not visible to the user directly)
- The app should offer a menu item that allows scanning for QR-Codes
  - Upon successful scan, the search query should be set to the scanned text
- In order to refresh the data, the app should offer:
  - a swipe-2-refresh functionality
  - and a worker that requests the resources from above every 60 minutes

### Authorization

It's mandatory for your requests towers the API to be authorized. You can find the required request below:

This is how it looks in `curl`:

```bash
curl --request POST \
  --url https://api.baubuddy.de/index.php/login \
  --header 'Authorization: Basic QVBJX0V4cGxvcmVyOjEyMzQ1NmlzQUxhbWVQYXNz' \
  --header 'Content-Type: application/json' \
  --data '{
        "username":"365",
        "password":"1"
}'
```

The response will contain a json object, having the access token in `json["oauth"]["access_token"]`. For all subsequent calls this has to be added to the request headers as `Authorization: Bearer {access_token}`.

A possible implementation in `Kotlin` could be the following. You don't have to copy over this one, feel free to indivualize it or use a different network library.

```kotlin
val client = OkHttpClient()
val mediaType = MediaType.parse("application/json")
val body = RequestBody.create(mediaType, "{\n        \"username\":\"365\",\n        \"password\":\"1\"\n}")
val request = Request.Builder()
  .url("https://api.baubuddy.de/index.php/login")
  .post(body)
  .addHeader("Authorization", "Basic QVBJX0V4cGxvcmVyOjEyMzQ1NmlzQUxhbWVQYXNz")
  .addHeader("Content-Type", "application/json")
  .build()
val response = client.newCall(request).execute()
```
