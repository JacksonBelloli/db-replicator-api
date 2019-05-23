(ns db-replicator-api.validator-test
		(:require [midje.sweet :refer :all]
							[db-replicator-api.validator :as validator]))

(facts "Hash code"
   (fact "id 123456 is equals to the md5 code"
		true => (validator/api-valid? "e10adc3949ba59abbe56e057f20f883e" "123456"))
	(fact "id 123456 is not equals to the md5 code"
		false => (validator/api-valid? "e8adc3949ba59abbe56e057f20f883e" "123456"))
	(fact "api get is valid"
		true => (validator/get-valid? {:id "123456" :code "e10adc3949ba59abbe56e057f20f883e"}))
	(fact "api get is valid"
		false => (validator/get-valid? {:id "123456" :code "e8adc3949ba59abbe56e057f20f883e"})))
