BEGIN TRANSACTION;

DROP TABLE IF EXISTS fixture;
DROP TABLE IF EXISTS fixture_type;
DROP TABLE IF EXISTS room;
DROP TABLE IF EXISTS wall_type;
DROP TABLE IF EXISTS floor_type;
DROP TABLE IF EXISTS floor;
DROP TABLE IF EXISTS project_user;
DROP TABLE IF EXISTS project;
DROP TABLE IF EXISTS aesthetic;
DROP TABLE IF EXISTS region;
DROP TABLE IF EXISTS users; -- code provided to us, don't touch

DROP SEQUENCE IF EXISTS seq_user_id;  -- code provided to us, don't touch
DROP SEQUENCE IF EXISTS seq_project_id;
DROP SEQUENCE IF EXISTS seq_floor_id;
DROP SEQUENCE IF EXISTS seq_room_id;
DROP SEQUENCE IF EXISTS seq_fixture_id;

--provided to us, don't touch
CREATE SEQUENCE seq_user_id
  INCREMENT BY 1
  NO MAXVALUE
  NO MINVALUE
  CACHE 1;

  
CREATE SEQUENCE seq_project_id
  INCREMENT BY 1
  NO MAXVALUE
  NO MINVALUE
  CACHE 1;
  
CREATE SEQUENCE seq_floor_id
  INCREMENT BY 1
  NO MAXVALUE
  NO MINVALUE
  CACHE 1;
  
CREATE SEQUENCE seq_room_id
  INCREMENT BY 1
  NO MAXVALUE
  NO MINVALUE
  CACHE 1;
  
CREATE SEQUENCE seq_fixture_id
  INCREMENT BY 1
  NO MAXVALUE
  NO MINVALUE
  CACHE 1;
  
-- users table was provided to us, don't touch
CREATE TABLE users (
	user_id int DEFAULT nextval('seq_user_id'::regclass) NOT NULL,
	username varchar(50) NOT NULL,
	password_hash varchar(200) NOT NULL,
	role varchar(50) NOT NULL,
	CONSTRAINT PK_user PRIMARY KEY (user_id)
);

CREATE TABLE region (
        region_name varchar(30) NOT NULL,
        region_cost float8 NOT NULL,
        
        CONSTRAINT pk_region PRIMARY KEY (region_name)
);

CREATE TABLE aesthetic (
        styleName varchar (30) NOT NULL,
        paragraph_description varchar (500),
        picture_url varchar(100),
        
        CONSTRAINT pk_aesthetic PRIMARY KEY (stylename)
);

CREATE TABLE project (
        project_id int DEFAULT nextval('seq_project_id'::regclass) NOT NULL,
        project_name varchar (30) NOT NULL,
        foundation_length int NOT NULL,
        foundation_width int NOT NULL,
        region_name varchar (30) NOT NULL,
        description varchar (300) NOT NULL,
        stylename varchar (30) NOT NULL,
        step_number int,
        
        CONSTRAINT pk_project PRIMARY KEY (project_id),
        CONSTRAINT fk_project_region FOREIGN KEY (region_name) REFERENCES region (region_name),
        CONSTRAINT fk_project_aesthetic FOREIGN KEY (stylename) REFERENCES aesthetic (stylename)
);

CREATE TABLE project_user (
        project_id int NOT NULL,
        user_id int NOT NULL,
        
        CONSTRAINT pk_project_user PRIMARY KEY (project_id, user_id),
        CONSTRAINT fk_project_user_user FOREIGN KEY (user_id) REFERENCES users (user_id),
        CONSTRAINT fk_project_user_project FOREIGN KEY (project_id) REFERENCES project (project_id)
);       

CREATE TABLE floor (
        floor_id int DEFAULT nextval('seq_floor_id'::regclass) NOT NULL,
        project_id int NOT NULL,
        floor_name varchar(30) NOT NULL,
        floor_order int NOT NULL,
        
        CONSTRAINT pk_floor PRIMARY KEY (floor_id),
        CONSTRAINT fk_floor_project FOREIGN KEY (project_id) REFERENCES project (project_id)
);

