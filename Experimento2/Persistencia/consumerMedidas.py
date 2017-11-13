import json
import requests
from kafka import KafkaConsumer

def post(p_sensetime, p_type, p_dataValue, p_unit):
	
	payload = {
	   "sensetime": p_sensetime, 
	   "type": p_type,
	   "dataValue": p_dataValue,
	   "unit": p_unit
    }

	url = 'http://localhost:8080/measurements'
	response = requests.post(url, data=json.dumps(payload), headers={'Content-type': 'application/json'})
	print(message.topic)
	print("Response Status Code: " + str(response.status_code))

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

		post(sensetime, "temperature", float(senseTemp), str(tempUnit))
		post(sensetime, "gas", float(senseGas), str(gasUnit))
		post(sensetime, "noise", float(senseNoise), str(noiseUnit))
		post(sensetime, "iluniation", float(), str(iluminationUnit))

	else:
		
		post(sensetime, "temperature", float(senseTemp), str(tempUnit))
		post(sensetime, "gas", float(senseGas), str(gasUnit))