INSERT INTO individual (individual_id, name, last_name, address, city, created_date, updated_date, day_of_birth)
VALUES (1, 'Vadim', 'Stepuro', 'Belarus', 'Grodno', '2024-01-23 12:16:45.866', '2024-01-23 12:16:45.866', '2003-10-18'),
(2, 'Maxim', 'Orlov', 'Belarus', 'Grodno', '2024-01-23 12:16:45.866', '2024-01-25 12:16:45.866', '2004-02-10'),
(3, 'Yagor', 'Ivanov', 'Belarus', 'Minsk', '2024-01-23 12:16:45.866', '2024-01-23 12:16:45.866', '2004-04-19'),
(4, 'Alex', 'Blare', 'Russia', 'Moscow', '2024-01-23 12:16:45.866', '2024-01-23 12:16:45.866', '2003-02-17');

INSERT INTO card (card_number, account_number, created_date, updated_date, status, balance, expiry_date, individual_id)
VALUES ('2222405343248877', 'IE12BOFI90000112345555', '2024-02-23 12:16:45.866', '2024-02-23 12:16:45.866', 'ACTIVE', 500.50, '2028-01-01', 1),
('2222420000001113', 'IE12BOFI90000112345666', '2024-02-21 12:16:45.866', '2024-02-22 12:16:45.866', 'ACTIVE', 621.320, '2028-05-01', 2),
('2223000048410010', 'IE12BOFI90000112345444', '2024-02-21 12:16:45.866', '2024-02-22 12:16:45.866', 'ACTIVE', 356.0, '2027-08-01', 3),
('4263982640269299', 'IE12BOFI90000112345678', '2024-02-23 12:16:45.866', '2024-02-23 12:16:45.866', 'ACTIVE', 720.10, '2029-03-01', 4)