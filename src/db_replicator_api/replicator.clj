(ns db-replicator-api.replicator
   (:require [db-replicator-api.database :refer :all]))

(defn generate-db
   [dbtype dbname host port user password]
   {:dbtype dbtype
   :dbname dbname
   :host host
   :port port
   :user user
   :password password})

(defn get-db
   [core-db db-id]
   (first (db-select-all-where core-db :UserDatabase {:id db-id})))

(defn get-db-direction
   [core-db process-id]
   (first (db-select-all-where core-db :DirectionProcess {:id_process process-id})))

(defn get-table-order
   [core-db process-id]
   (db-select-all-where core-db :OrderProcess {:id_process process-id}))

(defn get-direction-information
   [core-db direction]
      [(get-db core-db (:id_origin direction))
      (get-db core-db (:id_destin direction))])

(defn execute-table
   [order core-db process direction origin destin]
   (println origin)
   (db-select-all origin (get order :table_origim)))

(defn execute
   ([core-db process]
      (let [id (:id process)]
         (execute core-db process
               (get-table-order core-db id)
               (get-db-direction core-db id))))
   ([core-db process order direction]
      (execute core-db process order direction (get-direction-information core-db direction)))
   ([core-db process order direction [origin destin]]
      (loop [x (- (count order) 1)]
         (when (> x -1)
            (let [db_origin (generate-db (:db_type origin) (:name origin)
                                          (:ip origin) (:port origin)
                                          (:user_origin direction) (:password_origin direction))
                  db_destin (generate-db (:db_type destin) (:name destin)
                                                (:ip destin) (:port destin)
                                                (:user_destin direction) (:password_destin direction))]
                  (execute-table (nth order x) core-db process direction db_origin db_destin)
            (recur (- x 1)))))))



(defn init
   [core-db process-id]
   (execute core-db (first (db-select-all-where core-db :Process {:id process-id}))))
