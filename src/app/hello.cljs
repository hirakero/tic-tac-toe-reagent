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
  (let [next-player (if x-is-next? "X" "O")
        handle-click (fn [i]
                       (when-not (or (squares i)
                                     (calcurate-winner squares))
                         (let [next-squares (assoc squares i next-player)]
                           (on-play next-squares))))
        winner (calcurate-winner squares)
        status (if winner
                 (str "Winner: " winner)
                 (str "Next player: " next-player))
        render-square (fn[i] 
                      [square :value (squares i) :on-click #(handle-click i)])]
    [:<>
     [:div.status status]
     [:div.board-row
      (render-square 0)
      (render-square 1)
      (render-square 2)]
     [:div.board-row
      (render-square 3)
      (render-square 4)
      (render-square 5)]
     [:div.board-row
      (render-square 6)
      (render-square 7)
      (render-square 8)]]))

(defn game []
  (let [state (r/atom {:history [(vec (repeat 9 nil))]
                       :current-move 0})]
    (fn []
       (let [{:keys [history current-move]} @state
             x-is-next?  (even? current-move)
             current-squares (history current-move)
             handle-play (fn [next-squares]
                           (let [next-history  (conj (->> history
                                                          (take (inc current-move))
                                                          vec)
                                                     next-squares)]
                             (swap! state assoc :history next-history)
                             (swap! state assoc :current-move (-> next-history count dec))))
             jump-to (fn [next-move]
                       (swap! state assoc :current-move next-move))
             moves (->> history
                        (map-indexed (fn [idx _]
                                       [:li {:key idx}
                                        [:button {:on-click #(jump-to idx)} "go to move #"  idx]])))] 
         [:div.game 
          [:div.game-board
           [board 
            :x-is-next? x-is-next?
            :squares current-squares
            :on-play handle-play]
           [:div.game-info 
            [:ol moves]]]]))))