CREATE TABLE floor_type (
        floor_type_name varchar (30) NOT NULL,
        cost float8 NOT NULL,
        
        CONSTRAINT pk_floor_type PRIMARY KEY (floor_type_name)
);

CREATE TABLE wall_type (
        wall_type_name varchar(30) NOT NULL,
        wall_cost float8 NOT NULL,
        
        CONSTRAINT pk_wall_type PRIMARY KEY (wall_type_name)
);

CREATE TABLE room (
        room_id int DEFAULT nextval('seq_room_id'::regclass) NOT NULL,
        room_name varchar(30) NOT NULL,
        floor_id int NOT NULL,
        floor_type_name varchar(30),
        length int,
        width int,
        x_coordinate float8,
        y_coordinate float8, 
        wall_type_name varchar(30),
        stylename varchar(30),
        
        CONSTRAINT pk_room PRIMARY KEY (room_id),
        CONSTRAINT fk_room_floor FOREIGN KEY (floor_id) REFERENCES floor (floor_id),
        CONSTRAINT fk_room_floor_type FOREIGN KEY (floor_type_name) REFERENCES floor_type (floor_type_name),
        CONSTRAINT fk_room_wall_type FOREIGN KEY (wall_type_name) REFERENCES wall_type (wall_type_name),
        CONSTRAINT fk_aesthetic FOREIGN KEY (stylename) REFERENCES aesthetic (stylename)
);

CREATE TABLE fixture_type (
        fixture_type varchar (50) NOT NULL,
        length int NOT NULL,
        width int NOT NULL,
        economic_cost float8 NOT NULL,
        average_cost float8 NOT NULL,
        high_end_cost float8 NOT NULL,
        
        CONSTRAINT pk_fixture_type PRIMARY KEY (fixture_type)
);

CREATE TABLE fixture (
        fixture_id int DEFAULT nextval('seq_fixture_id'::regclass) NOT NULL,
        room_id int NOT NULL,
        fixture_type varchar (50) NOT NULL,
        x_coordinate float8 NOT NULL,
        y_coordinate float8 NOT NULL,
        
        CONSTRAINT pk_fixture PRIMARY KEY (fixture_id),
        CONSTRAINT fk_fixture_room FOREIGN KEY (room_id) REFERENCES room (room_id),
        CONSTRAINT fk_fixture_fixture_type FOREIGN KEY (fixture_type) REFERENCES fixture_type (fixture_type)
);


INSERT INTO fixture_type (fixture_type, length, width, economic_cost, average_cost, high_end_cost) VALUES 
        ('Low End Light Fixture', 1, 1, 34.97, 0 , 0),
        ('Average Light Fixture', 1, 1, 80, 0, 0),
        ('High End Light Fixture', 1, 1, 267.02, 0, 0),
        ('Low End Cabinet', 2, 3, 158, 0, 0),
        ('Average Cabinet', 2, 3, 277, 0, 0),
        ('High End Cabinet', 2, 3, 460, 0, 0),
        ('Low End Countertop (2ft x 3ft section)', 2, 3, 126, 0, 0),
        ('Average Countertop (2ft x 3ft section)', 2, 3, 240, 0, 0),
        ('High End Countertop (2ft x 3ft section)', 2, 3, 390, 0, 0),
        ('Low End Refrigerator', 3, 3, 698, 0, 0),
        ('Average Refrigerator', 3, 3, 1140, 0, 0),
        ('High End Refrigerator', 3, 3, 2398, 0, 0),
        ('Low End Kitchen Sink', 1,2, 126.75, 0, 0),
        ('Average Kitchen Sink', 1,2, 220, 0, 0),
        ('High End Kitchen Sink', 1,2, 399, 0, 0),
        ('Low End Bathroom Sink', 1, 1, 69.36, 0, 0),
        ('Average Bathroom Sink', 1, 1, 81.58, 0, 0),
        ('High End Bathroom Sink', 1, 1, 251.90, 0, 0),
        ('Low End Shower', 4, 4, 479, 0, 0),
        ('Average Shower', 4, 4, 769.99, 0, 0),
        ('High End Shower', 4, 4, 1303.96, 0, 0),
        ('Low End Bath', 3, 5, 514.38, 0, 0),
        ('Average Bath', 3, 5, 717.22, 0, 0),
        ('High End Bath', 3, 5, 1399, 0, 0),
        ('Low End Toilet', 1, 1, 169, 0, 0),
        ('Average Toilet', 1, 1, 309, 0, 0),
        ('High End Toilet', 1, 1, 598.5, 0, 0),
        ('Low End Washer', 3, 3, 499, 0, 0),
        ('Average Washer', 3, 3, 584, 0, 0),
        ('High End Washer', 3, 3, 1079, 0, 0),
        ('Low End Dryer', 3, 3, 499, 0, 0),
        ('Average Dryer', 3, 3, 798, 0, 0),
        ('High End Dryer', 3, 3, 1099, 0, 0),
        ('Low End Window', 0, 3, 99, 0, 0),
        ('Average Window', 0, 3, 156, 0, 0),
        ('High End Window', 0, 3, 374.85, 0, 0),
        ('Low End Interior Door', 0, 3, 83.86, 0, 0),
        ('Average Interior Door', 0, 3, 186, 0, 0),
        ('High End Interior Door', 0, 3, 248.78, 0, 0),
        ('Low End Exterior Door', 0, 3, 133, 0, 0),
        ('Average Exterior Door', 0, 3, 236, 0, 0),
        ('High End Exterior Door', 0, 3, 553, 0, 0),
        ('Low End Closet', 2, 4, 92, 0, 0),
        ('Average Closet', 2, 4, 103, 0, 0),
        ('High End Closet', 2, 4, 164, 0, 0)
        ;         

