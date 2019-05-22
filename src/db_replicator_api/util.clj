(ns db-replicator-api.util
		(:require [cheshire.core :as json]))

(defn generate-json
	"Generate a json output to the api"
	[content & [status]]
	{:status (or status 200)
	 :headers  {"Content-Type" "application/json; charset=utf8"}
	 :body (json/generate-string content)})

(defn handle-post-request
	"Verifies if the request can be add to the database"
	"TODO"
	[request]
	(:body request))