(ns app.support-viewer
  (:require [untangled.client.core :as core]
            [untangled.support-viewer :as viewer]
            [app.ui :as ui]
            [devtools.core :as devtools]))

(defonce cljs-build-tools
  (do (devtools/enable-feature! :sanity-hints)
      (devtools.core/install!)))

(viewer/start-untangled-support-viewer "support" ui/Root "app")
