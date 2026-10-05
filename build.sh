#!/bin/bash
set -e

echo "🔨 Building Spring Boot application..."
mvn clean package -DskipTests

echo "✅ Build completed!"
echo "📦 JAR file: target/trader-analyzer.jar"
echo "🚀 Ready for deployment to Render!"
