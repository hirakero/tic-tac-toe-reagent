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

#_(defn square [& {:keys [value on-click]}]
  [:button.square {:on-click on-click} value])
(defn square [& {:keys [value]}]
  [:button.square value])

(defn board []
 [:div
  [:div.board-row
   [square :value 1]
   [square :value 2]
   [square :value 3]]
  [:div.board-row
   [square :value 4]
   [square :value 5]
   [square :value 6]]
  [:div.board-row
   [square :value 7]
   [square :value 8]
   [square :value 9]]]
  )

(defn hello []
  [:<>
   [:p "Hello, react-tutorial is running!"]
   [:p "Here's an example of using a component with state:"]
   [click-counter click-count]
   [board]])