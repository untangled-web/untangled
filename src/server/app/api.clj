(ns app.api
  (:require [om.next.server :as om]
            [taoensso.timbre :as timbre]))

(defmulti apimutate om/dispatch)

;; your entry point for handling mutations. Standard Om mutate handling. All plumbing is taken care of. UNLIKE Om, if you
; return :tempids from your :action, they will take effect on the client automatically without post-processing.
(defmethod apimutate :default [e k p]
  (timbre/error "Unrecognized mutation " k))

;; your query entry point (feel free to make multimethod). Standard Om fare here.
(defn api-read [{:keys [query]} k params]
  (Thread/sleep 1000) ; playground delay...make it seem like there is some server work involved
  (case k
    :some-data {:value {:text "Hello from the server!"}}
    :data-item {:value {:comments [{:id 1 :text "Hi there!" :author "Sam"}
                                   {:id 2 :text "Hooray!" :author "Sally"}
                                   {:id 3 :text "La de da!" :author "Mary"}]}}
    (timbre/error "Unrecognized query for " k " : " query)))
