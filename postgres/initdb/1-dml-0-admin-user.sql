INSERT INTO users VALUES
--P@ssword123
(1, false, '$2a$10$1H1Ro.4Fu2p3nLcMGXN/ge.PBDOKdzUWZoVpudYNLmp1A6RT3N0m6', gen_random_uuid(), 'admin');

INSERT INTO user_details VALUES
(1, NULL, 'admin', NULL);

INSERT INTO user_entity_roles VALUES
(1, 0), (1, 1), (1, 2);