INSERT INTO aesthetic (stylename, paragraph_description, picture_url) VALUES
        ('Mid-Century Modern', 'The mid-1900s produced some of the most iconic pieces in modern design. It is characterized by refined lines, minimalist silhouettes, and natural shapes. From Saarinen, Niemeyer, Eames, Noguchi, Jacobsen and beyond, the mid-century modern masters defined creative ways to use new materials like molded plastic, plywood, and aluminum in industrial design. Its pieces are highly versatile and can complement a myriad of design styles.', 'https://blog.froy.com/wp-content/uploads/2014/12/Mid-Century-Modern-Design-4.jpg'),
        ('Industrial', 'This is a look that hearkens back to the turn-of-the-century industrial era. It emphasizes liberal use of exposed steel with distressed wooden elements, frequently complemented by exposed brick walls. The modern variant commonly includes copper-tone accents. In terms of general feel, industrial decor is often rustic and mature.', 'https://blog.froy.com/wp-content/uploads/2014/12/Industrial-Decor-6.jpg'),
        ('Nautical', 'Warm, relaxing, and positive. Nautical decor (also referred to as coastal or cottage decor) reflects the New England beach house spirit. This interior design style is based on white or sand colored foundation, with blue as the primary accent color.', 'https://blog.froy.com/wp-content/uploads/2014/12/Nautical-Decor-3.jpeg'),
        ('Scandinavian', 'An off-shoot of the mid-century modern movement, Scandinavian design introduced a popular minimalist look to the interior architecture field that lasts to this day. Although most people associate it with IKEA, there are a variety of subset looks within Scandinavian design itself.', 'https://blog.froy.com/wp-content/uploads/2014/12/Scandinavian-Design-3.jpg'),
        ('Bohemian', 'Bohemian decor captures the carefree and adventurous spirit of the avant-garde lifestyle. It features creative application of rich patterns and vibrant colors, especially those with red or purple tones. The key is to carefully present a purposefully “messy” look. Layer on textiles (throws, pillows, rugs, tapestry) for a warm ambience.', 'https://blog.froy.com/wp-content/uploads/2014/12/Bohemian-Decor-4.jpg'),
        ('Farmhouse', 'Farmhouse decor is a modern approach to cabin-inspired interior design. Mostly transitional in nature with some traditional elements mixed in, farmhouse aesthetic should transport your imagination to French Provence. Source some dried lavender bunches and other greeneries – careful arrangement of vases and planters will really stylize your home.', 'https://blog.froy.com/wp-content/uploads/2014/12/Farmhouse-Decor-1.jpg'),
        ('Urban Modern', 'Urban interior design stems from the modern designer lofts in the major cities. Taking cues from its cosmopolitan environment, urban modern is a fusion of various opposing and complementary traits. Minimalist modern, glamorous chic, ethnic heirlooms, and edgy experimental designs all collide in a distinctively 21st-century setting.', 'https://blog.froy.com/wp-content/uploads/2014/12/Urban-Modern-2.jpg'),
        ('Shabby Chic', 'Shabby chic decor emphasizes vintage elements to recreate the antique flea market look. The furniture are characterized by their aged appearance, with distressed wood composition covered in sanded milk paint to show signs of wear and tear.', 'https://blog.froy.com/wp-content/uploads/2014/12/Shabby-Chic-2.jpg'),
        ('None', '', '')
        ;

