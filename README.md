# datapipeline

 - **Kafka-connect-utilities** directory contains all the necessary libary and configuration for Sink the incoming data to MongoDB. Refer the README file for configuration set up

- **Simulator** directory contains two **Python** based Mqtt Client which keep sending data to the broker at every 5 seconds intervals. These two client will publish data on their respective topic 

- **docker-compose.yml** contains mongodb and mongo client 

- In this datapipeline application, **Apache Kafka** has been used. We need to start the Kafka Broker and Kafka-connect. Before starting the kafka connect, set up the configurations mentioned in **Kafak-connect-utilities**

- **Services** directory contains the Springboot based Java application - **datapipeline**, consumes data published by different client on their topic, perform the data transformations. It transform the data into common data model and then published to the **Kafka Broker**  on a topic. **Kafka-connect Mongodb Sink Connect** will consume data from that topic and push to MongoDB.


**System Architecture**


![image](https://user-images.githubusercontent.com/91028976/133956659-b8c90118-651a-4140-a2b4-154491fa33f0.png)
