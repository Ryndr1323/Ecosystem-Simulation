// Ambil elemen Canvas dari HTML
const canvas = document.getElementById('ecosystemCanvas');
const ctx = canvas.getContext('2d');

let entityCache = [];

async function fetchEcosystemData() {
    try {
        const response = await fetch('SimulationServlet'); 
        if (!response.ok) throw new Error('Gagal mengambil data dari server');
        
        entityCache = await response.json();
        
        renderCanvas();
    } catch (error) {
        console.error('Error Fetching:', error);
    }
}

function renderCanvas() {
    // Bersihkan canvas lama sebelum menggambar frame baru
    ctx.clearRect(0, 0, canvas.width, canvas.height);
    
    // Looping data dari cache untuk digambar
    entityCache.forEach(entity => {
        if (!entity.isAlive) return;
        
        ctx.beginPath();
        ctx.arc(entity.posX, entity.posY, 8, 0, Math.PI * 2);
        
        if (entity.breedName === "Singa") {
            ctx.fillStyle = '#ef4444';
        } else if (entity.breedName === "Kelinci") {
            ctx.fillStyle = '#3b82f6';
        } else {
            ctx.fillStyle = '#22c55e';
        }
        
        ctx.fill();
        ctx.closePath();
    });
}

setInterval(fetchEcosystemData, 100);