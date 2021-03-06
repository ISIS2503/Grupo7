package com.m2mci.mqttKafkaBridge;

import java.util.Properties;

import kafka.javaapi.producer.Producer;
import kafka.message.Message;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

import org.apache.log4j.Logger;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.kohsuke.args4j.CmdLineException;

public class Bridge implements MqttCallback {
	private Logger logger = Logger.getLogger(this.getClass().getName());
	private MqttAsyncClient mqtt;
	private MqttConnectOptions connOpt;
	private Producer<String, String> kafkaProducer;
	
	private void connect(String serverURI, String clientId, String zkConnect, String username, String password) throws Exception {
		
		String caCrtFile= "C:/certs/ca.crt";
		String crtFile = "C:/certs/srv.crt";
		String keyFile = "C:/certs/srv.key";
		
		connOpt = new MqttConnectOptions();
		connOpt.setUserName(username);
		connOpt.setPassword(password.toCharArray());
		connOpt.setSocketFactory(SslUtil.getSocketFactory(caCrtFile, crtFile, keyFile, password));
		
		mqtt = new MqttAsyncClient(serverURI, clientId);
		mqtt.setCallback(this);
		IMqttToken token = mqtt.connect(connOpt);
		Properties props = new Properties();
		
		//Updated based on Kafka v0.8.1.1
		props.put("metadata.broker.list", "localhost:8090");
        props.put("serializer.class", "kafka.serializer.StringEncoder");
        props.put("partitioner.class", "example.producer.SimplePartitioner");
        props.put("request.required.acks", "1");
		
		ProducerConfig config = new ProducerConfig(props);
		kafkaProducer = new Producer<String, String>(config);
		token.waitForCompletion();
		logger.info("Connected to MQTT and Kafka");
	}

	private void reconnect() throws MqttException {
		IMqttToken token = mqtt.connect();
		token.waitForCompletion();
	}
	
	private void subscribe(String[] mqttTopicFilters) throws MqttException {
		int[] qos = new int[mqttTopicFilters.length];
		for (int i = 0; i < qos.length; ++i) {
			qos[i] = 0;
		}
		mqtt.subscribe(mqttTopicFilters, qos);
	}

	@Override
	public void connectionLost(Throwable cause) {
		logger.warn("Lost connection to MQTT server", cause);
		while (true) {
			try {
				logger.info("Attempting to reconnect to MQTT server");
				reconnect();
				logger.info("Reconnected to MQTT server, resuming");
				return;
			} catch (MqttException e) {
				logger.warn("Reconnect failed, retrying in 10 seconds", e);
			}
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
			}
		}
	}

	@Override
	public void deliveryComplete(IMqttDeliveryToken token) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void messageArrived(String topic, MqttMessage message) throws Exception {
		
		char q = '"';
		byte[] payload = message.getPayload();
		String payloadString = new String(message.getPayload());
		String substring = payloadString.substring(0,payloadString.length()-1);
		String password = new String(connOpt.getPassword());
		String mensaje = substring+","+q+"credenciales"+q+":{"+q+"usuario"+q+":"+q+connOpt.getUserName()+q+","+q+"password"+q+":"+q+password+q+"}}";
		System.out.println(mensaje);
		//Updated based on Kafka v0.8.1.1
		KeyedMessage<String, String> data = new KeyedMessage<String, String>(topic, mensaje);
		kafkaProducer.send(data);
	}

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String args[]) throws Exception {
		CommandLineParser parser = null;
		try {
			parser = new CommandLineParser();
			parser.parse(args);
			Bridge bridge = new Bridge();
			bridge.connect(parser.getServerURI(), parser.getClientId(), parser.getZkConnect(), parser.getUserName(), parser.getPassword());
			bridge.subscribe(parser.getMqttTopicFilters());
		} catch (MqttException e) {
			e.printStackTrace(System.err);
		} catch (CmdLineException e) {
			System.err.println(e.getMessage());
			parser.printUsage(System.err);
		}
	}
}