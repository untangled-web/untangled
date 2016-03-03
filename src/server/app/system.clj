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

; build the server
(defn make-system []
  (core/make-untangled-server
    ; where you want to store your override config file
    :config-path "/usr/local/etc/app.edn"
    ; Standard Om parser
    :parser (om/parser {:read api/api-read :mutate logging-mutate})
    ; The keyword names of any components you want auto-injected into the parser env (e.g. databases)
    :parser-injections #{}
    ; Additional components you want added to the server
    :components {}))
