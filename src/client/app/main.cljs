(ns app.main
  (:require [app.ui :as ui]
            [app.core :as core]
            app.i18n.default-locale
            app.mutations
            [untangled.client.core :as uc]))

(reset! core/app (uc/mount @core/app ui/Root "app"))
