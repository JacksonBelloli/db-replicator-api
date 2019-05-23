(ns db-replicator-api.validator
   (:require [db-replicator-api.util :as util]))

(defn api-valid?
   [code data]
   (= code (util/md5 (str data "protoss"))))

(defn get-valid?
   [params]
   (cond
      (= (contains?  params :code1) false) false
      (= (contains?  params :code2) false) false
      :else (api-valid? (:code2 params) (:code1 params))))

(defn post-valid?
   [params]
   (cond
      (= (contains?  params :code) false) false
      (= (contains?  params :id) false) false
      (= (contains?  params :table) false) false
      :else (api-valid? (:code params) (:id params))))

(defn remove-code-from-arguments
   [arguments]
   (cond
      (contains? arguments :code) (recur (dissoc arguments :code))
      (contains? arguments :code1) (recur (dissoc arguments :code1))
      (contains? arguments :code2) (recur (dissoc arguments :code2))
      :else arguments))
