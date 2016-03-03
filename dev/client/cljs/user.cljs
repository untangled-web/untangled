(ns cljs.user
  (:require
    [app.core :refer [app]]
    [untangled.client.core :as core]
    app.i18n.default-locale
    app.i18n.locales
    [cljs.pprint :refer [pprint]]
    [devtools.core :as devtools]
    [untangled.client.logging :as log]
    [app.ui :as ui]))

(enable-console-print!)

; Use Chrome...these enable proper formatting of cljs data structures!
(devtools/enable-feature! :sanity-hints)
(devtools/install!)

; Mount the app and remember it.
(reset! app (core/mount @app ui/Root "app"))

; use this from REPL to view bits of the application db
(defn log-app-state
  "Helper for logging the app-state, pass in top-level keywords from the app-state and it will print only those
  keys and their values."
  [& keywords]
  (pprint (let [app-state @(:reconciler @app)]
            (if (= 0 (count keywords))
              app-state
              (select-keys app-state keywords)))))

; Om/dev logging level
(log/set-level :none)
