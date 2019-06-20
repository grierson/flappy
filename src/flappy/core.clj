(ns flappy.core
  (:require [quil.core :as q]
            [quil.middleware :as m]
            [clojure.java.io :as io]))

(defn setup []
  (q/frame-rate 1)                                          ;; Set framerate to 1 FPS
  (let [image (q/load-image "/src/flappy/data/PNG/background.png")] ;; Set the background colour to
    (Thread/sleep 1000)
    (q/background-image image))
  {:plane (q/load-image "/src/flappy/data/PNG/Planes/planeBlue1.png")
   :x     0
   :y     0})

(defn update-state [state]
  (let [key (q/raw-key)]
    (prn key)
    (condp = key
      \u (update state :y inc)
      \d (update state :y dec)
      state)))


;; a nice shade of grey.
(defn draw [state]
  (q/image (:plane state) 100 (:y state)))

(q/defsketch example                  ;; Define a new sketch named example
             :title "Oh so many grey circles"    ;; Set the title of the sketch
             :settings #(q/smooth 2)             ;; Turn on anti-aliasing
             :setup setup                                   ;; Specify the setup fn
             :update update-state
             :draw draw                                     ;; Specify the draw fn
             :size [800 480]
             :middleware [m/fun-mode])                      ;; You struggle to beat the golden ratio