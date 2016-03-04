(ns app.core
  (:require
    app.mutations
    [untangled.client.core :as uc]
    [untangled.i18n :refer-macros [tr trf]]
    [untangled.client.data-fetch :as df]
    [app.ui :as ui]
    [om.next :as om]))

(defonce app (atom (uc/new-untangled-client
                     ; can pass an atom, which means you hand normalized it already. Untangled ALWAYS normalizes incoming merged data
                     :initial-state (atom {
                                           :data-item   {42 {:text (tr "Nothing loaded...")}}

                                           :main        {:singleton {:id :singleton :tab/label (tr "Main") :tab/type :main}}
                                           :settings    {:singleton {:id :singleton :tab/label (tr "Settings") :tab/type :settings}}

                                           :current-tab [:main :singleton] ; switch to :settings :singleton to change tabs

                                           :some-data   [:ui :data-item]})
                     ; Called right after the app has loaded and mounted on the DOM
                     :started-callback
                     (fn [{:keys [reconciler]}]
                       ; Load a query from the server into app state, eliding any of the :without keywords (recursively)
                       (df/load-singleton reconciler (om/get-query ui/Root) :without #{:current-tab :comments})))))

