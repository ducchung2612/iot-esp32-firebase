#include <WiFi.h>
#include <FirebaseESP32.h>
#include <DHT.h>

// WiFi Credentials
#define WIFI_SSID "Duc Mai"
#define WIFI_PASSWORD "0915303445"

// Firebase Credentials
#define FIREBASE_HOST "https://cambiennhiet-2b35b-default-rtdb.firebaseio.com/"
#define FIREBASE_AUTH "X42BtH4Z6XVCrB8gUpgfmJfqCwFu51KFWICgYedF"

// Sensor & LED
#define DHTPIN 4
#define DHTTYPE DHT22
#define LED_PIN 2

DHT dht(DHTPIN, DHTTYPE);
FirebaseData firebaseData;
FirebaseConfig config;
FirebaseAuth auth;

void connectWiFi() {
  WiFi.begin(WIFI_SSID, WIFI_PASSWORD);
  Serial.print("🔌 Connecting to WiFi");
  while (WiFi.status() != WL_CONNECTED) {
    Serial.print(".");
    delay(500);
  }
  Serial.println("\n✅ WiFi Connected!");
  Serial.print("📶 IP: ");
  Serial.println(WiFi.localIP());
}

void setup() {
  Serial.begin(115200);
  pinMode(LED_PIN, OUTPUT);

  // WiFi
  connectWiFi();

  // Firebase config
  config.database_url = FIREBASE_HOST;
  config.signer.tokens.legacy_token = FIREBASE_AUTH;

  Firebase.begin(&config, &auth);
  Firebase.reconnectWiFi(true);
  Serial.println("✅ Firebase Connected!");

  dht.begin();

  // Init LED value
  Firebase.setBool(firebaseData, "Led/status", false);
}

void loop() {
  if (WiFi.status() != WL_CONNECTED) {
    Serial.println("❌ Lost WiFi, reconnecting...");
    connectWiFi();
  }

  // Read sensor data
  float temperature = dht.readTemperature();
  float humidity = dht.readHumidity();

  // Only send if valid
  if (!isnan(temperature) && !isnan(humidity)) {
    Serial.printf("🔥 Temp: %.1f°C | 🌫️ Humidity: %.1f%%\n", temperature, humidity);

    if (Firebase.setFloat(firebaseData, "Sensor/temperature", temperature)) {
      Serial.println("✅ Temperature sent.");
    } else {
      Serial.print("❌ Temp error: ");
      Serial.println(firebaseData.errorReason());
    }

    if (Firebase.setFloat(firebaseData, "Sensor/humidity", humidity)) {
      Serial.println("✅ Humidity sent.");
    } else {
      Serial.print("❌ Humid error: ");
      Serial.println(firebaseData.errorReason());
    }
  } else {
    Serial.println("⚠️ Failed to read DHT sensor!");
  }

  // Read LED state from Firebase
  if (Firebase.getBool(firebaseData, "Led/status")) {
    bool ledState = firebaseData.boolData();
    digitalWrite(LED_PIN, ledState);
    Serial.printf("💡 LED: %s\n", ledState ? "ON" : "OFF");
  } else {
    Serial.print("❌ LED read failed: ");
    Serial.println(firebaseData.errorReason());
  }

  delay(3000);
}
