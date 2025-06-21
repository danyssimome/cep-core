# echo "Waiting for Kafka to come online..."

# cub kafka-ready -b kafka:9092 1 20

# docker exec -it "container-name"
kafka-topics --create --topic atomic-event-raw-topic --partitions 4 --replication-factor 1 --bootstrap-server broker:29092
kafka-topics --create --topic aggregated-event-raw-topic --partitions 4 --replication-factor 1  --bootstrap-server broker:29092
kafka-topics --create --topic complex-event-raw-topic --partitions 4 --replication-factor 1  --bootstrap-server broker:29092
kafka-topics --create --topic atomic-event-topic --partitions 4 --replication-factor 1  --bootstrap-server broker:29092
kafka-topics --create --topic aggregated-event-topic --partitions 4 --replication-factor 1  --bootstrap-server broker:29092
kafka-topics --create --topic complex-event-topic --partitions 4 --replication-factor 1  --bootstrap-server broker:29092
kafka-topics --create --topic atomic-event-metadata-topic --partitions 4 --replication-factor 1  --bootstrap-server broker:29092
kafka-topics --create --topic aggregated-event-metadata-topic --partitions 4 --replication-factor 1  --bootstrap-server broker:29092
kafka-topics --create --topic complex-event-metadata-topic --partitions 4 --replication-factor 1  --bootstrap-server broker:29092

#sleep infinity
