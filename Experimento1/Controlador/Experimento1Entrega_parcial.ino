/*
 * Experimento 1 - Entrega parcial para Arquitectura de Software 2017-20
 * Universidad de los Andes
 * 
 * Grupo 7
 */
 
 #include <string.h>
 #include <math.h>
 #include <dht.h>
 #include <Adafruit_Sensor.h>
 #include <Adafruit_TSL2561_U.h>

 /*Monóxido*/
 // Selecciona el pin de entrada analoga a leer para el monóxido de carbono.
 int monoxPin 0;
 // variable para guardar el valor del sensor de monóxido de carbono.
 int monoxPPM = 0;
 // variable para la unidad de monóxido de carbono ppm.
 String monoxUnit = "ppm";
 // variable para guardar lectura de monóxido de carbono
 String monoxMsg = "";

 /*Temperatura*/
 // Selecciona el pin de entrada analoga a leer para la temperatura.
 #define tempPin 2;
 // variable para guardar el valor del sensor de temperatura.
 int tempC = 0;
 // variable para la unidad de temperatura Celsius.
 String tempUnit = "C";
 // variable para guardar lectura de temperatura
 String tempMsg = "";
 
 /*Iluminación*/
 // Selecciona el pin de entrada analoga a leer para la iluminación.
 int iluminPin = 4;
 // variable para guardar el valor del sensor de iluminación.
 int iluminLux = 0;
 // variable para la unidad de ilumicación Lux.
 String iluminUnit = "Lux";
 // variable para guardar lectura de iluminacion
 String iluminMsg = "";

 /*Sonido*/
 // Selecciona el pin de entrada analoga a leer para el sonido.
 int soundPin = 5;
 // variable para guardar el valor del sensor de sonido.
 double soundDB = 0;
 // variable para la unidad de sonido dB- Decibeles.
 String soundUnit = "dB";
 // variable para guardar lectura de sonido
 String soundMsg = "";
 
 // preparacion del proceso
 void setup() {
    // Abre puerto serial y lo configura a 9600 bps.
    Serial.begin(9600);
    
    // Config. sensor de iluminación
    tsl.enableAutoRange(true);
    tsl.setIntegrationTime(TSL2561_INTEGRATIONTIME_13MS); 
 }
 
 // ejecuta el procesamiento del sensor
 void loop() {
   // lee el valor del sensor de monóxido de carbono en Volts
  monoxPPM = analogRead(monoxPin);
  monoxMsg = String(monoxPPM) + " " + monoxUnit + "|";
   
  // lee el valor del sensor de temperatura en Volts
  tempC = DHT.read11(tempPin);
  tempMsg = String(DHT.temperature) + " " + tempUnit + "|";
   
  
  // lee el valor del sensor de iluminación en Volts
  // iluminLux = analogRead(iluminPin);
  sensors_event_t event;
  tsl.getEvent(&event);
  
  if (event.light) {
    iluminMsg = String(event.light) + " " + iluminUnit + "|";
  }
  else {
    iluminMsg = "ERROR" + " " + iluminUnit + "|";
  }
   
  // lee el valor del sensor de temperatura en Volts
  soundDB = analogRead(soundPin);
  soundDB = soundDB*(4.9/1024.0);
  soundDB = (20.0 * log10(soundDB/0.0050119))
  soundMsg = String(spundDB) + " " + soundUnit + "|";
  
  Serial.println(monoxMsg + tempMsg + iluminMsg + soundMsg);
  }
   
  // espera 1 minuto para repetir el procedimiento
  delay(60000);
   
 }
