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

(defn calcurate-winner [squares]
  (let [lines [[0 1 2]
               [3 4 5]
               [6 7 8]
               [0 3 6]
               [1 4 7]
               [2 5 8]
               [0 4 8]
               [2 4 6]]] 
     (some-> (filter (fn [[a b c]]
                  (and (squares a)
                       (= (squares a)
                          (squares b)
                          (squares c))))
                lines)
             first
             (#(squares (first %)))) 
    )
  )
(comment 
  (calcurate-winner [:o nil nil nil :o nil nil nil :o]) 
  (calcurate-winner [nil nil nil nil :o nil nil nil :o]))

(defn board []
  (let [state (r/atom {:squares (vec (repeat 9 nil))
                       :x-is-next? true}) 
        handle-click (fn [i] 
                       (when-not (or (get-in @state [:squares i])
                                     (calcurate-winner (:squares @state)))
                         (swap! state assoc-in [:squares i] (if (get @state :x-is-next?) "x" "o"))
                         (swap! state update :x-is-next? not))
                       )]
    (fn [] 
      (let [winner (calcurate-winner (:squares @state))
            status (if winner
                     (str "Winner:" winner)
                     (str "Next player:" (if (:x-is-next? @state) "x" "o")))]
        [:<>
         [:div.status status ]
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
          [square :value (get-in @state [:squares 8]) :on-click #(handle-click 8)]]])))
  )

(defn hello []
  [:<>
   [:p "Hello, react-tutorial is running!"]
   [:p "Here's an example of using a component with state:"]
   [click-counter click-count]
   [board]])