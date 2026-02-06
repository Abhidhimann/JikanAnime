# Anime App

## Tech Stacks
- Kotlin
- Jetpack Compose
- MVVM + Clean Architecture
- Hilt (Dependency Injection)
- Retrofit & OkHttp
- Coroutines & Flow
- Navigation Compose
- Room (Offline cache)
- Coil (Image Loading, Coil because its Kotlin-first and **Jetpack Compose–native**, with coroutine-based image loading that’s lighter and better suited for Lazy Compose UIs than Glide or Picasso.)
- Jikan API

## Features

- **Pagination**: Loads anime page-by-page, Fetches next page using API pagination metadata and Appends data locally while preserving previously loaded content.
- **Pull to Refresh**: Swipe down to fetch the latest anime from the network.
- **Offline First**: animes are cached locally using Room, then shown to user. 
- **Error Handling**: No static blocking "No Internet" screen; **Shows a Toast on refresh while allowing users to browse cached movies**. 
- **Loading State**: Circular loading indicator and Shimmer.
- **System UI Support**: Fully supports Light & Dark theme, Landscape / orientation changes and Edge-to-edge layout.
- **Youtube Player**: Youtube player to play trailer video if avaible else shows anime poster.

## Apk File: [Anime App](https://github.com/Abhidhimann/JikanAnime/blob/main/app-release.apk.zip) 

## Demo

https://github.com/user-attachments/assets/3dd15d0c-fab8-4034-afa1-e5b22d84de44

https://github.com/user-attachments/assets/165d816f-93c1-4fd3-9062-8268dce4a1ea

## Known Limitations

- Trailer playback depends on YouTube availability and most animes *youtube_id* is not availabile in api response.
- Only the anime list is cached to enable fast startup, offline browsing, and anime details are fetched on demand to avoid storing large, rarely reused data and to keep the local database lightweight.
- Unit tests are not included due to time constraints.

