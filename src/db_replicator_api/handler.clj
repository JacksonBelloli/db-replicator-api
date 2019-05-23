(ns db-replicator-api.handler
	 (:require [compojure.core :refer :all]
							[compojure.route :as route]
							[ring.middleware.defaults :refer [wrap-defaults api-defaults]]
							[cheshire.core :as cheshire]
							[ring.middleware.json :refer [wrap-json-body]]
							[db-replicator-api.configuration :as config]
							[db-replicator-api.util :refer :all]
							[db-replicator-api.database :refer :all]
							[db-replicator-api.validator :as validator]))


(defroutes app-routes
	(GET "/get/:table/all"
		[table & params]
		(if (validator/get-valid? params)
			(let [arguments (validator/remove-code-from-arguments params)]
				(if (= 0 (count arguments))
					(->
						(db-select-all config/core-db table)
						(generate-json))
					(->
						(db-select-all-where config/core-db table arguments)
						(generate-json))))
			(generate-json {:acess "Acesso Negado"} 401)))
	(POST "/post/:table"
		request
			(if (validator/get-valid? (:params request))
				(db-insert! config/core-db (:table (:params request)) (:body request))
				(generate-json {:acess "Acesso Negado"} 401)))
	(route/not-found "Not Found"))


(def app
	(->
		(wrap-defaults app-routes api-defaults)
		(wrap-json-body {:keywords? true :bigdecimal? true})))
