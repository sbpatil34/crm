CREATE DATABASE IF NOT EXISTS customersdb;
CREATE USER IF NOT EXISTS  user $MYSQL_USER identified by $MYSQL_PASSWORD;
grant all on customersdb.* to crmuser;
