(ns db-replicator-api.handler
	 (:require [compojure.core :refer :all]
							[compojure.route :as route]
							[ring.middleware.defaults :refer [wrap-defaults api-defaults]]
							[cheshire.core :as cheshire]
							[ring.middleware.json :refer [wrap-json-body]]
							[db-replicator-api.config :as config]
							[db-replicator-api.util :refer :all]
							[db-replicator-api.database :refer :all]))


(defroutes app-routes
	(GET "/" [] "Hello World")
	(GET "/get/:table/all"
		[table-name]
		(->
			(db-select-all config/core-db table-name)
			(generate-json)))
	(POST "/post/:table"
		request
			(do
				(println (:table (:params request)) (handle-post-request request))
				(db-insert! config/core-db (:table (:params request)) (handle-post-request request))))
	(route/not-found "Not Found"))


(def app
	(->
		(wrap-defaults app-routes api-defaults)
		(wrap-json-body {:keywords? true :bigdecimal? true})))
