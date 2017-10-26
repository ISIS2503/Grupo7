
import paho.mqtt.client as mqtt
import json
from datetime import datetime
import time


actuadores = list()

# This is the Subscriber
client = mqtt.Client()
json_data = None

def on_connect(client, userdata, flags, rc):
	#print("Connected with result code "+str(rc))
	client.subscribe("alarm")

def on_message(client, userdata, msg):
	print("wenas")
	json_data = json.loads(msg.payload.decode())
	lectura(json_data)
	client.disconnect()
	

def lectura(json):
	tipo = json['tipo']
	data = json['data']
	identificador = json['id']
	#print(identificador)
	actuadores.insert(identificador, str(json))
	print("hola")
	print(str(json)+"aaa")
	print("chao")

	
#====================================================================
if __name__ == "__main__":
	while True:
		try:
			client.connect("172.24.42.51", 8083, 60)
			client.on_connect = on_connect
			client.on_message = on_message

			client.loop_forever()

		except Exception as e:
			print("Excepcion: " + str(e))