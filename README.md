# WeatherCast (Android/Kotlin) MVVM-Jetpack Compose

Developed a real-time weather forecasting application that fetches live data from a weather API, featuring user notifications, alarm scheduling, favorite location selection and saving, multilingual support, customizable settings, and the ability to select or detect the user’s current location.



Uploading WeatherCastDemo.mp4…



Uploading WeatherCastDemo.mp4…



**Architecture & Technologies:** MVVM, Kotlin, Jetpack Compose, Room, Retrofit, Coroutines, Google APIs

## 🌍 Features

### 🔧 Settings Screen

* **Location Selection**

  * Use **GPS** to detect current location
  * Or choose a specific location via **interactive map**
* **Units Selection**

  * **Temperature:** Kelvin, Celsius, Fahrenheit
  * **Wind Speed:** meter/sec, miles/hour
  * **Language:** Arabic, English

---

### 📍 Main Weather Information

* Display **city name**
* Show appropriate **weather icon**
* Display **weather description** (e.g., clear sky, light rain)
* View **past hourly data** for the current date
* View **historical weather data** for the past 5 days

---

### 🚨 Weather Alerts Screen

* Add custom weather alerts with:

  * **Duration** of the alert
  * **Type**: Notification or Alarm Sound
  * Option to **turn off** or **snooze** the alert

---

### ❤️ Favorites Screen

* List and manage **favorite locations**
* Tap a favorite to view full forecast details
* Add a new favorite using:

  * **Map picker** with marker placement
  * Or **autocomplete search** input
* Option to **remove** saved favorite places

---

## 🔌 API Integration

This app uses the **OpenWeatherMap Forecast API**
📚 [API Documentation](https://api.openweathermap.org/data/2.5/forecast)
Make sure to read the docs thoroughly and choose endpoints suitable for each feature.

---

## 🛠️ Technologies & Architecture

* **Architecture:** MVVM (Model - View - ViewModel)
* **Language:** Kotlin
* **UI:** Android Jetpack Compose / XML (based on your implementation)
* **API:** OpenWeatherMap API
* **Testing:** Unit testing to ensure stability and accuracy

---

## ✅ Evaluation Criteria

* Feature completeness
* Usability and user experience
* Application quality, stability, and accuracy
* Clean and maintainable code following MVVM principles

---

---

## 📦 Getting Started

1. Clone the repo

   ```bash
   git clone https://github.com/DaTaj-ai/WeatherCast.git
   ```
2. Add your OpenWeatherMap API key in the proper config file
3. Build and run the app on an Android device or emulator

---

## 📄 License

MIT License (or your preferred license)
