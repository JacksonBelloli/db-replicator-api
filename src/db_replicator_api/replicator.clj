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
   (db-select-all-where-order core-db :OrderProcess {:id_process process-id} :order_process))

(defn get-direction-information
   [core-db direction]
      [(get-db core-db (:id_origin direction))
      (get-db core-db (:id_destin direction))])

(defn remove-extra-elements
   [index order core-db destin destin-elements]
   (if (< index (count destin-elements))
      (println "Removendo um elemento")
      (let [key-name (keyword (get order :key_name))]
         (db-delete-where destin (get order :table_destin)
               {key-name (get (nth destin-elements index) key-name)}))))

(defn execute-changes
   [order core-db origin destin element]
   (let [key-name (keyword (get order :key_name))]
      (let [contitions {key-name (get element key-name)} table (get order :table_destin)]
         (if (= 0 (count (db-select-all-where destin table contitions)))
               (do
                  (println "Inserindo novo elemento")
                  (db-insert! destin table element))
               (do
                  (println "Atualizando um elemento")
                  (db-update-where! destin table element contitions))))))

(defn execute-elements
   [index order core-db origin destin origin-elements destin-elements]
   (if (< index (count origin-elements))
      (let [element-origin (nth origin-elements index)
            key-name (keyword (get order :key_name))]
         (if-not (some #(= % element-origin) destin-elements)
            (do
               (execute-changes order core-db origin destin element-origin)
               (recur (inc index) order core-db origin destin origin-elements
                     (remove #(= (get % key-name) (get element-origin key-name)) destin-elements)))
            (recur (inc index) order core-db origin destin origin-elements
                  (remove #(= (get % key-name) (get element-origin key-name)) destin-elements))))
      (remove-extra-elements 0 order core-db destin destin-elements)))

(defn execute-table
   [order core-db process direction origin destin]
   (println "Replicando tabela: " (get order :table_origin) "->" (get order :table_destin))
   (execute-elements 0 order core-db origin destin
                     (db-select-all origin (get order :table_origin))
                     (db-select-all destin (get order :table_destin))))

(defn execute
   ([core-db process]
      (let [id (:id process)]
         (execute core-db process
               (get-table-order core-db id)
               (get-db-direction core-db id))))
   ([core-db process order direction]
      (execute 0 core-db process order direction (get-direction-information core-db direction)))
   ([index core-db process order direction [origin destin]]
      (if (< index (count order))
         (let [db_origin (generate-db (:db_type origin) (:name origin)
                                       (:ip origin) (:port origin)
                                       (:user_origin direction) (:password_origin direction))
               db_destin (generate-db (:db_type destin) (:name destin)
                                             (:ip destin) (:port destin)
                                             (:user_destin direction) (:password_destin direction))]
               (execute-table (nth order index) core-db process direction db_origin db_destin)
         (recur (inc index) core-db process order direction [origin destin])))))

(defn init
   [core-db process-id]
   (println "Inciando replicacao de tabelas...")
   (execute core-db (first (db-select-all-where core-db :Process {:id process-id}))))
