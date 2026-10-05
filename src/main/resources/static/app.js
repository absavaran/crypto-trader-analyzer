document.addEventListener('DOMContentLoaded', () => {
    const form = document.getElementById('analysisForm');
    const resultBox = document.getElementById('results');
    const marketOverview = document.getElementById('marketOverview');
    const tableBody = document.getElementById('marketTableBody');
    const priceChart = document.getElementById('priceChart');
    const chartTitle = document.getElementById('chartTitle');
    const chartSubtitle = document.getElementById('chartSubtitle');
    const chartTrend = document.getElementById('chartTrend');

    const conditionEl = document.getElementById('marketCondition');
    const rrEl = document.getElementById('rrValue');
    const volumeEl = document.getElementById('volumeValue');
    const confidenceEl = document.getElementById('confidenceValue');

    const fallbackTrendMap = {
        BTC: [62000, 64000, 65500, 66700, 67342, 68250, 69000],
        ETH: [3200, 3320, 3410, 3475, 3516, 3590, 3645],
        SOL: [124, 129, 138, 148, 158, 152, 166],
        XRP: [0.56, 0.58, 0.60, 0.59, 0.61, 0.63, 0.62],
        ADA: [0.67, 0.69, 0.72, 0.71, 0.74, 0.76, 0.79],
        DOGE: [0.18, 0.17, 0.18, 0.16, 0.17, 0.19, 0.18]
    };

    function formatPrice(value) {
        if (value >= 1000) {
            return new Intl.NumberFormat('en-US', { style: 'currency', currency: 'USD', maximumFractionDigits: 2 }).format(value);
        }
        if (value >= 1) {
            return new Intl.NumberFormat('en-US', { style: 'currency', currency: 'USD', maximumFractionDigits: 4 }).format(value);
        }
        return '$' + value.toFixed(4);
    }

    function fetchMarketData() {
        fetch('/api/market')
            .then(response => response.ok ? response.json() : Promise.reject())
            .then(data => {
                renderOverviewCards(data);
                renderMarketTable(data);
                setActiveCoin(data[0]);
            })
            .catch(() => {
                const staticData = [
                    { symbol: 'BTC', name: 'Bitcoin', price: 67342.18, change24h: 3.24, volume24h: 28_900_000_000, marketCap: 1_330_000_000_000, trend: 'Bullish' },
                    { symbol: 'ETH', name: 'Ethereum', price: 3516.45, change24h: 2.11, volume24h: 18_450_000_000, marketCap: 421_000_000_000, trend: 'Bullish' },
                    { symbol: 'SOL', name: 'Solana', price: 158.42, change24h: 5.73, volume24h: 5_500_000_000, marketCap: 71_000_000_000, trend: 'Bullish' },
                    { symbol: 'XRP', name: 'XRP', price: 0.61, change24h: -0.84, volume24h: 2_980_000_000, marketCap: 34_500_000_000, trend: 'Neutral' }
                ];
                renderOverviewCards(staticData);
                renderMarketTable(staticData);
                setActiveCoin(staticData[0]);
            });
    }

    function renderOverviewCards(data) {
        marketOverview.innerHTML = data.map((coin, index) => `
            <div class="metric-card ${index === 0 ? 'accent' : ''}">
                <span class="label">${coin.symbol}</span>
                <strong>${formatPrice(coin.price)}</strong>
                <small class="${coin.change24h >= 0 ? 'change-positive' : 'change-negative'}">
                    ${coin.change24h >= 0 ? '+' : ''}${coin.change24h.toFixed(2)}% (24h)
                </small>
            </div>
        `).join('');
    }

    function renderMarketTable(data) {
        tableBody.innerHTML = data.map(coin => `
            <tr data-symbol="${coin.symbol}" data-price="${coin.price}" data-name="${coin.name}" data-trend="${coin.trend}" data-change="${coin.change24h}">
                <td>${coin.symbol}</td>
                <td>${formatPrice(coin.price)}</td>
                <td class="${coin.change24h >= 0 ? 'change-positive' : 'change-negative'}">
                    ${coin.change24h >= 0 ? '+' : ''}${coin.change24h.toFixed(2)}%
                </td>
                <td>${coin.trend}</td>
            </tr>
        `).join('');

        tableBody.querySelectorAll('tr').forEach(row => {
            row.addEventListener('click', () => {
                const symbol = row.dataset.symbol;
                const price = Number(row.dataset.price);
                const name = row.dataset.name;
                const trend = row.dataset.trend;
                setActiveCoin({ symbol, price, name, trend });
                document.getElementById('coinName').value = symbol;
                document.getElementById('marketTrend').value = trend;
            });
        });
    }

    function setActiveCoin(coin) {
        const dataSet = fallbackTrendMap[coin.symbol] || [coin.price * 0.97, coin.price * 0.99, coin.price * 1.01, coin.price * 1.03, coin.price * 1.04, coin.price * 1.06, coin.price * 1.07];
        const line = buildLine(dataSet, 600, 230, coin.price);
        priceChart.innerHTML = `
            <defs>
                <linearGradient id="lineGradient" x1="0%" x2="100%" y1="0%" y2="0%">
                    <stop offset="0%" stop-color="#2dd4bf" />
                    <stop offset="100%" stop-color="#3b82f6" />
                </linearGradient>
            </defs>
            <path d="M ${line.map(p => `${p.x},${p.y}`).join(' L ')} " fill="none" stroke="url(#lineGradient)" stroke-width="4" stroke-linecap="round" stroke-linejoin="round" />
            <path d="M ${line.map(p => `${p.x},${p.y}`).join(' L ')} L 560,210 L 0,210 Z" fill="rgba(45,212,191,0.10)" />
        `;

        chartTitle.textContent = `${coin.symbol} / USD`;
        chartSubtitle.textContent = formatPrice(coin.price);
        chartTrend.textContent = coin.trend || 'Neutral';
        chartTrend.className = 'trend-badge ' + (coin.trend === 'Bullish' ? 'positive' : coin.trend === 'Bearish' ? 'negative' : 'neutral');
    }

    function buildLine(values, width, height, baseValue) {
        const min = Math.min(...values);
        const max = Math.max(...values);
        const range = max - min || 1;

        return values.map((value, index) => ({
            x: (index / (values.length - 1)) * width,
            y: height - ((value - min) / range) * (height - 30) - 15
        }));
    }

    form.addEventListener('submit', async (event) => {
        event.preventDefault();

        const payload = {
            coinName: document.getElementById('coinName').value,
            marketTrend: document.getElementById('marketTrend').value,
            supportLevel: document.getElementById('supportLevel').value,
            resistanceLevel: document.getElementById('resistanceLevel').value,
            riskReward: Number(document.getElementById('riskReward').value),
            volumeStrength: Number(document.getElementById('volumeStrength').value),
            sentiment: document.getElementById('sentiment').value
        };

        const trendValue = payload.marketTrend || 'Neutral';
        conditionEl.textContent = trendValue;
        rrEl.textContent = payload.riskReward || '2.0';
        volumeEl.textContent = `${Math.max(0, Math.min(100, payload.volumeStrength || 50))}%`;

        resultBox.innerHTML = '<div class="results-empty">Analyzing strategy setup...</div>';

        try {
            const response = await fetch('/api/analyze', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(payload)
            });

            if (!response.ok) {
                throw new Error('Request failed');
            }

            const data = await response.json();
            renderResult(data);
            confidenceEl.textContent = `${data.confidence}%`;
        } catch (error) {
            resultBox.innerHTML = '<div class="results-empty">Unable to analyze the strategy right now. Please try again.</div>';
            confidenceEl.textContent = 'N/A';
        }
    });

    function renderResult(data) {
        resultBox.className = 'result-card';
        resultBox.innerHTML = `
            <div class="result-header">
                <div class="result-title">${data.analysisTitle}</div>
                <span class="badge">${data.riskLevel}</span>
            </div>

            <div class="metrics-row">
                <div class="metric-box">
                    <span>Confidence</span>
                    <strong>${data.confidence}%</strong>
                </div>
                <div class="metric-box">
                    <span>Trend</span>
                    <strong>${document.getElementById('marketTrend').value}</strong>
                </div>
                <div class="metric-box">
                    <span>Signal</span>
                    <strong>${data.riskLevel}</strong>
                </div>
            </div>

            <div class="detail-box">
                <h3>Summary</h3>
                <p>${data.summary}</p>
            </div>

            <div class="detail-box">
                <h3>Strategy</h3>
                <p>${data.strategy}</p>
            </div>

            <div class="detail-box">
                <h3>Strengths</h3>
                <p>${data.strengths}</p>
            </div>

            <div class="detail-box">
                <h3>Weaknesses</h3>
                <p>${data.weaknesses}</p>
            </div>

            <div class="detail-box">
                <h3>Recommendation</h3>
                <p>${data.recommendation}</p>
            </div>
        `;
    }

    fetchMarketData();
});
