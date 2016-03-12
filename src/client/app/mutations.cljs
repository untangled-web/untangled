(ns app.mutations
  (:require [untangled.client.mutations :as m]))

; Untangled comes with some built-in mutations. Extend them like this (good idea to use namespaced symbols):

(defmethod m/mutate 'nav/change-tab [{:keys [state]} k {:keys [target]}]
  {:action (fn [] (swap! state assoc :current-tab [target :singleton]))})

(defmethod m/mutate 'app/do-random-thing [e k p] {:remote true})

(defmethod m/mutate 'app/do-failing-remote-thing [e k p] {:remote true})

(defmethod m/mutate 'app/handle-failure [{:keys [state]} k params]
  {:action (fn [] (swap! state assoc :random-data {:a (pr-str params)}))})
