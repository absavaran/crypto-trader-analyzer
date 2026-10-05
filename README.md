# Crypto Trader Analyzer

🚀 **سایت تحلیل و بررسی استراتژی‌های ارز دیجیتال**

## ویژگی‌ها
- 📊 داشبورد بازار زنده
- 💹 نمودار قیمت‌های ارز‌ها
- 📈 تحلیل استراتژی‌های تریدری
- 🎯 سیگنال‌های بازار (Signal Feed)
- 💼 پورتفولیو و ریسک‌مدیریت
- 📱 رابط کاربری مدرن و حرفه‌ای

## فناوری‌های استفاده‌شده
- **Backend**: Spring Boot 3.3.3
- **Frontend**: HTML5, CSS3, JavaScript
- **API**: RESTful API
- **Server**: Java 17
- **Database**: In-Memory (قابل توسعه)

## نحوه نصب و اجرا

### اجرای محلی (Local)
```bash
# کلون کردن مخزن
git clone https://github.com/absavaran/crypto-trader-analyzer.git
cd crypto-trader-analyzer

# کامپایل و ساخت
mvn clean package -DskipTests

# اجرا
java -jar target/trader-analyzer.jar
```

سپس به این آدرس بروید:
```
http://localhost:8080
```

### اجرای سریع با Maven
```bash
mvn spring-boot:run
```

## انتشار بر روی Render

### مراحل:
1. به [Render Dashboard](https://dashboard.render.com) بروید
2. روی **New +** کلیک کنید
3. **Web Service** را انتخاب کنید
4. **GitHub Repository** را انتخاب کنید:
   ```
   absavaran/crypto-trader-analyzer
   ```
5. تنظیمات زیر را انجام دهید:
   - **Name**: crypto-trader-analyzer
   - **Region**: نزدیک‌ترین منطقه
   - **Branch**: main
   - **Runtime**: Java
   - **Build Command**: `mvn clean package -DskipTests`
   - **Start Command**: `java -jar target/trader-analyzer.jar`

6. روی **Create Web Service** کلیک کنید

### آدرس نهایی بعد از Deploy:
```
https://crypto-trader-analyzer.onrender.com
```

## Endpoint‌های API

### بازار
```
GET /api/market
```
لیست ارز‌های فعال و قیمت‌های آن‌ها

### سیگنال‌های تریدری
```
GET /api/signals
```
سیگنال‌های خرید/فروش برای هر ارز

### پورتفولیو
```
GET /api/portfolio
```
موقعیت‌های موجود و P&L

### تحلیل استراتژی
```
POST /api/analyze
```
تجزیه و تحلیل یک استراتژی تریدری

## ساختار پروژه
```
crypto-trader-analyzer/
├── src/
│   ├── main/
│   │   ├── java/com/crypto/trader/
│   │   │   ├── controller/
│   │   │   ├── service/
│   │   │   ├── model/
│   │   │   └── Application.java
│   │   └── resources/
│   │       ├── static/
│   │       │   ├── index.html
│   │       │   ├── app.js
│   │       │   └── styles.css
│   │       └── application.properties
├── pom.xml
├── Procfile
├── render.yaml
└── README.md
```

## لایسنس
MIT License - برای استفاده آزادانه و تجاری

## نویسنده
[absavaran](https://github.com/absavaran)

---

**بروزرسانی شده در**: 2026-10-05
