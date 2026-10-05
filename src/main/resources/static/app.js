document.addEventListener('DOMContentLoaded', () => {
    const form = document.getElementById('analysisForm');
    const resultBox = document.getElementById('results');

    const conditionEl = document.getElementById('marketCondition');
    const rrEl = document.getElementById('rrValue');
    const volumeEl = document.getElementById('volumeValue');
    const confidenceEl = document.getElementById('confidenceValue');

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

        rrEl.textContent = payload.riskReward || '2.0';
        volumeEl.textContent = `${Math.max(0, Math.min(100, payload.volumeStrength || 50))}%`;
        conditionEl.textContent = payload.marketTrend || 'Neutral';

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
                    <span>Risk / Reward</span>
                    <strong>${data.riskLevel}</strong>
                </div>
                <div class="metric-box">
                    <span>Sentiment</span>
                    <strong>${document.getElementById('marketTrend').value}</strong>
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
});
