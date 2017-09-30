(ns item-finder.subs
  (:require [re-frame.core :refer [reg-sub]]))

(reg-sub
  :login-view/get-credentials
  (fn [db _]
    (get-in db [:login-view])))

(reg-sub
  :get-greeting
  (fn [db _]
    (get-in db [:greeting])))