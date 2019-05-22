(ns db-replicator-api.database
	(:require [clojure.java.jdbc :as jdbc]
					[java-jdbc.sql :as sql]))

(defn db-select-all
	[database table-name]
	(jdbc/query database
		(sql/select * table-name)))

(defn db-insert!
	[database table-name data]
	(jdbc/insert! database table-name data))

(defn db-insert-multi!
	[database table data]
	(map db-insert! data))
