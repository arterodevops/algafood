create table usuario_grupo (
    usuario_id bigint not null,
    grupo_id bigint not null
) engine=InnoDB default charset=utf8;

alter table usuario_grupo add constraint FK_usuario_grupo_grupo foreign key (grupo_id) references grupo (id);
alter table usuario_grupo add constraint FK_usuario_grupo_usuario foreign key (usuario_id) references usuario (id);

