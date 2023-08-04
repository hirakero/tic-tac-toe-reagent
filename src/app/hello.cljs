(ns app.hello
  (:require [reagent.core :as r]))

(defn click-counter [click-count]
  [:div
   "The atom " [:code "click-count"] " has value: "
   @click-count ". "
   [:input {:type "button" :value "Click me!"
            :on-click #(swap! click-count inc)}]])

(def click-count (r/atom 0))

#_(defn game []
  [:div.game
   [:dib.game-board
    [board]
    [:div.game-info
     [:div "status"]
     [:ol "todo"]]]])

#_(defn sqare [& {:keys [value on-click]}]
  [:button.square {:on-click on-click} value])
(defn board []
 [:div
  [:div.board-row
   [:button.square "1"]
   [:button.square "2"]
   [:button.square "3"]]
  [:div.board-row
   [:button.square "4"]
   [:button.square "5"]
   [:button.square "6"]]
  [:div.board-row
   [:button.square "7"]
   [:button.square "8"]
   [:button.square "9"]]]
  )

(defn hello []
  [:<>
   [:p "Hello, react-tutorial is running!"]
   [:p "Here's an example of using a component with state:"]
   [click-counter click-count]
   [board]])