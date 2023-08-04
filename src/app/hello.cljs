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
(defn square [& {:keys [value on-click]}] 
  [:button.square
   {:on-click on-click}
   value])

(defn board []
  (let [state (r/atom {:squares (vec (repeat 9 nil))
                       :x-is-next? true}) 
        handle-click (fn [i] 
                       (when-not (get-in @state [:squares i])
                         (swap! state assoc-in [:squares i] (if (get @state :x-is-next?) "x" "o"))
                         (swap! state update :x-is-next? not))
                       )]
    (fn [] 
      [:div
       [:div.board-row
        [square :value (get-in @state [:squares 0]) :on-click #(handle-click 0)]
        [square :value (get-in @state [:squares 1]) :on-click #(handle-click 1)]
        [square :value (get-in @state [:squares 2]) :on-click #(handle-click 2)]]
       [:div.board-row
        [square :value (get-in @state [:squares 3]) :on-click #(handle-click 3)]
        [square :value (get-in @state [:squares 4]) :on-click #(handle-click 4)]
        [square :value (get-in @state [:squares 5]) :on-click #(handle-click 5)]]
       [:div.board-row
        [square :value (get-in @state [:squares 6]) :on-click #(handle-click 6)]
        [square :value (get-in @state [:squares 7]) :on-click #(handle-click 7)]
        [square :value (get-in @state [:squares 8]) :on-click #(handle-click 8)]]]))
  )

(defn hello []
  [:<>
   [:p "Hello, react-tutorial is running!"]
   [:p "Here's an example of using a component with state:"]
   [click-counter click-count]
   [board]])