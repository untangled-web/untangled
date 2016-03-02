(ns app.core
  (:require
    app.mutations
    [untangled.client.core :as uc]
    [untangled.client.data-fetch :as df]
    [app.ui :as ui]
    [om.next :as om]))

(defonce app (atom (uc/new-untangled-client
                     :initial-state (atom {
                                           :data-item {42 {:text "Nothing loaded..."}}
                                           :some-data [:ui :data-item]})

                     :started-callback
                     (fn [{:keys [reconciler]}]
                       (df/load-singleton reconciler (om/get-query ui/Root) :without #{:react-key :comments})))))

