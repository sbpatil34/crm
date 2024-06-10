CREATE DATABASE IF NOT EXISTS customersdb;
CREATE USER IF NOT EXISTS  'crmuser' identified by 'ThePassword';
grant all on customersdb.* to 'crmuser';
