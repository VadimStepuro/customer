INSERT INTO legal_entity (legal_entity_id, name, address, city, created_date, updated_date, inn)
VALUES (1, 'Stepuro', 'Belarus', 'Grodno', '2024-01-23 12:16:45.866', '2024-01-23 12:16:45.866', '1111111111'),
(2, 'Orlov', 'Belarus', 'Grodno', '2024-01-23 12:16:45.866', '2024-01-25 12:16:45.866', '2222222222'),
(3, 'Ivanov', 'Belarus', 'Minsk', '2024-01-23 12:16:45.866', '2024-01-23 12:16:45.866', '3333333333'),
(4, 'Blare', 'Russia', 'Moscow', '2024-01-23 12:16:45.866', '2024-01-23 12:16:45.866', '4444444444');

INSERT INTO account (account_number, created_date, updated_date, status, balance, legal_entity_id)
VALUES ('IE12BOFI90000112345555', '2024-02-23 12:16:45.866', '2024-02-23 12:16:45.866', 'ACTIVE', 807.30, 1),
('IE12BOFI90000112345666', '2024-02-21 12:16:45.866', '2024-02-22 12:16:45.866', 'ACTIVE', 542.250, 2),
('IE12BOFI90000112345444', '2024-02-21 12:16:45.866', '2024-02-22 12:16:45.866', 'ACTIVE', 467.20, 3),
('IE12BOFI90000112345678', '2024-02-23 12:16:45.866', '2024-02-23 12:16:45.866', 'ACTIVE', 635.70, 4)