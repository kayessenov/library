create table booking (id int8 generated by default as identity, created_at timestamp not null, deleted_at timestamp, update_at timestamp, amount int4, books_id int8, users_id int8, primary key (id));
create table books (id int8 generated by default as identity, created_at timestamp not null, deleted_at timestamp, update_at timestamp, description TEXT, image varchar(255), name varchar(255), price int4, genre_id int8, primary key (id));
create table genre (id int8 generated by default as identity, created_at timestamp not null, deleted_at timestamp, update_at timestamp, code varchar(255), name varchar(255), primary key (id));
create table roles (id int8 generated by default as identity, created_at timestamp not null, deleted_at timestamp, update_at timestamp, role varchar(255), primary key (id));
create table users (id int8 generated by default as identity, created_at timestamp not null, deleted_at timestamp, update_at timestamp, avatar varchar(255), email varchar(255), fullname varchar(255), password varchar(255), primary key (id));
create table users_roles (users_id int8 not null, roles_id int8 not null);
alter table if exists booking add constraint FKkqja56nipukavr7vg02yl6y8r foreign key (books_id) references books;
alter table if exists booking add constraint FK158twsxs1y4jvfd99k821m4e4 foreign key (users_id) references users;
alter table if exists books add constraint FKad8kkytqpsb8n9wmbsi0nk6ce foreign key (genre_id) references genre;
alter table if exists users_roles add constraint FKa62j07k5mhgifpp955h37ponj foreign key (roles_id) references roles;
alter table if exists users_roles add constraint FKml90kef4w2jy7oxyqv742tsfc foreign key (users_id) references users;