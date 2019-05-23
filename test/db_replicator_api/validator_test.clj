(ns db-replicator-api.validator-test
		(:require [midje.sweet :refer :all]
							[db-replicator-api.validator :as validator]))

(facts "Hash code"
   (fact "id 123456 is equals to the md5 code"
		true =>
				(validator/api-valid?
					"81f0c2e5f646b361c3736bfbe6be8db5" "123456"))
	(fact "id 123456 is not equals to the md5 code"
		false =>
				(validator/api-valid?
					"e8adc3949ba59abbe56e057f20f883e" "123456"))
	(fact "api get is valid"
		true =>
				(validator/get-valid?
					{:code1 "123456" :code2 "81f0c2e5f646b361c3736bfbe6be8db5"}))
	(fact "api get is valid"
		false =>
				(validator/get-valid?
					{:code1 "123456" :code2 "e8adc3949ba59abbe56e057f20f883e"}))
	(fact "api post is valid"
		true =>
				(validator/post-valid?
					{:id "123456" :code "81f0c2e5f646b361c3736bfbe6be8db5" :table "User"}))
	(fact "api post isnt valid"
		false =>
				(validator/post-valid?
					{:id "123456" :code "e8adc3949ba59abbe56e057f20f883e" :table "User"})))
