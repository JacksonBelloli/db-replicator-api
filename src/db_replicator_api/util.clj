(ns db-replicator-api.util
		(:require [cheshire.core :as json]))

(defn generate-json
	[content & [status]]
	{:status (or status 200)
	 :headers  {"Content-Type" "application/json; charset=utf8"}
	 :body (json/generate-string content)})
