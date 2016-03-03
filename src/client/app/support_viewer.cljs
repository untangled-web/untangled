(ns app.support-viewer
  (:require [untangled.client.core :as core]
            [untangled.support-viewer :as viewer]
            [app.ui :as ui]
            [devtools.core :as devtools]))

(defonce cljs-build-tools
  (do (devtools/enable-feature! :sanity-hints)
      (devtools.core/install!)))

; The support viewed expects an id parameter in the URL to indicate which case to load from the server. You must write
; server code to persist and fetch these via whatever data store you've chosen.
(viewer/start-untangled-support-viewer "support" ui/Root "app")
