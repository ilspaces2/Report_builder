create table if not exists table_reports_id(
    report_id int unique primary key
);

create table if not exists table_reports_names(
    report_id int references table_reports_id(report_id),
    tables_names varchar
);

create table if not exists table_query(
    query_id int unique primary key,
    table_name varchar,
    query varchar
);

create table if not exists single_query(
    query_id int unique primary key,
    query varchar
);