CREATE TABLE albums (
                        id SERIAL PRIMARY KEY,
                        title VARCHAR(255),
                        expire_date DATE
);
CREATE TABLE cards (
                       id SERIAL PRIMARY KEY,
                       album_id INTEGER REFERENCES albums(id),
                       player_id INTEGER REFERENCES players(id)
);