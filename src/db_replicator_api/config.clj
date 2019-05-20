(ns db-replicator-api.config)


(defn suported-database
	[]
	[
	 {
		:id 1
		:name "postgres"
		:url ""
		}
	 {
		:id 2
		:name "mysql"
		:url ""
		}
	 ])