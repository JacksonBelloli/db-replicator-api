(ns db-replicator-api.validator-test
		(:require [midje.sweet :refer :all]
							[db-replicator-api.validator :as validator]))

(facts "Hash code"
   (fact "id 123456 is equals to the code e10adc3949ba59abbe56e057f20f883e"
		true => (validator/api-valid? "e10adc3949ba59abbe56e057f20f883e" "123456")))
