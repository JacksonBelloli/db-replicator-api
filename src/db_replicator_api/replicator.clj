(ns db-replicator-api.replicator
   (:require [db-replicator-api.database :refer :all]
            [db-replicator-api.util :refer :all]))

(defn get-db
   [core-db db-id]
   (first (db-select-all-where core-db :UserDatabase {:id db-id})))

(defn get-db-direction
   [core-db direction-id]
   (first (db-select-all-where core-db :DirectionProcess {:id direction-id})))

(defn get-table-order
   [core-db process-id]
   (db-select-all-where-order core-db :OrderProcess {:id_process process-id} :order_process))

(defn get-direction-information
   [core-db direction]
      [(get-db core-db (:id_origin direction))
      (get-db core-db (:id_destin direction))])

(defn insert-logs
   [core-db id-execution message number id]
   (db-insert! core-db :LogsProcess
         (generate-logs id-execution message number id)))

(defn remove-extra-elements
   [index order core-db process destin destin-elements id-execution]
   (if (< index (count destin-elements))
      (do
         (insert-logs core-db id-execution "Removendo um elemento" 3 (:id_user process))
         (let [key-name (keyword (get order :key_name))]
            (db-delete-where destin (get order :table_destin)
                  {key-name (get (nth destin-elements index) key-name)}))
         (recur (inc index) order core-db process destin destin-elements id-execution))))

(defn execute-changes
   [order core-db process origin destin element id-execution]
   (let [key-name (keyword (get order :key_name))]
      (let [contitions {key-name (get element key-name)} table (get order :table_destin)]
         (if (= 0 (count (db-select-all-where destin table contitions)))
               (do
                  (insert-logs core-db id-execution "Inserindo novo elemento" 1 (:id_user process))
                  (db-insert! destin table element))
               (do
                  (insert-logs core-db id-execution "Atualizando um elemento" 1 (:id_user process))
                  (db-update-where! destin table element contitions))))))

(defn execute-elements
   [index order core-db process origin destin origin-elements destin-elements id-execution]
   (if (< index (count origin-elements))
      (let [element-origin (nth origin-elements index)
            key-name (keyword (get order :key_name))]
         (if-not (some #(= % element-origin) destin-elements)
            (do
               (execute-changes order core-db process origin destin element-origin id-execution)
               (recur (inc index) order core-db process origin destin origin-elements
                     (remove #(= (get % key-name) (get element-origin key-name)) destin-elements) id-execution))
            (recur (inc index) order core-db process origin destin origin-elements
                  (remove #(= (get % key-name) (get element-origin key-name)) destin-elements) id-execution)))
      (remove-extra-elements 0 order core-db process destin destin-elements id-execution)))

(defn execute-elements-loop
   [start order core-db process direction origin destin id-execution]
   (let [key-name (keyword (get order :key_name))]
      (if (= 0 start)
         (do
            (let [origin-elements (db-select-all-limit-order origin (get order :table_origin) 10 key-name)]
            (if (= 0 (count origin-elements)) true
            (let [first-id (key-name (first origin-elements)) last-id (key-name (last origin-elements))]
               (execute-elements 0 order core-db process origin destin origin-elements
                              (special-db-select-all-where-order destin (get order :table_destin)
                                    (str (get order :key_name) " >= " "'" first-id "'"
                                    " AND " (get order :key_name) " <= " "'" last-id "'") key-name)
                              id-execution)
               (recur last-id order core-db process direction origin destin id-execution)))))
         (do
            (let [origin-elements (special-db-select-all-where-limit-order origin (get order :table_origin)
                  (str (get order :key_name) " > " "'" start "'") 10 key-name)]
            (if (= 0 (count origin-elements)) true
            (let [last-id (key-name (last origin-elements))]
               (execute-elements 0 order core-db process origin destin origin-elements
                              (special-db-select-all-where-order destin (get order :table_destin)
                                    (str (get order :key_name) " <= " "'" last-id "'"
                                    " AND " (get order :key_name) " > " "'" start "'") key-name)
                              id-execution)
               (recur last-id order core-db process direction origin destin id-execution))))))))

(defn execute-table
   [order core-db process direction origin destin id-execution]
   (try
      (insert-logs core-db id-execution
         (str "Replicando tabela: " (get order :table_origin) "->" (get order :table_destin)) 5 (:id_user process))
      (execute-elements-loop 0 order core-db process direction origin destin id-execution)
   (catch Exception e
      (println e)
      (insert-logs core-db id-execution "ERRO ao execultar tabela" 4 (:id_user process)) 1)))

(defn execute
   ([core-db process direction]
      (let [id (:id process)]
         (execute core-db process (get-table-order core-db id) direction)))
   ([core-db process order direction]
      (let [id-execution (:generated_key (first (db-insert! core-db :Execution
            {:id_direction (:id direction) :id_user (:id_user process) :threads (:threads direction)})))]
         (insert-logs core-db id-execution "Inciando replicacao" 5 (:id_user process))
         (try
            (execute 0 core-db process order direction (get-direction-information core-db direction) id-execution)
         (catch Exception e
            (println e)
            (insert-logs core-db id-execution "ERRO ao execultar execulcao" 4 (:id_user process)) 1))
         (println "Finalizando replicacao")
         (insert-logs core-db id-execution "Finalizando replicacao" 5 (:id_user process))
         (db-update-where! core-db :Execution {:end 1} {:id id-execution})))
   ([index core-db process order direction [origin destin] id-execution]
      (if (< index (count order))
         (let [db_origin (generate-db (:db_type origin) (:name origin)
                                       (:ip origin) (:port origin)
                                       (:user_origin direction) (:password_origin direction))
               db_destin (generate-db (:db_type destin) (:name destin)
                                             (:ip destin) (:port destin)
                                             (:user_destin direction) (:password_destin direction))]
               (execute-table (nth order index) core-db process direction db_origin db_destin id-execution)
         (recur (inc index) core-db process order direction [origin destin] id-execution)))))

(defn init
   [core-db process-id direction-id]
   (execute core-db
         (first (db-select-all-where core-db :Process {:id process-id}))
         (get-db-direction core-db direction-id)))
