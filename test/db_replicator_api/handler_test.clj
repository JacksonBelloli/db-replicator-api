(ns db-replicator-api.handler-test
  (:require [midje.sweet :refer :all]
            [ring.mock.request :as mock]
            [db-replicator-api.handler :refer :all]))


(facts "Hello World on root"
		(fact "response status is 200"
				(let [response (app (mock/request :get "/"))]
						(:status response) => 200))
		(fact "response body is hello world"
				(let [response (app (mock/request :get "/"))]
						(:body response) => "Hello World")))

(facts "Invalid Route"
		(fact "response status is 404"
				(let [response (app (mock/request :get "/invalid"))]
						(:status response) => 404)))