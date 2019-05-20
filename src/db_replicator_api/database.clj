(ns db-replicator-api.database
		(:require [clojure.java.jdbc :as jdbc]
		 					[java-jdbc.sql :as sql]))

(def main-db
	{
	 :dbtype "postgres"
	 :dbname "master"
	 :user "admin"
	 :password "admin"
	 })

(defn select-all
	[table-name]
	(jdbc/query main-db
		(sql/select * table-name)))

(defn insert-t
	[table data]
	(println table data)
	(jdbc/insert! main-db table data))