INSERT INTO albums(title, expire_date)
VALUES
    ('WWC FIFA World Cup 2023. Australia and New Zealand', '2024-12-31');

INSERT INTO cards(album_id, player_id)
SELECT 1 AS album_id, id as player_id FROM players limit 100;