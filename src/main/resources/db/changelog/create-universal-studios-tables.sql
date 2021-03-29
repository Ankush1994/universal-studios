drop table if exists app_user;
create table app_user (
  id SERIAL8 PRIMARY KEY,
  name VARCHAR NOT NULL,
  access_token VARCHAR NOT NULL UNIQUE,
  role VARCHAR NOT NULL,
  active BOOLEAN NOT NULL DEFAULT TRUE
);

drop table if exists ride;
create table ride (
  id SERIAL8 PRIMARY KEY,
  name VARCHAR NOT NULL UNIQUE,
  capacity int NOT NULL,
  round_duration_in_sec int NOT NULL,
  active BOOLEAN NOT NULL DEFAULT TRUE
);

drop table if exists queue;
create table queue (
  id SERIAL8 PRIMARY KEY,
  user_id SERIAL8 NOT NULL,
  ride_id SERIAL8 NOT NULL,
  status VARCHAR NOT NULL,
  entry_ts SERIAL8 NOT NULL
);

drop table if exists activity_log;
create table activity_log (
  id SERIAL8 PRIMARY KEY,
  user_id SERIAL8,
  ride_id SERIAL8 NOT NULL,
  activity_ts SERIAL8 NOT NULL
);