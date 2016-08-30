# spark-hawkular-demo

[![Build Status](https://travis-ci.org/Jiri-Kremser/spark-hawkular-demo.svg?branch=master)](https://travis-ci.org/Jiri-Kremser/spark-hawkular-demo)

## Assumptions
This application assumes that the Cassandra is up and running on localhost and listening on default ports. Also that the `hawkular-services` was run together with the agent that was collecting some metrics and stored them into Cassandra.

## Running
`./sbt run`

## What it does
It's a simple application written in Scala that shows how to connect to the Cassandra and do some data analysis. It reads the metric data from the SST table called `data` in the `hawkular_metrics` keyspace. It creates two [RDDs](https://spark.apache.org/docs/latest/programming-guide.html#resilient-distributed-datasets-rdds) from the table naively by filtering the rows based on the metric id and feed id.

In my environment the RDDs contained ~3000 measurements. The first RDD represents the data points for the "Total Memory" metric (a constant value) and the second one the "Available Memory". Based on those two RDDs the third one is calculated by zipping the datapoints into tupples and substracting the second one from the first one, this will intuitively create the `used memory` RDD.

Then for the demonstration purposes the correlation (Pearson's r) between `used memory` and `available memory` is calculated. No surprises here, the result is the total negative correlation `-0.99999...`.

Last step is running another method from the [MLlib](https://spark.apache.org/mllib/) package that does the clustering on the data. We say to the learning algorithm that we want to end up with three clusters and run the training.
