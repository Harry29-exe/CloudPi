INSERT INTO users VALUES
    (1, false, '', gen_random_uuid(), 'bob'),
    (2, false, '', gen_random_uuid(), 'alice'),
    (3, false, '', gen_random_uuid(), 'anne');

INSERT INTO user_entity_roles VALUES
    (1, 0), (1, 1),
    (2, 1), (2, 2),
    (3, 2);

INSERT INTO user_details VALUES
    (1, NULL, 'bob', NULL),
    (2, NULL, 'alice', NULL),
    (3, NULL, 'anne', NULL);