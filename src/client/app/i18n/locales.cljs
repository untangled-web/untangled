(ns
 app.i18n.locales
 (:require
  goog.module
  goog.module.ModuleLoader
  [goog.module.ModuleManager :as module-manager]
  [untangled.i18n.core :as i18n]
  app.i18n.en-US
  app.i18n.es-MX)
 (:import goog.module.ModuleManager))

(defonce manager (module-manager/getInstance))

(defonce modules #js {"en-US" "/en-US.js", "es-MX" "/es-MX.js"})

(defonce module-info #js {"en-US" [], "es-MX" []})

(defonce ^:export loader (let [loader (goog.module.ModuleLoader.)] (.setLoader manager loader) (.setAllModuleInfo manager module-info) (.setModuleUris manager modules) loader))

(defn set-locale [l] (js/console.log (str "LOADING ALTERNATE LOCALE: " l)) (if (exists? js/i18nDevMode) (do (js/console.log (str "LOADED ALTERNATE LOCALE in dev mode: " l)) (reset! i18n/*current-locale* l)) (.execOnLoad manager l (fn after-locale-load [] (js/console.log (str "LOADED ALTERNATE LOCALE: " l)) (reset! i18n/*current-locale* l)))))