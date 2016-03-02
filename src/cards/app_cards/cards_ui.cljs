(ns app-cards.cards-ui
  (:require [devcards.core :as dc :include-macros true]
            [app-cards.sample-card]))

(defn start []
  (dc/start-devcard-ui!))

(start)
