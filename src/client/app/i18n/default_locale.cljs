(ns app.i18n.default-locale (:require app.i18n.en-US [untangled.i18n.core :as i18n]))

(reset! i18n/*current-locale* "en-US")

(swap! i18n/*loaded-translations* #(assoc % :en-US app.i18n.en-US/translations))