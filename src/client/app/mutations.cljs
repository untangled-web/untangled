(ns app.mutations
  (:require [untangled.client.mutations :as m]))

(defmethod m/mutate 'set-data [{:keys [state]} k p]
  {:action (fn [] (swap! state assoc :some-data "Updated data"))})
