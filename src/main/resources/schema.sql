-- Schema that represents the database structure
-- Syntax: SQLite

-- Drop tables if they already exist
DROP TABLE IF EXISTS riders;
DROP TABLE IF EXISTS trainers;
DROP TABLE IF EXISTS lessons;
DROP TABLE IF EXISTS bookings;
DROP TABLE IF EXISTS memberships;
DROP TABLE IF EXISTS horses;
DROP TABLE IF EXISTS arenas;
DROP TABLE IF EXISTS horsesBoxes;
-- Table: trainers
CREATE TABLE IF NOT EXISTS trainers
(
    fiscalCode       TEXT PRIMARY KEY,
    firstName        TEXT NOT NULL,
    lastName         TEXT NOT NULL
);

-- Table: horses
CREATE TABLE IF NOT EXISTS horses
(
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT NOT NULL,
    info TEXT NOT NULL
);

-- Table: arenas
CREATE TABLE IF NOT EXISTS arenas
(
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT NOT NULL,
    available INTEGER NOT NULL CHECK(available IN (0, 1)) -- 0 = not available, 1 = available
);

-- Table: lessons
CREATE TABLE IF NOT EXISTS lessons
(
    id           INTEGER PRIMARY KEY AUTOINCREMENT,
    arena        INTEGER NOT NULL,
    trainer      TEXT    NOT NULL,
    date         TEXT    NOT NULL,
    time         TEXT    NOT NULL,
    FOREIGN KEY (trainer) REFERENCES trainers (fiscalCode) ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (arena) REFERENCES arenas (id) ON UPDATE CASCADE ON DELETE CASCADE
);

-- Table: riders
CREATE TABLE IF NOT EXISTS riders
(
    fiscalCode      TEXT PRIMARY KEY,
    firstName        TEXT NOT NULL,
    lastName     TEXT NOT NULL,
    horse       INTEGER NOT NULL,
    FOREIGN KEY (horse) REFERENCES horses (id) ON UPDATE CASCADE ON DELETE CASCADE
);


-- Table: bookings (many-to-many between lessons and riders)
CREATE TABLE IF NOT EXISTS bookings
(
    rider    TEXT NOT NULL,
    lesson   INTEGER NOT NULL,
    PRIMARY KEY (rider, lesson),
    FOREIGN KEY (rider) REFERENCES riders (fiscalCode) ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (lesson) REFERENCES lessons (id) ON UPDATE CASCADE ON DELETE CASCADE
);

-- Table: memberships
CREATE TABLE IF NOT EXISTS memberships
(
    rider    TEXT PRIMARY KEY,
    numLessons INTEGER NOT NULL,
    type    TEXT    NOT NULL, -- Type of extension (e.g. "lessonsPack")
    FOREIGN KEY (rider) REFERENCES riders (fiscalCode) ON UPDATE CASCADE ON DELETE CASCADE
);

-- Table: horsesBoxes
CREATE TABLE IF NOT EXISTS horseBoxes
(
    box INTEGER PRIMARY KEY AUTOINCREMENT,
    horse INTEGER NOT NULL,
    FOREIGN KEY (horse) REFERENCES horses (id) ON UPDATE CASCADE ON DELETE CASCADE
);





