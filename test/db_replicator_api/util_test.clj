(ns db-replicator-api.util-test
		(:require [midje.sweet :refer :all]
					[db-replicator-api.util :as util]))

(facts "Hash code"
   (fact "123456 is e10adc3949ba59abbe56e057f20f883e"
		"e10adc3949ba59abbe56e057f20f883e" => (util/md5 "123456")))
