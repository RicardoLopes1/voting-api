CREATE TABLE IF NOT EXISTS tb_schedule (
  id INTEGER GENERATED BY DEFAULT AS IDENTITY,
  name varchar(25) NOT NULL,
  description TEXT,
  created_by varchar(25) NOT NULL,
  created_date DATETIME NOT NULL,

  primary key (id)
);

CREATE TABLE IF NOT EXISTS tb_session (
  id INTEGER GENERATED BY DEFAULT AS IDENTITY,
  name varchar(25) NOT NULL,
  active boolean,
  start_date DATETIME,
  end_date DATETIME,
  schedule_id INTEGER,

  foreign key (schedule_id) references tb_schedule(id),

  primary key (id)
);