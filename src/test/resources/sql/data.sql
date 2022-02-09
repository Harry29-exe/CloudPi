INSERT INTO users
VALUES
--P@ssword123
(1, false,
-- bcrypt 10 rounds
--  '$2a$10$1H1Ro.4Fu2p3nLcMGXN/ge.PBDOKdzUWZoVpudYNLmp1A6RT3N0m6',
-- bcrypt 4 rounds
 '$2a$04$hbroGClaZjL0ZsTBPAqc7eapTcMs.WkLiN.Lqz4QIErH30SRSIw2W',
 gen_random_uuid(), 'admin');

INSERT INTO user_details
VALUES (1, NULL, 'admin', NULL);

INSERT INTO user_entity_roles
VALUES (1, 0),
       (1, 1),
       (1, 2);