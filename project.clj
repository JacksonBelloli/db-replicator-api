(defproject db-replicator-api "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [compojure "1.6.1"]
                 [ring/ring-defaults "0.3.2"]
                 [midje "1.9.6"]
                 [ring/ring-core "1.7.1"]
                 [ring/ring-jetty-adapter "1.7.1"]
                 [clj-http "3.9.1"]
                 [cheshire "5.8.1"]
                 [ring/ring-json "0.4.0"]
                 [org.clojure/java.jdbc "0.7.9"]
                 [mysql/mysql-connector-java "8.0.16"]
                 [org.postgresql/postgresql "42.2.5.jre7"]
                 [org.apache.derby/derby "10.15.1.3"]
                 [com.h2database/h2 "1.4.199"]
                 [org.xerial/sqlite-jdbc "3.27.2.1"]
                 [hsqldb/hsqldb "1.8.0.10"]
                 [com.microsoft.sqlserver/mssql-jdbc "7.2.2.jre11"]
                 [java-jdbc/dsl "0.1.0"]
                 [org.clojure/core.async "0.4.490"]]
  :plugins [[lein-ring "0.12.5"]
            [lein-midje "3.2.1"]]
  :ring {:handler db-replicator-api.handler/app}
  :profiles
  {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                        [ring/ring-mock "0.3.2"]]}})
