# Kafka Interview Roadmap

## 1. Why Kafka?
Kafka decouples producers and consumers, enabling scalable, fault-tolerant event streaming.

## Learning Order
1. Why Kafka
2. Kafka Architecture
3. Topics & Partitions
4. Producers
5. Consumers
6. Consumer Groups
7. Replication
8. Leader Election
9. Offsets
10. Delivery Guarantees
11. Ordering
12. Scaling
13. Fault Tolerance
14. Performance
15. Kafka Internals
16. Transactions
17. Exactly Once
18. Kafka Connect
19. Kafka Streams
20. Monitoring & Troubleshooting

---

# Kafka Architecture

Producer -> Broker -> Topic -> Partitions -> Consumer Group

## Core Components
- Producer
- Broker
- Topic
- Partition
- Consumer
- KRaft Controller (ZooKeeper in older versions)

## Broker
A Kafka server that stores partitions.

## Topic
A logical stream of messages.

## Partition
An append-only log that enables parallel processing.

## Offset
Unique sequence number within a partition used for tracking progress.

## Producer
Publishes messages to Kafka.

## Consumer
Pulls messages from Kafka.

## Consumer Groups
Within a consumer group, one partition is assigned to only one consumer.

Different consumer groups receive independent copies of the stream.

## Replication
Leader + Followers provide high availability.

## Leader Election
If the leader fails, an in-sync follower becomes leader.

## ISR (In-Sync Replicas)
Replicas fully caught up with the leader and eligible for leader election.

## Producer Acknowledgements
- acks=0 : Fastest, may lose data
- acks=1 : Leader acknowledgement
- acks=all : Wait for all ISR replicas (safest)

## Delivery Guarantees
- At Most Once
- At Least Once
- Exactly Once

Exactly-once requires:
- Idempotent Producer
- Transactions
- Proper consumer handling

## Ordering
Guaranteed only within a partition.

## Message Key
Messages with the same key always go to the same partition.

## Retention
Messages remain for configured time/size even after consumption.

## Replay
Consumers can reset offsets and replay messages.

## Log Compaction
Retains only the latest value for each key.

## Scaling
Increase partitions, brokers, and consumers.

## Performance
Kafka achieves high throughput using:
- Sequential disk writes
- Batching
- Compression
- Zero-copy transfer
- Page cache
- Pull-based consumers
- Parallel partitions

## Kafka Internals
- Append-only logs
- Segment files
- Index files

## Transactions
Atomic writes across multiple partitions/topics.

## Idempotent Producer
Prevents duplicates during retries.

## Kafka Connect
Move data between Kafka and external systems.

## Kafka Streams
Build real-time stream processing applications.

## Monitoring
Monitor:
- Consumer Lag
- Producer Latency
- Throughput
- Broker CPU
- Disk Usage
- Network I/O
- Under-replicated Partitions
- Offline Partitions
- ISR Changes
- Controller Health

# Common Interview Questions

- Why Kafka over RabbitMQ?
- Why partitions?
- Why replication?
- What happens if a consumer crashes?
- What happens if a broker crashes?
- Can Kafka guarantee ordering?
- How do you increase throughput?
- Why does Kafka scale well?

# Senior Software Engineer Topics

## Distributed Systems
- CAP Theorem
- Consistency Models
- Consensus
- Replication
- Sharding
- Leader Election
- Quorums

## Messaging
- Kafka
- RabbitMQ
- Pulsar
- SQS/SNS
- DLQs
- Event Sourcing

## System Design
- Load Balancers
- API Gateways
- Redis
- CDN
- SQL/NoSQL
- Rate Limiting
- Circuit Breakers
- Observability

## Concurrency
- Threads
- Locks
- Futures
- Java Memory Model
- Producer-Consumer

## Databases
- Indexes
- Transactions
- Isolation Levels
- Locking
- Query Optimization

## Reliability
- Retries
- Backoff
- Timeouts
- Bulkheads
- Graceful Degradation
- Chaos Engineering

## Cloud
- Kubernetes
- Containers
- CI/CD
- Infrastructure as Code
- Metrics, Logs, Tracing

## Coding
- Trees
- Graphs
- Dynamic Programming
- Tries
- Sliding Window
- Heaps
- Union-Find
- Topological Sort

# Six-Week Study Plan

## Week 1
Kafka fundamentals

## Week 2
Kafka internals and performance

## Week 3
Distributed systems

## Week 4
System design

## Week 5
Databases, concurrency, cloud

## Week 6
LeetCode, mock interviews, behavioral preparation
