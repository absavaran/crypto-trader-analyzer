#!/bin/bash
echo "Starting Crypto Trader Analyzer..."
java -Dserver.port=${PORT:-8080} -jar target/trader-analyzer.jar
