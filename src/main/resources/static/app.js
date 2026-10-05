document.addEventListener('DOMContentLoaded', () => {
    const form = document.getElementById('analysisForm');
    const resultBox = document.getElementById('results');

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

        resultBox.innerHTML = 'Analyzing strategy...';

        try {
            const response = await fetch('/api/analyze', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(payload)
            });

            if (!response.ok) {
                throw new Error('Request failed');
            }

            const data = await response.json();
            renderResult(data);
        } catch (error) {
            resultBox.innerHTML = '<div class="result-card"><p>Unable to analyze the strategy right now. Please try again.</p></div>';
        }
    });

    function renderResult(data) {
        resultBox.className = 'result-card';
        resultBox.innerHTML = `
            <div class="result-header">
                <div class="main-title">${data.analysisTitle}</div>
                <span class="badge">${data.riskLevel}</span>
            </div>

            <div class="metric">
                <span>Confidence</span>
                <strong>${data.confidence}%</strong>
            </div>

            <div class="result-section">
                <h3>Summary</h3>
                <p>${data.summary}</p>
            </div>

            <div class="result-section">
                <h3>Strategy</h3>
                <p>${data.strategy}</p>
            </div>

            <div class="result-section">
                <h3>Strengths</h3>
                <p>${data.strengths}</p>
            </div>

            <div class="result-section">
                <h3>Weaknesses</h3>
                <p>${data.weaknesses}</p>
            </div>

            <div class="result-section">
                <h3>Recommendation</h3>
                <p>${data.recommendation}</p>
            </div>
        `;
    }
});
