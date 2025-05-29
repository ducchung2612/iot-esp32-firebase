# 🌡️ SMART TEMPERATURE & HUMIDITY MONITORING APP

This Android app enables **real-time monitoring of temperature and humidity**, and **remote LED control** using Firebase. Perfect for smart home and IoT automation projects.

---

## 1. 🔥 FEATURES

1.1.Real-time Sensor Monitoring

- Reads temperature & humidity data (likely from DHT11/DHT22 sensor via Arduino/ESP8266/ESP32)

- Displays live updates in the Android app

1.2.Remote LED Control

- Toggle LED status (ON/OFF) from the app

- Firebase acts as the cloud-based control hub

1.3.Firebase Integration

- Realtime Database (for sensor data & LED state)

- Authentication (optional, for user login)

1.4.Smart Home / IoT Use Case

- Can be used for home automation, greenhouse monitoring, etc.

## 2. 📱 APP DEMO

| App UI | ESP32 & Sensor |
|--------|----------------|
| <img src="https://github.com/ducchung2612/iot-esp32-firebase/blob/main/448274421-08b462bc-d23b-40d2-bd21-dcf6002a1333.png?raw=true" width="200"/> | <img src="https://github.com/ducchung2612/iot-esp32-firebase/blob/main/giaodien.png?raw=true" width="200"/> |
 

🎥 **Demo video**: [[Watch on YouTube](https://www.youtube.com/watch?v=your-video-id)](https://youtu.be/tV6abDleoD8)
📦  Download APK: [https://drive.google.com/file/d/1JRPQeZvut3SpGOnknil8w1qGjX0DU3Rf/view?usp=sharing)
---

## 3. 🛠️ TECHNOLOGIES USED

- Android (Kotlin)
- ESP32 + DHT11/DHT22 Sensor
- Firebase Realtime Database
- Arduino IDE + Firebase Library
- Gradle Kotlin DSL

---

## 4. 🔌 HARDWARE CONNECTION TABLE

| Component        | ESP32 Pin  |
|------------------|------------|
| DHT11 (Signal)   | D4         |
| LED (Anode)      | D2         |
| GND              | GND        |
| VCC (3.3V/5V)    | 3.3V       |



---
## 📝 How It Works?

1.Hardware Side (ESP32/Arduino)
  - Reads sensor data → Sends to Firebase
  - Listens for LED commands from Firebase

2.Android App
  - Subscribes to Firebase for live updates
  - Sends LED toggle commands

3.Firebase
  - Acts as the middleman for bidirectional communication

## 5. ⚙️ HOW TO BUILD & RUN

### 5.1. System Requirements:
- Android Studio (Electric Eel or later)
- Firebase project with Realtime Database enabled
- ESP32 board, LED, DHT22 sensor

### 5.2. Steps: git clone https://github.com/ducchung2612/iot-esp32-firebase


- Open the project in Android Studio (`File > Open`)
- Connect Firebase or replace the `google-services.json` file
- Run the app on a physical device (API 21+)
- Upload code to ESP32 from the `ESP32SensorProject` folder using Arduino IDE

---


## 6. 📄 LICENSE

This project is licensed under the **MIT License** – feel free to use and modify it with proper attribution.

## made by :
Nguyễn Đức Chung

Hoàng Công Chiến

Phan Thanh Hải


study in: Ha Noi University of Business and Technology
