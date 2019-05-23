(ns db-replicator-api.config
	(:require [cheshire.core :as cheshire]))

(def core-db
	(do
		(println "test")
		(cheshire/parse-string (slurp "config/core-db.json") true)))
