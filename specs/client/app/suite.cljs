(ns app.suite
  (:require
    [untangled-spec.reporters.suite :as ts :include-macros true]
    app.tests-to-run
    [devtools.core :as devtools]))

(enable-console-print!)

(devtools/enable-feature! :sanity-hints)
(devtools/install!)

(ts/deftest-all-suite sample-specs #".*-spec")

(sample-specs)
