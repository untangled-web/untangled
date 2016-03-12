(ns app.api
  (:require [om.next.server :as om]
            [taoensso.timbre :as timbre]))

(defmulti apimutate om/dispatch)

;; your entry point for handling mutations. Standard Om mutate handling. All plumbing is taken care of. UNLIKE Om, if you
; return :tempids from your :action, they will take effect on the client automatically without post-processing.
(defmethod apimutate :default [e k p]
  (timbre/error "Unrecognized mutation " k))

(defmethod apimutate 'app/do-failing-remote-thing [e k p] (throw (ex-info "Buggery!" {:problem "Failed"})))

(def random-value (atom 42))

(defmethod apimutate 'app/do-random-thing [e k p] {:action (fn [] (swap! random-value inc))})

;; your query entry point (feel free to make multimethod). Standard Om fare here.
(defn api-read [{:keys [ast query] :as env} dispatch-key params]
  (Thread/sleep 10)
  (case dispatch-key
    :random-data {:value {:a @random-value}}
    :data-items {:value [{:db/id 1 :item/text "Data Item 1"}
                         {:db/id 2 :item/text "Data Item 2"}]}
    :data-item (let [{:keys [key]} ast]
                 (if (= (second key) 1)
                   {:value {:item/comments [{:id 1 :text "Hi there!" :author "Sam"}
                                            {:id 2 :text "Hooray!" :author "Sally"}
                                            {:id 3 :text "La de da!" :author "Mary"}]}}
                   {:value {:item/comments [{:id 4 :text "Ooops!" :author "Sam"}]}}))
    (timbre/error "Unrecognized query for " dispatch-key " : " query)))
