(ns stupid-fx-app.spinal
    (:require [reagi.core :as r]))

(def spinal (r/events))
 
(defn push [event]
  (r/push! spinal event))