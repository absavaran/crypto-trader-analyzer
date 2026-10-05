# README.md
# Crypto Trader Analyzer

A Java-based crypto trading dashboard and strategy analysis website. It evaluates a trader profile, market trend, support/resistance behavior, risk management, and provides a concise professional review.

## Features
- Web dashboard for crypto strategy evaluation
- Java backend with Spring Boot
- Trading analysis engine for trend, risk-reward, and market structure
- Responsive frontend interface
- REST API for AI-like strategy analysis

## Run locally

```bash
mvn spring-boot:run
```

Then open:

```text
http://localhost:8080
```

## API

### POST /api/analyze

Request body example:

```json
{
  "coinName": "BTC",
  "marketTrend": "Bullish",
  "supportLevel": "62000",
  "resistanceLevel": "68000",
  "riskReward": 2.5,
  "volumeStrength": 75,
  "sentiment": "Strong momentum"
}
```

## Stack
- Java 17
- Spring Boot 3
- Maven
- HTML/CSS/JavaScript
