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
  (= :o ( calcurate-winner [:o nil nil nil :o nil nil nil :o])) 
  (nil? (calcurate-winner [nil nil nil nil :o nil nil nil :o])))

(defn board [& {:keys [x-is-next? squares on-play]}]
  (let [handle-click (fn [i]
                       (when-not (or (squares i)
                                     (calcurate-winner squares))
                         (let [next-squares (assoc squares i (if x-is-next? "X" "O"))]
                           (on-play next-squares))))
        winner (calcurate-winner squares)
        status (if winner
                 (str "Winner: " winner)
                 (str "Next player: " (if x-is-next? "X" "O")))]
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
      [square :value (squares 8) :on-click #(handle-click 8)]]]))

(defn game []
  (let [state (r/atom {:history [(vec (repeat 9 nil))]
                       :x-is-next? true
                       :current-move 0})]
    (fn []
       (let [{:keys [history x-is-next? current-move]} @state
             current-squares (history current-move)
             handle-play (fn [next-squares]
                           (let [next-history  (conj (->> history
                                                          (take (inc current-move))
                                                          vec)
                                                     next-squares)]
                             (swap! state assoc :history next-history)
                             (swap! state assoc :current-move (-> next-history count dec)))
                           (swap! state update :x-is-next? not))
             jump-to (fn [next-move]
                       (swap! state assoc :current-move next-move)
                       (swap! state assoc :x-is-next? (zero? (mod next-move 2))))
             moves (->> history
                        (map-indexed (fn [idx itm]
                                       [:li {:key idx}
                                        [:button {:on-click #(jump-to idx)} "go to move #"  idx]])))] 
         [:div.game 
          [:div.game-board
           [board 
            :x-is-next? x-is-next?
            :squares current-squares
            :on-play handle-play]
           [:div.game-info
            [:ol moves]
            [:div "status"]
            [:ol "todo"]]]]))))
