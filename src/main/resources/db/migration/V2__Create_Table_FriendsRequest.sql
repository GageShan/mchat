create table friends_request
(
	id varchar(64) not null,
	send_user_id varchar(64) not null,
	accept_user_id varchar(64) not null,
	request_date_time datetime not null,
	constraint friends_request_pk
		primary key (id)
);

