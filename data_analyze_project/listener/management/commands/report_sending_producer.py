import os
import json
from confluent_kafka import Producer
from dotenv import load_dotenv

load_dotenv()
conf = {'bootstrap.servers': os.getenv('KAFKA_BOOTSTRAP-SERVERS', 'localhost:29092')}

report_sending_topic = 'report-sending-topic'


class ReportSendingProducer:
    def __init__(self) -> None:
        self.producer = Producer(conf)

    def publish(self, body):
        print('Inside Report Sending Producer: Sending to Kafka: ')
        print(body)
        self.producer.produce(report_sending_topic, value=json.dumps(body))
        self.producer.flush()
