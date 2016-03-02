(ns app.api
  (:require [om.next.server :as om]
            [taoensso.timbre :as timbre]))

(defmulti apimutate om/dispatch)

(defmethod apimutate :default [e k p]
  (timbre/error "Unrecognized mutation " k))

(defn api-read [{:keys [query]} k params]
  (Thread/sleep 1000)
  (case k
    :some-data {:value {:text "Hello from the server!"}}
    :data-item {:value {:comments [{:id 1 :text "Hi there!" :author "Sam"}
                                   {:id 2 :text "Hooray!" :author "Sally"}
                                   {:id 3 :text "La de da!" :author "Mary"}]}}
    (timbre/error "Unrecognized query for " k " : " query)))
