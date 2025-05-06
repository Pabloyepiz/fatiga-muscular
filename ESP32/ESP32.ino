#include <WiFi.h>
#include <Firebase_ESP_Client.h>  // << NOTA: esta es la moderna

// Wi-Fi
#define WIFI_SSID "Puvlo Note 10+"
#define WIFI_PASSWORD "12345678901"

// Firebase
#define FIREBASE_HOST "avance-4111c-default-rtdb.firebaseio.com"
#define FIREBASE_AUTH "bWZQZpINogzT8qxf2bJl2hwFSMHzx0M6pU7i4FqN"

// Objetos Firebase
FirebaseData fbdo;
FirebaseAuth auth;
FirebaseConfig config;

#define SENSOR_PIN 34

void setup() {
  Serial.begin(115200);
  WiFi.begin(WIFI_SSID, WIFI_PASSWORD);

  Serial.print("Conectando a WiFi");
  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }
  Serial.println(" ¡Conectado!");

  config.database_url = FIREBASE_HOST;
  config.signer.tokens.legacy_token = FIREBASE_AUTH;

  Firebase.begin(&config, &auth);
  Firebase.reconnectWiFi(true);
}

void loop() {
  int valor = analogRead(SENSOR_PIN);
  Serial.print("Valor leído: ");
  Serial.println(valor);

  if (Firebase.RTDB.setInt(&fbdo, "/sensorMyoWare", valor)) {
    Serial.println("Dato enviado correctamente");
  } else {
    Serial.println("Error: " + fbdo.errorReason());
  }

  delay(1000);
}
