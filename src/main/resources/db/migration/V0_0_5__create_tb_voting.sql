CREATE TABLE IF NOT EXISTS tb_voting (
  id INTEGER GENERATED BY DEFAULT AS IDENTITY,
  session_id INTEGER,
  schedule_id INTEGER,
  associate_id INTEGER,
  vote varchar(5),
  vote_date DATETIME,
  
  foreign key (session_id) references tb_session(id),
 
  primary key (id)
);