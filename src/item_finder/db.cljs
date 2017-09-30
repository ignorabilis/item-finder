(ns item-finder.db
  (:require [clojure.spec.alpha :as s]))

; TODO - provide a proper spec here; also the login view should actually be a local state
#_(s/def ::greeting string?)
(s/def ::app-db
  (s/keys :req-un [::login-view]))

;; initial state of app-db
(def app-db
  {:greeting "Hi, you rock!"
   :login-view
   {:username nil
    :password nil}})
