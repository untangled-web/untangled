(ns app.server-spec
  (:require [untangled-spec.core :refer [specification provided behavior assertions]]))

(specification "A Sample Specificaation"
  (behavior "Has some items"
    (assertions
      "Server logic is ok"
      (if true 2 1) => 2)))
