CREATE TABLE IF NOT EXISTS tb_associate (
  id int8 generated by default as identity, 
  name varchar(50), 
  cpf varchar(11), 
  email varchar(50),
  
  primary key (id)
);