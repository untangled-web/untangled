(defproject untangled/app "0.0.3"
  :description "A sample app"
  :url ""
  :license {:name "MIT"
            :url  "https://opensource.org/licenses/MIT"}

  :dependencies [[com.datomic/datomic-free "0.9.5206" :exclusions [joda-time]]
                 [com.taoensso/timbre "4.3.1"]
                 [commons-codec "1.10"]
                 [lein-doo "0.1.6" :scope "test"]
                 [org.clojure/clojure "1.7.0"]
                 [org.clojure/clojurescript "1.7.228"]
                 [org.omcljs/om "1.0.0-alpha31" :exclusions [cljsjs/react]]
                 [navis/untangled-client "0.4.5" :exclusions [cljsjs/react org.omcljs/om]]
                 [navis/untangled-server "0.4.5"]
                 [navis/untangled-datomic "0.4.4" :exclusions [com.datomic/datomic-free org.clojure/tools.cli]]
                 [navis/untangled-spec "0.3.5" :scope "test" :exclusions [cljsjs/react-with-addons]]]

  :plugins [[lein-cljsbuild "1.1.2"]
            [lein-doo "0.1.6" :exclusions [org.clojure/tools.reader]]
            [navis/untangled-lein-i18n "0.1.2" :exclusions [org.apache.maven.wagon/wagon-provider-api org.codehaus.plexus/plexus-utils org.clojure/tools.cli]]
            [lein-environ "1.0.0"]
            [com.jakemccrary/lein-test-refresh "0.14.0"]]

  :test-refresh {:report    untangled-spec.reporters.terminal/untangled-report
                 :with-repl true}

  :source-paths ["src/shared" "src/server"]
  :test-paths ["specs/shared" "specs/server" "specs"]
  :uberjar-name "app.jar"
  :jvm-opts ["-server" "-Xmx1024m" "-Xms512m" "-XX:-OmitStackTraceInFastThrow"]
  :clean-targets ^{:protect false} ["resources/public/js/specs" "resources/public/js/compiled" "target" "resources/private/js"]

  :doo {:build "automated-tests"
        :paths {:karma "node_modules/karma/bin/karma"}}

  :cljsbuild {:builds
              [
               {:id           "dev"
                :source-paths ["src/client" "src/shared" "dev/client" "checkouts/untangled-client/src"]
                :figwheel     true
                :compiler     {:main                 cljs.user
                               :asset-path           "js/compiled/dev"
                               :output-to            "resources/public/js/compiled/app.js"
                               :output-dir           "resources/public/js/compiled/dev"
                               :optimizations        :none
                               :parallel-build       false
                               :verbose              false
                               :recompile-dependents true
                               :source-map-timestamp true}}

               {:id           "cards"
                :source-paths ["src/cards" "src/client" "src/shared" "checkouts/untangled-client/src"]
                :figwheel     {:devcards true}
                :compiler     {:main                 app-cards.cards-ui
                               :asset-path           "js/compiled/cards"
                               :output-to            "resources/public/js/compiled/cards.js"
                               :output-dir           "resources/public/js/compiled/cards"
                               :parallel-build       true
                               :optimizations        :none
                               :recompile-dependents true
                               :source-map-timestamp true}}

               {:id           "test"
                :source-paths ["specs/client" "specs/shared" "src/client" "src/shared"]
                :figwheel     true
                :compiler     {:main                 app.suite
                               :output-to            "resources/public/js/specs/specs.js"
                               :output-dir           "resources/public/js/compiled/specs"
                               :asset-path           "js/compiled/specs"
                               :parallel-build       true
                               :recompile-dependents true
                               :optimizations        :none}}

               {:id           "automated-tests"
                :source-paths ["specs/client" "specs/shared" "src/client" "src/shared"]
                :compiler     {:output-to     "resources/private/js/unit-tests.js"
                               :main          app.all-tests
                               :asset-path    "js"
                               :output-dir    "resources/private/js"
                               :optimizations :none
                               }}

               {:id           "production"
                :source-paths ["src/client" "src/shared"]
                :compiler     {:main          app.main
                               :asset-path    "js/compiled/prod"
                               :output-dir    "resources/public/js/compiled/prod"
                               :optimizations :advanced
                               :modules       {:en-US {:output-to "resources/public/js/en-US.js", :entries #{"app.i18n.en-US"}},
                                               :es-MX {:output-to "resources/public/js/es-MX.js", :entries #{"app.i18n.es-MX"}},
                                               :main  {:output-to "resources/public/js/app.js", :entries #{"app.main"}}}
                               }}

               {:id           "i18n"
                :source-paths ["src/client" "src/shared"]
                :compiler     {:main          app.main
                               :output-to     "i18n/app.js"
                               :output-dir    "i18n/out"
                               :optimizations :whitespace}}]}

  :figwheel {:css-dirs ["resources/public/css"]
             :open-file-command "/Users/tonykay/projects/team/scripts/figwheel-intellij.sh"}

  :untangled-i18n {:default-locale        "en-US"
                   :translation-namespace "app.i18n"
                   :source-folder         "src/client"
                   :target-build          "i18n"}

  :profiles {:uberjar {:prep-tasks  ["compile"
                                     ["cljsbuild" "once" "production"]]
                       :omit-source false
                       :main        app.core
                       :env         {:production true}
                       :aot         :all}

             :dev     {:dependencies [[binaryage/devtools "0.5.2"  :exclusions [environ]]
                                      [figwheel-sidecar "0.5.0-3" :exclusions [ring/ring-core joda-time org.clojure/tools.reader]]
                                      [com.cemerick/piggieback "0.2.1"]
                                      [org.clojure/tools.nrepl "0.2.12"]
                                      [devcards "0.2.1-6" :exclusions [org.omcljs/om]]
                                      [ring/ring-mock "0.2.0"]]
                       :source-paths ["dev/server" "src/server" "src/shared" "specs/shared" "specs/server"]
                       :repl-options {:init-ns          user
                                      :nrepl-middleware [cemerick.piggieback/wrap-cljs-repl]}
                       :env          {:dev true}}})
