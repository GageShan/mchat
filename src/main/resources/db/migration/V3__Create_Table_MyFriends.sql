create table my_friends
(
	id varchar(64) not null,
	my_user_id varchar(64) not null,
	my_friends_user_id varchar(64) not null,
	constraint my_friends_pk
		primary key (id)
);

