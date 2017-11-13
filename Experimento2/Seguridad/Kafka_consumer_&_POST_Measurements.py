import json
import requests
from kafka import KafkaConsumer

"""
Funcion que realiza POST para persistencia de medidas
"""
def post(p_sensetime, p_type, p_dataValue, p_unit, def_get_token):
	
	payload = {
	   "sensetime": p_sensetime, 
	   "type": p_type,
	   "dataValue": p_dataValue,
	   "unit": p_unit
    }

	url = 'http://localhost:8080/measurements'
	response = requests.post(url, data=json.dumps(payload), headers={'Content-type': 'application/json', 'Authorization': 'Bearer ' + def_get_token})
	print(message.topic)
	print("Response Status Code: " + str(response.status_code))

"""
Funcion que recupera Token de Auth0
"""
def get_token():

	domain = "arquisoft201720-jabermudez10.auth0.com"
	api_identifier = "uniandes.edu.co/thermalcomfort"
	client_id = "Nk7ep4CydJZjs9p3YqOb67J6Z-vx5c9Y"
	client_secret = "EOGaGRwr4FMIRnTZSXaqmp_GI_tEQPtxpeuXM2Ovkjg2Lx988-gLpofpD6vICESp"
	grant_type = "client_credentials"

	base_url = "https://{domain}".format(domain=domain) + "/oauth/token"

	payload = {
		'client_id': client_id,
		'client_secret': client_secret,
		'audience': api_identifier,
		'grant_type': grant_type
	}

	response = requests.post(base_url, data=json.dumps(payload), headers={'Content-type': 'application/json'})
	auth0_response = response.json()
	token_auth0 = auth0_response.get('access_token')

	return token_auth0

"""
Consumidor de Kafka
"""
consumer = KafkaConsumer('rawdata', group_id='my-group', bootstrap_servers=['localhost:8090'])

for message in consumer:

	json_data = json.loads(message.value.decode('utf-8'))
	
	sensetime = json_data['sensetime']

	temp = json_data['temperature']
	senseTemp = temp['data']
	tempUnit = temp['unit']

	gas = json_data['gas']
	senseGas = gas['data']
	gasUnit = gas['unit']

	if ((json_data['noise'] != None) and (json_data['ilumination'] != None)):
		
		noise = json_data['noise']
		senseNoise = noise['data']
		noiseUnit = noise['unit']

		ilumination = json_data['ilumination']
		senseIlumination = ilumination['data']
		iluminationUnit = ilumination['unit']

		post(sensetime, "temperature", float(senseTemp), str(tempUnit), get_token())
		post(sensetime, "gas", float(senseGas), str(gasUnit), get_token())
		post(sensetime, "noise", float(senseNoise), str(noiseUnit), get_token())
		post(sensetime, "ilumination", float(), str(iluminationUnit), get_token())

	else:
		
		post(sensetime, "temperature", float(senseTemp), str(tempUnit), get_token())
		post(sensetime, "gas", float(senseGas), str(gasUnit), get_token())