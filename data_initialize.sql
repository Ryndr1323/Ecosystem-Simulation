SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;

-- Refresh Tables
DROP TABLE IF EXISTS `animal_descriptors`;
DROP TABLE IF EXISTS `plant_descriptors`;

-- New Initialize Tables
CREATE TABLE IF NOT EXISTS `animal_descriptors`(
    -- Core Data
    `id` INT AUTO_INCREMENT PRIMARY KEY,
    `animal_name` VARCHAR(50) NOT NULL UNIQUE,
    `main_desc` TEXT,
    `diet_type` VARCHAR(20) NOT NULL,
    -- Speed Related
    `base_speed` DOUBLE NOT NULL,
    `chase_mult` DOUBLE NOT NULL,
    -- Age Related
    `age_mating` DOUBLE NOT NULL,
    `max_age` DOUBLE NOT NULL,
    -- Addresses
    `img_addr` VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS `plant_descriptors` (
    -- Core Data
    `id` INT AUTO_INCREMENT PRIMARY KEY,
    `plant_name` VARCHAR(50) NOT NULL UNIQUE,
    `main_desc` TEXT,
    `seed_type` VARCHAR(20) NOT NULL,
    `poisonous` BOOLEAN DEFAULT FALSE,
    -- Age Related
    `min_age` DOUBLE NOT NULL,
    `max_age` DOUBLE NOT NULL,
    -- Addresses
    `img_addr` VARCHAR(255) NOT NULL
);

-- Insert Data
INSERT INTO `animal_descriptors` 
(`animal_name`, `main_desc`, `diet_type`, `base_speed`, `chase_mult`, `age_mating`, `max_age`, `img_addr`) VALUES
-- Carnivores
('Harimau', 'Predator puncak hutan dengan loreng ikonik.', 'CARNIVORE', 4.5, 1.5, 24.0, 144.0, 'harimau.png'),
('Serigala', 'Pemburu taktis yang bergerak lincah.', 'CARNIVORE', 4.2, 1.4, 18.0, 120.0, 'serigala.png'),
('Singa', 'Predator kuat penguasa padang rumput.', 'CARNIVORE', 4.0, 1.6, 30.0, 150.0, 'singa.png'),
('Macan', 'Karnivora tangkas yang pandai memanjat.', 'CARNIVORE', 4.8, 1.4, 24.0, 130.0, 'macan.png'),
('Cheetah', 'Hewan darat tercepat di dunia simulasi.', 'CARNIVORE', 6.0, 1.8, 20.0, 100.0, 'cheetah.png'),
('Rubah', 'Karnivora kecil yang cerdik dan oportunis.', 'CARNIVORE', 3.8, 1.3, 12.0, 90.0, 'rubah.png'),
('Hyena', 'Pemburu tangguh dengan gigian yang sangat kuat.', 'CARNIVORE', 3.5, 1.3, 18.0, 110.0, 'hyena.png'),
('Coyote', 'Karnivora pelari cepat yang sangat adaptif.', 'CARNIVORE', 4.0, 1.4, 15.0, 100.0, 'coyote.png'),
('Panther', 'Predator soliter yang bergerak senyap dalam bayangan.', 'CARNIVORE', 4.4, 1.5, 24.0, 135.0, 'panther.png'),
('Musang', 'Karnivora kecil aktif yang memburu mangsa kecil.', 'CARNIVORE', 3.2, 1.3, 10.0, 80.0, 'musang.png'),
-- Herbivores
('Domba', 'Mamalia jinak penghasil wol tebal.', 'HERBIVORE', 2.2, 1.2, 12.0, 96.0, 'domba.png'),
('Kelinci', 'Herbivora kecil yang melompat dengan sangat cepat.', 'HERBIVORE', 3.5, 1.4, 6.0, 60.0, 'kelinci.png'),
('Rusa', 'Hewan lincah berkaki panjang yang waspada.', 'HERBIVORE', 4.0, 1.5, 16.0, 120.0, 'rusa.png'),
('Sapi', 'Herbivora besar yang bergerak lambat namun kuat.', 'HERBIVORE', 1.8, 1.1, 20.0, 160.0, 'sapi.png'),
('Kuda', 'Herbivora dengan stamina tinggi dan lari stabil.', 'HERBIVORE', 4.5, 1.3, 24.0, 180.0, 'kuda.png'),
('Kambing', 'Herbivora tangguh yang suka memanjat area terjal.', 'HERBIVORE', 2.5, 1.2, 10.0, 100.0, 'kambing.png'),
('Zebra', 'Herbivora bermotif garis yang suka berkelompok.', 'HERBIVORE', 3.8, 1.3, 22.0, 140.0, 'zebra.png'),
('Keledai', 'Herbivora penyabar dengan daya tahan tubuh tinggi.', 'HERBIVORE', 2.0, 1.1, 18.0, 150.0, 'keledai.png'),
('Kancil', 'Herbivora mini yang sangat cerdik menghindari musuh.', 'HERBIVORE', 3.7, 1.4, 8.0, 70.0, 'kancil.png'),
('Unta', 'Herbivora kuat yang mampu bertahan di kondisi ekstrem.', 'HERBIVORE', 2.3, 1.1, 36.0, 200.0, 'unta.png');

INSERT INTO `plant_descriptors` 
(`plant_name`, `main_desc`, `seed_type`, `poisonous`, `min_age`, `max_age`, `img_addr`) VALUES
-- Safe Plants
('Rumput', 'Sumber makanan utama herbivora kecil yang tumbuh sangat cepat di dataran terbuka.', 'MONOCOTS', FALSE, 2.0, 24.0, 'rumput.png'),
('Semak', 'Tanaman berkayu rendah yang padat, menyediakan nutrisi tinggi untuk herbivora besar.', 'DICOTS', FALSE, 4.0, 48.0, 'semak.png'),
('Alga', 'Organisme fotosintetik yang tumbuh subur di area lembap atau perairan simulasi.', 'MONOCOTS', FALSE, 1.0, 12.0, 'alga.png'),
('Talas', 'Tumbuhan berdaun lebar dengan umbi kaya karbohidrat yang terkubur di dalam tanah.', 'MONOCOTS', FALSE, 6.0, 60.0, 'talas.png'),
('Pakis', 'Tanaman vaskular kuno yang menyukai area teduh dan bereproduksi lewat spora.', 'MONOCOTS', FALSE, 3.0, 36.0, 'pakis.png'),
('Lichen', 'Organisme simbiosis unik yang tumbuh lambat di atas permukaan batu atau batang pohon.', 'MONOCOTS', FALSE, 1.0, 18.0, 'lichen.png'),
('Lumut', 'Vegetasi perintis tipis yang menyelimuti tanah lembap dan menjaga kelembapan dunia.', 'MONOCOTS', FALSE, 0.5, 10.0, 'lumut.png'),
-- Hazardous Plants
('Jamur', 'Fungi payung yang mengandung toksin, berbahaya jika tidak sengaja tertelan.', 'DICOTS', TRUE, 1.0, 16.0, 'jamur.png'),
('Kecubung', 'Tanaman berbunga indah namun membawa zat halusinogen beracun bagi ekosistem.', 'DICOTS', TRUE, 5.0, 40.0, 'kecubung.png');

COMMIT;