import json
import threading
from threading import Lock
from flask import Flask, render_template
from flask_socketio import SocketIO, emit
from kafka import KafkaConsumer
 
# Set this variable to "threading", "eventlet" or "gevent" to test the
async_mode = None
 
app = Flask(__name__)
app.config['SECRET_KEY'] = 'secret_thermalcomfort'
socketio = SocketIO(app, async_mode=async_mode)
thread = None
thread_lock = Lock()
 
# Ruta del dashboard
@app.route('/')
def index():
    return render_template('index_ws.html', async_mode=socketio.async_mode)
 
# Consumidor del topic de Kafka "alta.piso1.local1". Cada valor recibido se envía a través del websocket.
def background_thread_websocket():
    consumer = KafkaConsumer('rawdata', group_id='temperature', bootstrap_servers=['localhost:8090'])
    
    for message in consumer:
        json_data = json.loads(message.value.decode('utf-8'))
        sensetime = json_data['sensetime']
        sense_temp = json_data['temperature']
        sense_ilu = json_data['ilumination']
        sense_ruido = json_data['noise']
        sense_mono = json_data['gas']
        
        payload_temp = {
            'time': sensetime,
            'value': sense_temp['data']
        }

        payload_mono = {
            'time': sensetime,
            'value': sense_mono['data']
        }

        if (sense_ilu != None and sense_ruido != None):

            payload_ilu = {
                'time': sensetime,
                'value': sense_ilu['data']
            }

            payload_ruido = {
                'time': sensetime,
                'value': sense_ruido['data']
            }

            socketio.emit('mesurementsIlum', str(payload_ilu), namespace='/thermalcomfort')
            socketio.emit('mesurementsRuid', str(payload_ruido), namespace='/thermalcomfort')

        socketio.emit('mesurementsTemp', str(payload_temp), namespace='/thermalcomfort')
        socketio.emit('mesurementsMono', str(payload_mono), namespace='/thermalcomfort')
 
# Rutina que se ejecuta cada vez que se conecta un cliente de websocket e inicia el consumidor de Kafka
@socketio.on('connect', namespace='/thermalcomfort')
def test_connect():
    global thread
    with thread_lock:
        if thread is None:
            thread = socketio.start_background_task(target=background_thread_websocket)
    emit('mesurements', "Connected!!!")
 
# Iniciar el servicio en el puerto 8086
if __name__ == '__main__':
    socketio.run(app, port=8086, debug=True)