(ns db-replicator-api.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [cheshire.core :as json]
            [db-replicator-api.config :refer :all]))

(defroutes app-routes
  (GET "/" [] "Hello World")
  (GET "/data-bases" [] (json/generate-string (suported-database)))
  (route/not-found "Not Found"))


(def app
  (wrap-defaults app-routes site-defaults))
