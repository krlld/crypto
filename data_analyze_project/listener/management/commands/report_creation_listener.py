import os
import json
import sys
import threading
from confluent_kafka import Consumer
from confluent_kafka import KafkaError
from confluent_kafka import KafkaException
from dotenv import load_dotenv

from .create_report import create_report

running = True

load_dotenv()
conf = {'bootstrap.servers': os.getenv('KAFKA_BOOTSTRAP-SERVERS', 'localhost:29092'),
        'auto.offset.reset': 'smallest',
        'group.id': "data-analyze-project"}

report_creation_topic = 'report-creation-topic'


class ReportCreationListener(threading.Thread):
    def __init__(self):
        threading.Thread.__init__(self)
        self.consumer = Consumer(conf)

    def run(self):
        print('Inside Report Creation Listener :  Created Listener ')
        try:
            self.consumer.subscribe([report_creation_topic])
            while running:
                msg = self.consumer.poll(timeout=1.0)
                if msg is None:
                    continue
                if msg.error():
                    if msg.error().code() == KafkaError.PARTITION_EOF:
                        sys.stderr.write('%% %s [%d] reached end at offset %d\n' %
                                         (msg.topic(), msg.partition(), msg.offset()))
                elif msg.error():
                    raise KafkaException(msg.error())
                else:
                    message = json.loads(msg.value().decode('utf-8'))
                    create_report(message)
        finally:
            self.consumer.close()
