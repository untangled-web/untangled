(ns app-cards.sample-card
  (:require
    [om.dom :as dom]
    [devcards.core :refer-macros [defcard dom-node]]))

(defcard select-control-sample
  "A card rendering a select control"
  (fn [state _]
    (dom/select #js {:value        (:selected @state)
                     :defaultValue "none"
                     :onChange     (fn [evt] (swap! state assoc :selected (-> evt .-target .-value)))}
      (dom/option #js {:key "a" :value "none"} "None")
      (dom/option #js {:key "b" :value "1"} "One")
      (dom/option #js {:key "c" :value "2"} "Two")
      (dom/option #js {:key "d" :value "3"} "Three")))
  {:selected "none"}
  {:inspect-data true})

