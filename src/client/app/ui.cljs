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
  ; to lose track of it, or deal with the server not wanting to see it!
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
        (when (nil? comments)
          ; LAZY LOAD the comments. The original load elided comments. load-field derives the query from the UI,
          ; roots it via the component's ident, and places state markers in the app state (so you can render spinners/loading).
          ; lazily-loaded (read the source) is an example of how to leverage this.
          ; IMPORTANT: The component to load MUST include :ui/fetch-state in order for you to see these state markers.
          (dom/button #js {:onClick #(df/load-field this :comments)} (tr "Show comments")))
        (df/lazily-loaded render-comments comments)))))

(def ui-data-item (om/factory DataItem))

(defui ^:once Root
  static om/IQuery
  (query [this] [:ui/locale :ui/react-key {:some-data (om/get-query DataItem)}])
  Object
  (render [this]
    (let [{:keys [ui/locale ui/react-key some-data] :or {ui/react-key "ROOT"}} (om/props this)]
      (dom/div #js {:key react-key}
        ;; the build in mutation for setting locale triggers re-renders of translated strings
        (dom/select #js {:value locale :onChange (fn [evt] (om/transact! this `[(ui/change-locale {:lang ~(.. evt -target -value)})]))}
          (dom/option #js {:value "en-US"} "English")
          (dom/option #js {:value "es-MX"} "Español"))
        (df/lazily-loaded ui-data-item some-data)))))
