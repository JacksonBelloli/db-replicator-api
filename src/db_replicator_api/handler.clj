(ns db-replicator-api.handler
	 (:require [compojure.core :refer :all]
							[compojure.route :as route]
							[ring.middleware.defaults :refer [wrap-defaults api-defaults]]
							[cheshire.core :as cheshire]
							[ring.middleware.json :refer [wrap-json-body]]
							[db-replicator-api.configuration :as config]
							[db-replicator-api.util :refer :all]
							[db-replicator-api.database :refer :all]
							[db-replicator-api.validator :as validator]
							[db-replicator-api.replicator :as replicator]))


(defroutes app-routes
	(GET "/get/:table/all"
		[table & params]
		(if (validator/get-valid? params)
			(let [arguments (validator/remove-code-from-arguments params)]
					(->
						(db-select-all-where config/core-db table (conj arguments {:dt_deleted nil}))
						(generate-json)))
			(generate-json {:message "Acesso Negado"} 401)))

	(GET "/execute/:process/:direction"
		[process direction]
		(println "Processo" process " direcao " direction "iniciado...")
		(->
			(replicator/init config/core-db process direction)
			(generate-json)))

	(POST "/post/:table"
		request
			(if (validator/get-valid? (:params request))
				(db-insert! config/core-db (:table (:params request)) (:body request))
				(generate-json {:message "Acesso Negado"} 401)))

	(PUT "/update/:table/:id"
		request
		(if (validator/put-valid? (:params request))
			(db-update-where! config/core-db (:table (:params request)) (:body request) {:id (:id (:params request))})
			(generate-json {:message "Acesso Negado"} 401)))

	(DELETE "/delete/:table/:id"
		request
		(if (validator/delete-valid? (:params request))
			(db-update-where! config/core-db (:table (:params request)) (:body request) {:id (:id (:params request))})
			(generate-json {:message "Acesso Negado"} 401)))

	(route/not-found "Not Found"))


(def app
	(->
		(wrap-defaults app-routes api-defaults)
		(wrap-json-body {:keywords? true :bigdecimal? true})))
