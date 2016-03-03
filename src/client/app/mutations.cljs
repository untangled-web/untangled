(ns app.mutations
  (:require [untangled.client.mutations :as m]))

; Untangled comes with some built-in mutations. Extend them like this (good idea to use namespaced symbols):

#_(defmethod m/mutate 'app/set-data [{:keys [state]} k p]
  {:action (fn [] (swap! state assoc :some-data "Updated data"))})
