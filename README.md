````markdown
# TMDBVideos

**TMDBVideos** is an Android application for browsing movies sourced from  
[The Movie Database (TMDB)](https://www.themoviedb.org/).  
It’s built entirely with **Kotlin**, **Jetpack Compose**, and a clean-architecture
approach.

---

## ✨ Features

- **Trending** – discover what’s popular this week at a glance.  
- **Movie categories** – quickly hop between **Popular**, **Upcoming**, and  
  **Top Rated** lists.  
- **Offline caching** – Room + Paging keeps previously viewed data available
  when you’re offline.  
- **Favorites** – mark the movies you love for instant access later.  
- **Modern UI** – Material 3 components, smooth animations, dark-theme support,
  and Hilt-powered dependency injection.

---

## 🗂 Project Structure

```text
.
├── app     // Presentation layer (UI, navigation)
├── data    // Repositories, remote API, local database
└── domain  // Use-cases and domain models
````

---

## 🚀 Getting Started

### 1. Clone the repo

```bash
git clone https://github.com/parinexus/TMDBVideos.git
```

### 2. Get a TMDB API key

1. Sign up (free) at **https://www.themoviedb.org**.
2. Navigate to **Settings → API** and create an API key.

### 3. Add your key

Open **`data/build.gradle.kts`** and replace the placeholder value:

```kotlin
buildConfigField("String", "API_KEY", "\"YOUR_API_KEY\"")
```

> **Tip:** Avoid committing secrets.
> Store the key in your local **`gradle.properties`** instead:

```properties
TMDB_API_KEY=your-key-here
```

and reference it in **`build.gradle.kts`**:

```kotlin
buildConfigField("String", "API_KEY", "\"${property("TMDB_API_KEY")}\"")
```

### 4. Build & run

1. Open the project in **Android Studio**.
2. Sync Gradle.
3. Select the **`app`** configuration and hit **Run** (or *Shift ⌘ R*).

---

## 🛠 Built With

* **Kotlin** & **Coroutines**
* **Jetpack Compose** (Material 3)
* **Retrofit** / **OkHttp**
* **Room** database
* **Paging 3**
* **Hilt** for DI

---