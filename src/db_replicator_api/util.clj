(ns db-replicator-api.util
		(:require [cheshire.core :as cheshire]
						[db-replicator-api.database :refer :all]))

(defn get-execution-content
	[core-db id]
	(special-db-select-execution-logs core-db id))

(defn generate-json
	"Generate a json output to the api"
	[content & [status]]
	{:status (or status 200)
	 :headers  {"Content-Type" "application/json; charset=utf8"}
	 :body (cheshire/generate-string content)})

 (defn generate-execution
 	"Generate a json output to the api"
 	[id core-db & [status]]
	(println (get-execution-content core-db id))
 	{:status (or status 200)
 	 :headers  {"Content-Type" "application/json; charset=utf8"}
 	 :body (cheshire/generate-string (get-execution-content core-db id))})

 (import 'java.security.MessageDigest
         'java.math.BigInteger)

(defn md5 [s]
	(let [algorithm (MessageDigest/getInstance "MD5")
			raw (.digest algorithm (.getBytes s))]
		(format "%032x" (BigInteger. 1 raw))))

(defn generate-db
   [dbtype dbname host port user password]
   {:dbtype dbtype
   :dbname dbname
   :host host
   :port port
   :user user
   :password password})


(defn generate-logs
	[id-execution message type id-user]
	{:id_execution id-execution
		:message message
		:type type
		:id_user id-user})
