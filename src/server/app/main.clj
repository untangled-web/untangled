(ns app.main
  (:require
    [com.stuartsierra.component :as component]
    [app.system :as sys]
    [untangled.server.core :as c]
    [untangled.server.impl.components.config :refer [load-config]]
    [untangled.datomic.schema :as schema]
    [untangled.datomic.core :as dc]
    [taoensso.timbre :as timbre])
  (:gen-class))

; thin wrappers around System for mocking purposes
(def console (System/console))
(defn exit [exit-code]
  (System/exit exit-code))

(defn exit-if-headly
  "Exits with specified unix-y exit code, if the program is being run from a command line."
  [exit-code]
  (if console (exit exit-code)))

(defn -main
  "Main entry point for the server"
  [& args]
  (let [system (sys/make-system)]
    (component/start system)))
