(ns db-replicator-api.configuration
	(:require [cheshire.core :as cheshire]))

(def core-db
	(try
		(cheshire/parse-string (slurp "config/core-db.json") true)
		(cheshire/parse-string (slurp "config/default-db.json") true)))
