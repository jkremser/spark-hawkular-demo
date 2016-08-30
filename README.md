# spark-hawkular-demo

[![Build Status](https://travis-ci.org/Jiri-Kremser/spark-hawkular-demo.svg?branch=master)](https://travis-ci.org/Jiri-Kremser/spark-hawkular-demo)

## Assumptions
This application assumes that the Cassandra is up and running on localhost and listening on default ports. Also that the `hawkular-services` was run together with the agent that was collecting some metrics and stored them into Cassandra.

## Running the Spark
`./sbt run`
