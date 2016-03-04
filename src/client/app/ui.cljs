(ns app.ui
  (:require [om.dom :as dom]
            [om.next :as om :refer-macros [defui]]
            [untangled.i18n :refer-macros [tr trf]]
            yahoo.intl-messageformat-with-locales
            [untangled.client.data-fetch :as df]))

; Standard Om UI code.

; NOTE: Data fetch helpers load-singleton, load-collection, and load-field allow for easy lazy loading based on UI
; queries that can be triggered via events. These do transact! underneath, and should work find.

(defui ^:once Comment
  static om/IQuery
  ; ANY property namespaced to :ui/... will NEVER appear in a server query. They are automatically elided in the plubming
  ; for your convenience. This allows you to use things like :ui/visible to store state in the app state without having
  ; to use local component state or deal with the server not wanting to see it in queries.
  ; See also data-fetch mutation helpers like `df/set-string!` (MEANT ONLY for UI attributes)
  (query [this] [:ui/fetch-state :id :author :text])
  static om/Ident
  (ident [this props] [:comment/by-id (:id props)])
  Object
  (render [this]
    (let [{:keys [id author text]} (om/props this)]
      (dom/div #js {:className "comment"}
        (dom/span #js {:className "value"} text)
        ;; i18n is as simple as wrapping strings in tr or trf and leveraging the i18n lein plugin!
        (dom/span #js {:className "author"} (trf "By {author}" :author author))))))

(def ui-comment (om/factory Comment {:keyfn :id}))

(defn render-comments [comments] (mapv ui-comment comments))

(defui ^:once DataItem
  static om/IQuery
  (query [this] [:ui/fetch-state :text {:comments (om/get-query Comment)}])
  static om/Ident
  (ident [this props] [:data-item 42])
  Object
  (render [this]
    (let [{:keys [text comments]} (om/props this)]
      (dom/div nil
        (dom/span nil text)
        (dom/div #js {:className "comments"}
          (when (nil? comments)
            ; LAZY LOAD the comments. The original load elided comments. load-field derives the query from the UI,
            ; roots it via the component's ident, and places state markers in the app state (so you can render spinners/loading).
            ; lazily-loaded (read the source) is an example of how to leverage this.
            ; IMPORTANT: The component to load MUST include :ui/fetch-state in order for you to see these state markers.
            (dom/button #js {:className "show-button" :onClick #(df/load-field this :comments)} (tr "Show comments")))
          (df/lazily-loaded render-comments comments))))))

(def ui-data-item (om/factory DataItem))

(defui ^:once SettingsTab
  static om/IQuery
  (query [this] [:id :tab/type :tab/label])
  Object
  (render [this]
    (dom/div nil
      (dom/h1 nil "Settings")
      (dom/p nil "Settings go here..."))))

(def ui-settings-tab (om/factory SettingsTab))

(defui ^:once MainTab
  static om/IQuery
  (query [this] [:id :tab/type :tab/label {:some-data (om/get-query DataItem)}])
  Object
  (render [this]
    (let [{:keys [some-data]} (om/props this)]
      (dom/div nil
        (dom/h1 nil "Main")
        (df/lazily-loaded ui-data-item some-data)))))

(def ui-main-tab (om/factory MainTab))

(defui ^:once Tab
  static om/IQuery
  (query [this] {:main (om/get-query MainTab) :settings (om/get-query SettingsTab)})
  static om/Ident
  (ident [this props] [(:tab/type props) (:id props)])
  Object
  (render [this]
    (let [{:keys [id tab/type] :as props} (om/props this)]
      (case type
        :main (ui-main-tab props)
        :settings (ui-settings-tab props)
        (dom/div nil "MISSING TAB")))))

(def ui-tab (om/factory Tab))

(defui ^:once Root
  static om/IQuery
  (query [this] [:ui/locale :ui/react-key {:current-tab (om/get-query Tab)}])
  Object
  (render [this]
    (let [{:keys [current-tab ui/locale ui/react-key some-data] :or {ui/react-key "ROOT"} :as props} (om/props this)]
      (dom/div #js {:key react-key} ; needed for forced re-render to work on locale changes and hot reload
        (dom/div nil
          (dom/ul nil
            (dom/li nil (dom/a #js {:onClick #(om/transact! this '[(nav/change-tab {:target :main})])} (tr "Main")))
            (dom/li nil (dom/a #js {:onClick #(om/transact! this '[(nav/change-tab {:target :settings})])} (tr "Settings"))))
          (ui-tab current-tab))

        ;; the build in mutation for setting locale triggers re-renders of translated strings
        (dom/select #js {:className "locale-selector" :value locale :onChange (fn [evt] (om/transact! this `[(app/change-locale {:lang ~(.. evt -target -value)})]))}
          (dom/option #js {:value "en-US"} "English")
          (dom/option #js {:value "es-MX"} "Español"))
        ))))
