CREATE TABLE IF NOT EXISTS planets (
    id INT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    climate VARCHAR(255) NOT NULL,
    terrain VARCHAR(255) NOT NULL
);

INSERT INTO planets (id, name, climate, terrain) VALUES (1, 'Tatooine', 'arid', 'desert');
INSERT INTO planets (id, name, climate, terrain) VALUES (2, 'Alderaan', 'temperate', 'grassland, mountains');
INSERT INTO planets (id, name, climate, terrain) VALUES (3, 'Yabin IV', 'temperate, tropical', 'jungle, rainforest');
