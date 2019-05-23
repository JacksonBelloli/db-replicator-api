(ns db-replicator-api.util
		(:require [cheshire.core :as cheshire]))

(defn generate-json
	"Generate a json output to the api"
	[content & [status]]
	{:status (or status 200)
	 :headers  {"Content-Type" "application/json; charset=utf8"}
	 :body (cheshire/generate-string content)})


 (import 'java.security.MessageDigest
         'java.math.BigInteger)

(defn md5 [s]
	(let [algorithm (MessageDigest/getInstance "MD5")
			raw (.digest algorithm (.getBytes s))]
		(format "%032x" (BigInteger. 1 raw))))
