(ns db-replicator-api.handler-test
  (:require [midje.sweet :refer :all]
            [ring.mock.request :as mock]
            [db-replicator-api.handler :refer :all]))


(facts "Da um hello world na rota raiz"
		(fact "o status da reposta e 200"
				(let [response (app (mock/request :get "/"))]
						(:status response) => 200))
		(fact "o texto do corpo e hello world"
				(let [response (app (mock/request :get "/"))]
						(:body response) => "Hello World")))

(facts "Rota invalida nao existe"
		(fact "o codigo de erro e 404"
				(let [response (app (mock/request :get "/invalid"))]
						(:status response) => 404)))