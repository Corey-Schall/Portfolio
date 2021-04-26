SELECT project_id, project_name, foundation_length, foundation_width, region_id, description, aesthetic_id, user_id FROM project JOIN project_user USING(project_id) WHERE user_id = 3;
INSERT INTO project (project_id, project_name, foundation_length, foundation_width, region_id, description, aesthetic_id) VALUES (DEFAULT, 'My Cabin', 40, 50, 1, 'On two hundred acres on a mountain.', 1);
INSERT INTO project (project_id, project_name, foundation_length, foundation_width, region_id, description, aesthetic_id) VALUES (DEFAULT, 'My Beachhouse', 40, 50, 1, 'On the beach front with a pool and hot tub.', 1);
INSERT INTO project (project_id, project_name, foundation_length, foundation_width, region_name, description, stylename) VALUES (DEFAULT, 'My Condo in the city', 40, 50, 'West', 'A penthouse in the heart of the city with rooftop views.', 'Industrial');
INSERT INTO project_user (project_id, user_id) VALUES ((SELECT project_id FROM project WHERE project_id = 1), (SELECT user_id FROM users WHERE user_id = 3));
SELECT aesthetic_id, stylename, paragraph_description, picture_url FROM aesthetic;
INSERT INTO users (user_id, username, password_hash, role) VALUES (DEFAULT, 'test', 'test', 'test');
SELECT region_id, region_name, region_cost FROM region;
SELECT fixture_id, room_id, fixture_type_id, x_coordinate, y_coordinate FROM fixture;
SELECT project_id, project_name, foundation_length, foundation_width, region_id, description, aesthetic_id, user_id FROM project JOIN project_user USING(project_id) WHERE user_id = 3;
UPDATE project SET project_name = 'My Loveshack', foundation_length = 75, foundation_width = 45, region_id = 1, description = 'On two hundred acres on a mountain', aesthetic_id = 1 WHERE project_id = 1;
SELECT region_name FROM region;
SELECT floor_id, project_id, floor_name, floor_order FROM floor;
INSERT INTO floor (floor_id, project_id, floor_name, floor_order) VALUES (DEFAULT, 2, 'First Floor', 1);
INSERT INTO floor (floor_id, project_id, floor_name, floor_order) VALUES (DEFAULT, 2, 'Second Floor', 2);
INSERT INTO floor (floor_id, project_id, floor_name, floor_order) VALUES (DEFAULT, 2, 'Third Floor', 3);

SELECT * FROM room;
INSERT INTO room (room_id, room_name, floor_id, floor_type_name, length, width, x_coordinate, y_coordinate, wall_type_name, stylename)
VALUES (DEFAULT, 'Kitchen', 1, 'Tile', 15, 20, 0, 0, 'Drywall', 'Industrial');

INSERT INTO room (room_id, room_name, floor_id, floor_type_name, length, width, x_coordinate, y_coordinate, wall_type_name, stylename) 
VALUES (DEFAULT, 'Living Room', 1, 'Tile', 15, 20, 0, 0, 'Drywall', 'Industrial');

INSERT INTO room (room_id, room_name, floor_id, floor_type_name, length, width, x_coordinate, y_coordinate, wall_type_name, stylename) 
VALUES (DEFAULT, 'Dining Room', 1, 'Tile', 15, 20, 0, 0, 'Drywall', 'Industrial');

INSERT INTO room (room_id, room_name, floor_id, floor_type_name, length, width, x_coordinate, y_coordinate, wall_type_name, stylename)
VALUES (DEFAULT, 'Bedroom 1', 2, 'Tile', 10, 18, 0, 0, 'Drywall', 'Industrial');

INSERT INTO room (room_id, room_name, floor_id, floor_type_name, length, width, x_coordinate, y_coordinate, wall_type_name, stylename) 
VALUES (DEFAULT, 'Bathroom', 2, 'Tile', 15, 20, 0, 0, 'Drywall', 'Industrial');


SELECT * FROM floor_type;
INSERT INTO floor_type VALUES (DEFAULT, 'Carpet', 2.00);
INSERT INTO floor_type VALUES (DEFAULT, 'Hardwood', 4.00);
INSERT INTO floor_type VALUES (DEFAULT, 'Tile', 6.00);

SELECT room_id, room_name, floor_id, floor_type_name, length, width, x_coordinate, y_coordinate, wall_type_name, stylename FROM room WHERE floor_id = 1;

INSERT INTO wall_type (wall_type_id, wall_type_name, wall_cost) VALUES (DEFAULT, 'Drywall', 5.00);
INSERT INTO wall_type (wall_type_id, wall_type_name, wall_cost) VALUES (DEFAULT, 'Brick', 10.00);

SELECT floor_id, project_id, floor_name, floor_order FROM floor WHERE project_id = 2;
SELECT * FROM floor;

SELECT project_id, project_name, foundation_length, foundation_width, region_name, description, stylename, user_id FROM project JOIN project_user USING(project_id) WHERE user_id = 3;

SELECT * FROM fixture;

SELECT * FROM room;
UPDATE room SET room_name = 'Test', floor_id = 1, floor_type_name = 'Carpet', 
length = 20, width = 30, x_coordinate = 3, y_coordinate = 3, wall_type_name = 'Drywall', stylename = 'Industrial'  
WHERE room_id = 3;

INSERT INTO fixture (room_id, fixture_type, x_coordinate, y_coordinate) VALUES (1, 'Interior Door', 0, 0);
INSERT INTO fixture (room_id, fixture_type, x_coordinate, y_coordinate) VALUES (1, 'Refrigerator', 0, 0);
INSERT INTO fixture (room_id, fixture_type, x_coordinate, y_coordinate) VALUES (2, 'Window', 0, 0);
INSERT INTO fixture (room_id, fixture_type, x_coordinate, y_coordinate) VALUES (5, 'Toilet', 0, 0);
INSERT INTO fixture (room_id, fixture_type, x_coordinate, y_coordinate) VALUES (4, 'Light Fixture', 0, 0);

SELECT fixture_id, room_id, fixture_type, x_coordinate, y_coordinate FROM fixture WHERE room_id = 1;

SELECT * FROM fixture;
UPDATE fixture SET room_id = 3, fixture_type = 'Refrigerator', x_coordinate = 9999, y_coordinate = 8888 WHERE fixture_id = 2;

SELECT fixture_type, length, width, economic_cost, average_cost, high_end_cost FROM fixture_type;

SELECT * FROM region;