(ns user
  (:require
    [clojure.pprint :refer (pprint)]
    [clojure.stacktrace :refer (print-stack-trace)]
    [clojure.tools.namespace.repl :refer [disable-reload! refresh clear set-refresh-dirs]]
    [clojure.tools.nrepl.server :as nrepl]
    [com.stuartsierra.component :as component]
    [datomic-helpers :refer [to-transaction to-schema-transaction ext]]
    [datomic.api :as d]
    [environ.core :refer [env]]
    [taoensso.timbre :refer [info set-level!]]
    [untangled.datomic.schema :refer [dump-schema dump-entity]]
    [clojure.java.io :as io]
    [figwheel-sidecar.repl-api :as ra]
    [app.system :as sys]))

;;FIGWHEEL

(def figwheel-config
  {:figwheel-options {:css-dirs ["resources/public/css"]
                      :open-file-command "/Users/tonykay/projects/team/scripts/figwheel-intellij.sh"}
   :build-ids        ["dev" "test" "cards"]
   :all-builds       (figwheel-sidecar.repl/get-project-cljs-builds)})

(defn start-figwheel
  "Start Figwheel on the given builds, or defaults to build-ids in `figwheel-config`."
  ([]
   (let [props (System/getProperties)
         all-builds (->> figwheel-config :all-builds (mapv :id))]
     (start-figwheel (keys (select-keys props all-builds)))))
  ([build-ids]
   (let [default-build-ids (:build-ids figwheel-config)
         build-ids (if (empty? build-ids) default-build-ids build-ids)]
     (println "STARTING FIGWHEEL ON BUILDS: " build-ids)
     (ra/start-figwheel! (assoc figwheel-config :build-ids build-ids))
     (ra/cljs-repl))))

;;SERVER

(set-refresh-dirs "dev/server" "src/server" "src/shared" "specs/server" "specs/shared")

(defonce system (atom nil))

(set-level! :info)

(defn init
  "Create a web server from configurations. Use `start` to start it."
  []
  (reset! system (sys/make-system)))

(defn start "Start (an already initialized) web server." [] (swap! system component/start))
(defn stop "Stop the running web server." []
  (swap! system component/stop)
  (reset! system nil))

(defn go "Load the overall web server system and start it." []
  (init)
  (start))

(defn reset
  "Stop the web server, refresh all namespace source code from disk, then restart the web server."
  []
  (stop)
  (refresh :after 'user/go))

