(ns db-replicator-api.validator
   (:require [db-replicator-api.util :as util]))

(defn api-valid?
   [code data]
   (= code (util/md5 (str data "protoss"))))

(defn get-valid?
   [params]
   (cond
      (= (contains?  params :code) false) false
      (= (contains?  params :id) false) false
      :else (api-valid? (:code params) (:id params))))

(defn post-valid?
   [params]
   (cond
      (= (contains?  params :code) false) false
      (= (contains?  params :id) false) false
      (= (contains?  params :table) false) false
      :else (api-valid? (:code params) (:id params))))
