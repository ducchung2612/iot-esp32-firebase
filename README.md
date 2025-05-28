# 🌡️ SMART TEMPERATURE & HUMIDITY MONITORING APP

This Android app enables **real-time monitoring of temperature and humidity**, and **remote LED control** using Firebase. Perfect for smart home and IoT automation projects.

---

## 1. 🔥 FEATURES

- 📡 Real-time monitoring of temperature and humidity via ESP32 sensors
- 💡 Remote control to turn LED on/off via app
- ☁️ Firebase integration for cloud-based data synchronization
- 🏠 Smart home ready – easily adaptable to IoT systems

---

## 2. 📱 APP DEMO

| App UI | Firebase Database | ESP32 & Sensor |
|--------|-------------------|----------------|
| <img src="https://github.com/ducchung2612/iot-esp32-firebase/blob/main/448274421-08b462bc-d23b-40d2-bd21-dcf6002a1333.png?raw=true" width="200"/> | <img src="https://github.com/user-attachments/assets/6e165d5e-f56b-402b-9f74-3618a6f45905" width="200"/> | <img src="https://github.com/ducchung2612/iot-esp32-firebase/blob/main/giaodien.png?raw=true" width="200"/> |
 

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

## 4. ⚙️ HOW TO BUILD & RUN

### 4.1. Requirements:
- Android Studio (Electric Eel or later)
- Firebase project with Realtime Database enabled
- ESP32 board, LED, DHT22 sensor

### 4.2. Steps: git clone https://github.com/ducchung2612/iot-esp32-firebase


- Open the project in Android Studio (`File > Open`)
- Connect Firebase or replace the `google-services.json` file
- Run the app on a physical device (API 21+)
- Upload code to ESP32 from the `ESP32SensorProject` folder using Arduino IDE

---

## 5. 🔌 HARDWARE CONNECTION TABLE

| Component        | ESP32 Pin  |
|------------------|------------|
| DHT11 (Signal)   | D4         |
| LED (Anode)      | D2         |
| GND              | GND        |
| VCC (3.3V/5V)    | 3.3V       |



---

## made by :
Nguyễn Đức Chung

Hoàng Công Chiến

Phan Thanh Hải


study in: Ha Noi University of Business and Technology
## 6. 📄 LICENSE

This project is licensed under the **MIT License** – feel free to use and modify it with proper attribution.
