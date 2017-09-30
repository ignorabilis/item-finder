(ns item-finder.android.core
  (:require [reagent.core :as r :refer [atom]]
            [re-frame.core :refer [subscribe dispatch dispatch-sync]]
            [item-finder.events]
            [item-finder.subs]
            [clojure.string :as string]))

(def ReactNative (js/require "react-native"))

(def app-registry (.-AppRegistry ReactNative))
(def text (r/adapt-react-class (.-Text ReactNative)))
(def view (r/adapt-react-class (.-View ReactNative)))
(def image (r/adapt-react-class (.-Image ReactNative)))
(def touchable-highlight (r/adapt-react-class (.-TouchableHighlight ReactNative)))

(def text-input (r/adapt-react-class (.-TextInput ReactNative)))

(def logo-img (js/require "./images/mglass.png"))

(defn alert [title]
  (.alert (.-Alert ReactNative) title))

(def input-style
  {:height       50
   :align-self   :stretch
   :margin-top   10
   :padding      4
   :font-size    18
   :border-width 1
   :border-color "#48bbec"})

(defn app-root []
  (let [credentials (subscribe [:login-view/get-credentials])]
    (fn []
      (let [{:keys [username password]} @credentials]
        [view {:style
               {:flex             1
                :padding-top      40
                :padding          10
                :align-items      "center"
                :background-color "#fcf5ff"}}
         [image {:source logo-img
                 :style  {:width 80 :height 80 :margin-bottom 30}}]
         [text {:style
                {:font-size   30
                 :font-weight "100"
                 :margin-top  10
                 :text-align  "center"}}
          "Item Findr"]
         [text
          {:style
           {:font-size   20
            :font-weight "100"
            :margin-top  10
            :text-align  "center"}}
          (when-not (string/blank? username)
            (str "Hi, " username "! Let's get to it!"))]
         [text-input
          {:style                   input-style
           :placeholder             "Username"
           :underline-color-android "transparent"
           :on-change-text          #(dispatch [:login-view/set-username %])}]
         [text-input
          {:style                   input-style
           :placeholder             "Password"
           :underline-color-android "transparent"
           :secure-text-entry       true
           :on-change-text          #(dispatch [:login-view/set-password %])}]
         [touchable-highlight
          {:style
           {:height           50
            :background-color "#48bbec"
            :align-self       :stretch
            :padding          9
            :margin-top       10
            :border-radius    3}
           :on-press
           #(alert (str "Username entered: "
                        username
                        "\n"
                        "Password entered: "
                        password))}
          [text
           {:style
            {:color       "white"
             :font-size   22
             :text-align  "center"
             :font-weight "bold"}} "Log in"]]]))))

(defn init []
  (dispatch-sync [:initialize-db])
  (.registerComponent app-registry "ItemFinder" #(r/reactify-component app-root)))
