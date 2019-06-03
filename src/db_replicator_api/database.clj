(ns db-replicator-api.database
	(:require [clojure.java.jdbc :as jdbc]
					[java-jdbc.sql :as sql]))

(defn db-select-all
	[database table-name]
	(jdbc/query database
		(sql/select * table-name)))

(defn db-select-all-where
	[database table-name conditions]
	(jdbc/query database
		(sql/select * table-name
			(sql/where conditions))))

(defn db-select-all-where-limit
	[database table-name conditions limit]
	(jdbc/query database
		(sql/select * table-name
			(sql/where conditions)
			(str "LIMIT" limit))))

(defn db-select-all-limit
	[database table-name limit]
	(jdbc/query database
		(sql/select * table-name
			(str "LIMIT " limit))))

(defn db-select-all-limit-order
	[database table-name limit order]
	(jdbc/query database
		(sql/select * table-name
			(sql/order-by order)
			(str "LIMIT " limit))))

(defn db-select-all-where-order
	[database table-name conditions order]
	(jdbc/query database
		(sql/select * table-name
			(sql/where conditions)
			(sql/order-by order))))

(defn special-db-select-all-where-order
	[database table-name conditions order]
	(jdbc/query database
		(sql/select * table-name " WHERE " conditions (sql/order-by order))))

(defn special-db-select-all-where-limit-order
	[database table-name conditions limit order]
	(jdbc/query database
		(sql/select * table-name " WHERE " conditions (sql/order-by order) (str " LIMIT " limit))))

(defn special-db-select-execution-logs
	[database execution-id]
	(first
		(jdbc/query database
			["SELECT SUM(type=5) AS 'info',
						SUM(type=1) AS 'insert',
						SUM(type=2) AS 'update',
						SUM(type=3) AS 'delete',
						SUM(type=4) AS 'error'
						FROM `LogsProcess` WHERE id_execution = ?" execution-id])))

(defn db-insert!
	[database table-name data]
	(jdbc/insert! database table-name data))

(defn db-update-where!
	[database table-name data conditions]
	(jdbc/update! database table-name data (sql/where conditions)))

(defn db-delete-where
	[database table-name conditions]
	(jdbc/delete! database table-name (sql/where conditions)))
