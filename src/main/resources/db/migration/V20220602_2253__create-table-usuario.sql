create table usuario (
    id bigint not null auto_increment,
    data_cadastro datetime not null,
    email varchar(255) not null,
    nome varchar(255) not null,
    senha varchar(255) not null,

    primary key (id)
) engine=InnoDB default charset=utf8;