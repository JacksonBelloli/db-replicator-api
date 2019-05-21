(ns db-replicator-api.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults api-defaults]]
            [cheshire.core :as json]
            [db-replicator-api.config :refer :all]
            [db-replicator-api.util :refer :all]
            [ring.middleware.json :refer [wrap-json-body]]
            [db-replicator-api.database :refer :all]))

(defroutes app-routes
  (GET "/" [] "Hello World")
  (GET "/get/data-bases" [] (->
                              (suported-database)
                              (generate-json)))
  (GET "/get/replication" [] (->
                              (select-all :replication)
                              (generate-json)))
  (GET "/get/replication-process" [] {})
  (GET "/get/replication-table" [] {})
  (GET "/get/replication-direction" [] {})
  (POST "/post/replication" request (insert-t :replication (:body request)))
  (POST "/post/replication-process" request {})
  (POST "/post/replication-table" request {})
  (POST "/post/replication-direction" request {})
  (route/not-found "Not Found"))


(def app
  (->
    (wrap-defaults app-routes api-defaults)
    (wrap-json-body {:keywords? true :bigdecimal? true})))