INSERT INTO floor_type VALUES 
        ('Carpet', 42.00),
        ('Hardwood', 57.00),
        ('Tile', 59.00),
        ('Default', 40.00)
        ;
        
INSERT INTO wall_type VALUES 
        ('Drywall', 63.33), 
        ('Plaster', 71),      
        ('Brick', 82.00),
        ('Wood Planks', 74)
        ;
        
INSERT INTO region (region_name, region_cost) VALUES
('West', 1.8), ('Northeast', 1.5), ('Southwest', 1.2), ('Midwest', 1.2), ('Southeast', 1);     

-- Code provided to us below this line, don't touch       
INSERT INTO users (username,password_hash,role) VALUES ('user','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_USER');
INSERT INTO users (username,password_hash,role) VALUES ('admin','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_ADMIN');

-- demo test data
INSERT INTO users (user_id, username, password_hash, role) VALUES (3, 'test', '$2a$10$hRXV97QuqW0tUXpiu25xouJzSbt6W3KIzsEKfopM9lHHJRo4gxuhS', 'ROLE_USER');

INSERT INTO project (project_id, project_name, foundation_length, foundation_width, region_name, description, stylename, step_number) VALUES 
        (DEFAULT, 'My Cabin', 40, 50, 'West', 'On two hundred acres on a mountain.', 'Mid-Century Modern', 2),
        (DEFAULT, 'My Beach House', 40, 50, 'West', 'On the beach front with a pool and hot tub.', 'Mid-Century Modern', 3),
        (DEFAULT, 'My Condo in the city', 40, 50, 'West', 'A penthouse in the heart of the city with rooftop views.', 'Mid-Century Modern', 2)
        ;

INSERT INTO project_user (project_id, user_id) VALUES (1, 3), (2, 3), (3, 3);

INSERT INTO floor (floor_id, project_id, floor_name, floor_order) VALUES 
        (DEFAULT, 2, 'First Floor', 1),
        (DEFAULT, 2, 'Second Floor', 2),
        (DEFAULT, 2, 'Third Floor', 3)
        ;

INSERT INTO room (room_id, room_name, floor_id, floor_type_name, length, width, x_coordinate, y_coordinate, wall_type_name, stylename) VALUES
        (DEFAULT, 'Kitchen', 1, 'Tile', 15, 20, 0, 0, 'Drywall', 'Industrial'),
        (DEFAULT, 'Living Room', 1, 'Tile', 15, 20, 0, 0, 'Drywall', 'Industrial'),
        (DEFAULT, 'Dining Room', 1, 'Tile', 15, 20, 0, 0, 'Drywall', 'Industrial'),
        (DEFAULT, 'Bedroom 1', 2, 'Tile', 10, 18, 0, 0, 'Drywall', 'Industrial'),
        (DEFAULT, 'Bathroom', 2, 'Tile', 15, 20, 0, 0, 'Drywall', 'Industrial')
        ;
        
INSERT INTO fixture (room_id, fixture_type, x_coordinate, y_coordinate) VALUES
        (1, 'Low End Interior Door', 0, 0),
        (1, 'High End Refrigerator', 0, 0),
        (2, 'Average Window', 0, 0),
        (5, 'High End Toilet', 0, 0),
        (4, 'Average Light Fixture', 0, 0)
        ; 

COMMIT TRANSACTION;