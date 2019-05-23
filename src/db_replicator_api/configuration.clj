(ns db-replicator-api.configuration
	(:require [cheshire.core :as cheshire]))

(def core-db
	(do
		(cheshire/parse-string (slurp "config/core-db.json") true)))
