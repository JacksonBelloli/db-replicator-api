(ns db-replicator-api.validator
   (:require [db-replicator-api.util :as util]))

(defn api-valid?
   [hash-code data]
   (= hash-code (util/md5 data)))

(defn get-valid?
   [params]
   (cond
      (= (contains?  params :code) false) false
      (= (contains?  params :id) false) false
      :else (api-valid? (:code params) (:id params))))


(defn post-valid?
   [request]
   true)
