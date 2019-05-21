(ns db-replicator-api.database
		(:require [clojure.java.jdbc :as jdbc]
		 					[java-jdbc.sql :as sql]))

(def core-db
	{
	 :dbtype "postgres"
	 :dbname "master"
	 :user "admin"
	 :password "admin"
	 })

(defn core-db-select-all
	[table-name]
	(jdbc/query core-db
		(sql/select * table-name)))

(defn core-db-insert
	[table data]
	(jdbc/insert! core-db table data))