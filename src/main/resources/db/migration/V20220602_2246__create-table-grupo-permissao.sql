create table grupo_permissao (
    grupo_id bigint not null,
    permissao_id bigint not null

) engine=InnoDB default charset=utf8;

alter table grupo_permissao add constraint FK_grupo_permissao_permissao foreign key (permissao_id) references permissao (id);
alter table grupo_permissao add constraint FK_grupo_permissao_grupo foreign key (grupo_id) references grupo (id);