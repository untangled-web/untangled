(ns app.system
  (:require
    [untangled.server.core :as core]
    [app.api :as api]
    [om.next.server :as om]
    [taoensso.timbre :as timbre]))

;; IMPORTANT: Remember to load all multi-method namespaces to ensure all of the methods are defined in your parser!

(defn logging-mutate [env k params]
  (timbre/info "Mutation Request: " k)
  (api/apimutate env k params))

(defn make-system []
  (core/make-untangled-server
    :config-path "/usr/local/etc/app.edn"
    :parser (om/parser {:read api/api-read :mutate logging-mutate})
    :parser-injections #{}
    :components {}))
