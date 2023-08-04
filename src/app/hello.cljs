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
(defn square []
  (let [value (r/atom nil)]
    (fn []
      [:button.square
       {:on-click #(reset! value "x")} 
       @value])))

(defn board []
 [:div
  [:div.board-row
   [square]
   [square]
   [square]]
  [:div.board-row
   [square]
   [square]
   [square]]
  [:div.board-row
   [square]
   [square]
   [square]]]
  )

(defn hello []
  [:<>
   [:p "Hello, react-tutorial is running!"]
   [:p "Here's an example of using a component with state:"]
   [click-counter click-count]
   [board]])