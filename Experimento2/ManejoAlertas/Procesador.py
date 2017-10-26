import paho.mqtt.client as mqtt
import json
from datetime import datetime
import time

# ====================== CONSTANTES ====================== #

# Temperatura
TEMP = 'temperatura' 
MIN_TEMP = 16.1
MAX_TEMP = 32.4

# Ruido
RUIDO = 'ruido'
MIN_RUIDO = 0.0
MAX_RUIDO = 85.0

# Gases
GAS = 'gas'
MIN_GAS = 0.0
MAX_GAS = 100.0


# Iluminacion
ILUM = 'iluminacion'
MIN_ILUM = 100.0
MAX_ILUM = 2000.0

# Constantes de frecuencias
# Temperatura y Gas
TEMP_GAS_FREC =  6000

# Ruido e Ilumincacion
RUIDO_ILUM_FREC = 12000

LAST_MSG_LIST = list()
last_msg = None
TIME_OUT = 30
TIME_SLEEP = 3

# ====================== ATRIBUTOS ====================== #

diez_gas = list()
diez_temp = list()
diez_ruido = list()
diez_ilum = list()

# contadorCorreo = 0

publicator = mqtt.Client()
# This is the Subscriber
json_data = None

# ====================== OFF_LINE ======================= #
epoch = datetime.utcfromtimestamp(0)
time_offline = 0.0
runtime = time.time()
enviado = False

# =============== Funciones MQTT Paho =================== #
client = mqtt.Client()

def on_connect(client, userdata, flags, rc):
	#print("Connected with result code "+str(rc))
	client.subscribe("processor1")

def on_message(client, userdata, msg):
	json_data = json.loads(msg.payload.decode())
	#publicator.connect("172.24.42.51",8083,60)

	off_line()
	LAST_MSG_LIST.append(json_data)

	client.disconnect()

	data_temp = json_data['temperature']['data']
	out_of_range(diez_temp, 'temperatura', data_temp)

"""
	data_gas = json_data['gas']['data']
	out_of_range(diez_gas, 'gas', data_gas)

	if not (json_data['noise'] is None):
		data_noise = json_data['noise']['data']
		out_of_range(diez_ruido, 'ruido', data_noise)

	if not (json_data['ilumination'] is None):
		data_ilum = json_data['ilumination']['data']
		out_of_range(diez_ilum, 'iluminacion', data_ilum)
"""
	
# =============== Funciones Procesamiento =============== #
def sendAlarm(mensaje):
	publicator.publish("alarm",mensaje)
	publicator.disconnect()

def sendReport(mensaje):
	publicator.publish("report",mensaje)
	publicator.disconnect()

# Para calcular mediciones de rango

def out_of_range(lista, tipo, medida):

	#print("Entro out_of_range para lista: " + str(tipo).upper())
	
	if len(lista) < 2:
		lista.insert(0, medida)
		print( "Inserto: " + str(medida) + " en lista [ " + str(tipo) + " ]" )

	else:
		prom = sum(lista)/2
		#print(str(tipo) + " promedio: " + str(prom))

		if tipo == TEMP:
			if not (MIN_TEMP <= prom <= MAX_TEMP):
				
				mensaje = {
					'tipo': str(tipo),
					'data':  float(prom),
					'id': 0
				}

				json_msg = json.dumps(mensaje)
				print( str(json_msg) )
				sendAlarm(str(json_msg))
				##contadorCorreo++
			else:
				print( ( str(tipo) + " promedio: " + str(prom) ).upper() )
				##contadorCorreo = 0
		elif tipo == GAS:
			if not (MIN_GAS <= prom <= MAX_GAS):

				mensaje = {
					'tipo': str(tipo),
					'data':  float(prom)
				}

				json_msg = json.dumps(mensaje)
				print( str(json_msg) )
				sendAlarm(str(json_msg))

				##contadorCorreo++
			else:
				print( ( str(tipo) + " promedio: " + str(prom) ).upper() )
				##contadorCorreo = 0
		elif tipo == ILUM:
			if not (MIN_ILUM <= prom <= MAX_ILUM):

				mensaje = {
					'tipo': str(tipo),
					'data':  float(prom)
				}

				json_msg = json.dumps(mensaje)
				print( str(json_msg) )
				sendAlarm(str(json_msg))

				##contadorCorreo++
			else:
				print( ( str(tipo) + " promedio: " + str(prom) ).upper() )
				##contadorCorreo = 0
		elif tipo == RUIDO:
			if not (MIN_RUIDO <= prom <= MAX_RUIDO):

				mensaje = {
					'tipo': str(tipo),
					'data':  float(prom)
				}

				json_msg = json.dumps(mensaje)
				print( str(json_msg) )
				sendAlarm(str(json_msg))

				##contadorCorreo++
			else:
				print( ( str(tipo) + " promedio: " + str(prom) ).upper() )
				##contadorCorreo = 0
		lista.pop()

# Para calcular fuera de linea
def off_line():

	if len(LAST_MSG_LIST) == 0:
		now = time.time()
		time_offline = now - runtime
		print("Lista vacía, tiempo fuera de linea: " + str(time_offline))

	if len(LAST_MSG_LIST) > 0:
		enviado = False
		now = time.time()
		last_msg = LAST_MSG_LIST.pop()
		sense_time = last_msg["sensetime"]
		hora_json = datetime.strptime(sense_time, "%Y-%m-%d %H:%M:%S.%f")
		time_offline = now - (time.mktime(hora_json.timetuple()) + (hora_json.microsecond / 1000000.0))
		print("Lista con 1 elemento, tiempo fuera de linea: " + str(time_offline))

	if time_offline > TIME_OUT and not enviado:
		print("Error: El controlador esta desconectado.")
		enviado = True

#if __name__ == "__main__":
while True:
	try:
		client.connect("localhost", 8083, 60)
		client.on_connect = on_connect
		client.on_message = on_message
		
		if len(LAST_MSG_LIST) == 0:
			now = time.time()
			time_offline = now - runtime
			print("Lista vacía, tiempo fuera de linea: " + str(time_offline))

		if len(LAST_MSG_LIST) > 0:
			enviado = False
			now = time.time()
			last_msg = LAST_MSG_LIST.pop()
			sense_time = last_msg["sensetime"]
			hora_json = datetime.strptime(sense_time, "%Y-%m-%d %H:%M:%S.%f")
			time_offline = now - (time.mktime(hora_json.timetuple()) + (hora_json.microsecond / 1000000.0))
			print("Lista con 1 elemento, tiempo fuera de linea: " + str(time_offline))

		if time_offline > TIME_OUT and not enviado:
			print("Error: El controlador esta desconectado.")
			enviado = True
		
		client.loop_forever()
		time.sleep(TIME_SLEEP)

	except Exception as e:
		print("Excepcion: " + str(e))