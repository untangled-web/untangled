(ns app.main
  (:require [app.ui :as ui]
            [app.core :as core]
            app.i18n.default-locale
            app.mutations
            [untangled.client.core :as uc]))

; PRODUCTION entry point. Not used during development mode. Simply mounts the app on the DOM.

; The reset is so we know the mounted app. Mounting an already mounted app will just re-render it. See ALSO dev/client/cljs/user.cljs
(reset! core/app (uc/mount @core/app ui/Root "app"))
