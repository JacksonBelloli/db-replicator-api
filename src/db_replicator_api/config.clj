(ns db-replicator-api.config)

"
TODO
ALL CONFIG INFORMATION NEED TO BE IMPORTED OF A FILE
"
(defn suported-database
	[]
	[{:id 1
		:name "postgres"
		:url ""}
	 {:id 2
		:name "mysql"
		:url ""}
	 ])

(def core-db
	{:dbtype "postgres"
	:dbname "master"
	:user "admin"
	:password "admin"})
