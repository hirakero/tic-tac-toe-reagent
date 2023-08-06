(ns app.hello
  (:require [reagent.core :as r]))

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
            (#(squares (first %))))))
(comment 
  (calcurate-winner [:o nil nil nil :o nil nil nil :o]) 
  (calcurate-winner [nil nil nil nil :o nil nil nil :o]))

(defn board [& {:keys [state]}]
  (fn []
    (let [{:keys [squares x-is-next?]} @state
          handle-click (fn [i]
                         (when-not (or (squares i)
                                       (calcurate-winner squares))
                           (swap! state assoc-in [:squares i] (if x-is-next? "x" "o"))
                           (swap! state update :x-is-next? not)))
          winner (calcurate-winner squares)
          status (if winner
                   (str "Winner:" winner)
                   (str "Next player:" (if x-is-next? "x" "o")))]
      [:<>
       [:div.status status]
       [:div.board-row
        [square :value (squares 0) :on-click #(handle-click 0)]
        [square :value (squares 1) :on-click #(handle-click 1)]
        [square :value (squares 2) :on-click #(handle-click 2)]]
       [:div.board-row
        [square :value (squares 3) :on-click #(handle-click 3)]
        [square :value (squares 4) :on-click #(handle-click 4)]
        [square :value (squares 5) :on-click #(handle-click 5)]]
       [:div.board-row
        [square :value (squares 6) :on-click #(handle-click 6)]
        [square :value (squares 7) :on-click #(handle-click 7)]
        [square :value (squares 8) :on-click #(handle-click 8)]]])))

(defn game []
  (let [state (r/atom {:squares (vec (repeat 9 nil))
                       :x-is-next? true})]
    [:div.game 
     [:div.game-board
      [board :state state]
      [:div.game-info
       [:div "status"]
       [:ol "todo"]]]]))

(defn hello []
  [:<>
   [game]])