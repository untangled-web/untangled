(ns app.ui
  (:require [om.dom :as dom]
            [om.next :as om :refer-macros [defui]]
            [untangled.i18n :refer-macros [tr trf]]
            yahoo.intl-messageformat-with-locales
            [untangled.client.data-fetch :as df]))

(defui ^:once Comment
  static om/IQuery
  (query [this] [:ui/fetch-state :id :author :text])
  static om/Ident
  (ident [this props] [:comment/by-id (:id props)])
  Object
  (render [this]
    (let [{:keys [id author text]} (om/props this)]
      (dom/div #js {:className "comment"}
        (dom/span #js {:className "value"} text)
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
          (dom/button #js {:onClick #(df/load-field this :comments)} (tr "Show comments")))
        (df/lazily-loaded render-comments comments)))))

(def ui-data-item (om/factory DataItem))

(defui ^:once Root
  static om/IQuery
  (query [this] [:app/locale :react-key {:some-data (om/get-query DataItem)}])
  Object
  (render [this]
    (let [{:keys [app/locale react-key some-data] :or {react-key "ROOT"}} (om/props this)]
      (dom/div #js {:key react-key}
        (dom/select #js {:value locale :onChange (fn [evt] (om/transact! this `[(app/change-locale {:lang ~(.. evt -target -value)})]))}
          (dom/option #js {:value "en-US"} "English")
          (dom/option #js {:value "es-MX"} "Español"))
        (df/lazily-loaded ui-data-item some-data)))))
