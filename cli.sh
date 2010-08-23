#!/bin/bash

java -Dorg.apache.commons.logging.Log=org.apache.commons.logging.impl.SimpleLog -Dorg.apache.commons.logging.simplelog.log.org.apache.http=DEBUG -cp target/animoto-api-1.0-SNAPSHOT-jar-with-dependencies.jar com.animoto.api.cli.ApiClientCli $*
