(ns db-replicator-api.handler-test
  (:require [midje.sweet :refer :all]
            [ring.mock.request :as mock]
            [db-replicator-api.handler :refer :all]))


(facts "Invalid Route"
   (fact "response status is 404"
      (let [response (app (mock/request :get "/invalid"))]
      (:status response) => 404)))
