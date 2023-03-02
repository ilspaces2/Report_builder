create table table_reports_id (
id serial,
report_id int unique primary key
);

create table table_reports_names (
report_id int references table_reports_id(report_id),
tables_names varchar
);