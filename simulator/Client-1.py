import os
import time
import sys
import json
import random
import paho.mqtt.client as mqtt

# This is the Publisher

INTERVAL=5

telemetry_data = {'tmp': 0, 'speed': 0, 'latit': 0, 'longitude': 0}

next_reading = time.time();

client = mqtt.Client()
client.connect("localhost",1883,60)

client.loop_start()

try:
    while True:
        tmp = round(random.uniform(10.5,75.5), 2)
        speed = round(random.uniform(5.5,100.5), 2)
        latit = round(random.uniform(10.5,75.5), 5)
        longitude = round(random.uniform(10.5,75.5), 5)
        print(u"Temperature: {:g}\u00b0C, Speed: {:g}, latit: {:g}, longitude: {:g})".format(tmp, speed, latit, longitude));
        telemetry_data['tmp'] = tmp
        telemetry_data['speed'] = speed
        telemetry_data['latit'] = latit
        telemetry_data['longitude'] = longitude
        
        client.publish('first-telemetry', json.dumps(telemetry_data));
        
        next_reading += INTERVAL;
        sleep_time = next_reading-time.time()
        if sleep_time > 0:
            time.sleep(sleep_time)
        
except KeyboardInterrupt:
    pass
    
client.loop_start()
client.disconnect();