INSERT INTO users
VALUES
--P@ssword123
(1, false, '$2a$10$1H1Ro.4Fu2p3nLcMGXN/ge.PBDOKdzUWZoVpudYNLmp1A6RT3N0m6', gen_random_uuid(), 'admin');

INSERT INTO user_details
VALUES (1, NULL, false, NULL, 'admin');

INSERT INTO role_entity(id, user_id, role)
VALUES (1, 1, 0),
       (2, 1, 1),
       (3, 1, 2);