(ns flappy.core
  (:require [quil.core :as q]
            [quil.middleware :as m]
            [clojure.java.io :as io]))

(defn setup []
  (let [background "./resources/PNG/background.png"
        plane "./resources/PNG/Planes/planeGreen1.png"
        ground "./resources/PNG/groundDirt.png"
        ceiling "./resources/PNG/ceilingDirt.png"
        rock "./resources/PNG/rock.png"
        rockDown "./resources/PNG/rockDown.png"]
    (q/set-state!
      :image (q/load-image background)
      :rock (q/load-image rock)
      :rockDown (q/load-image rockDown)
      :ground (q/load-image ground)
      :ceiling (q/load-image ceiling)
      :plane {:x 0
              :y 240
              :image (q/load-image plane)})))

(defn update-state [state]
  (if (q/key-pressed?)
    (let [key (q/raw-key)]
      (case key
        \u (update-in state [:plane :y] dec)
        \d (update-in state [:plane :y] inc)
        state))
    state))

(defn draw [state]
  (q/background 255)
  (let [img (q/state :image)
        plane (q/state :plane)
        ground (q/state :ground)
        ceiling (q/state :ceiling)
        rock (q/state :rock)
        rockDown (q/state :rockDown)]
    (when (q/loaded? img)
      (q/image img 0 0))
    (when (q/loaded? (:image plane))
      (q/image (:image plane) (:x plane) (:y plane)))
    (when (and (q/loaded? rock) (q/loaded? rockDown))
      (q/image rock 600 (- 480 239))
      (q/image rockDown 300 0))
    (when (and (q/loaded? ground) (q/loaded? ceiling))
      (q/image ground 0 409)
      (q/image ceiling 0 0))))

(q/defsketch example
             :title "Flappy"
             :setup setup
             :update update-state
             :draw draw
             :size [800 480]
             :middleware [m/fun-mode])