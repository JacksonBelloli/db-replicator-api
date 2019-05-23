(ns db-replicator-api.config
	(:require [cheshire.core :as cheshire]))

(defn core-db []
	(do
		(println "test")
		(cheshire/parse-string (slurp "config/core-db.json") true)))
