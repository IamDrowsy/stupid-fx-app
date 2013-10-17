(ns stupid-fx-app.core
  (:use stupid-fx-app.run-now
        stupid-fx-app.spinal)
  (:import [javafx.fxml FXMLLoader]
           [javafx.stage StageBuilder]
           [javafx.scene Scene]
           [javafx.scene.paint Color])
  (:require [reagi.core :as r]))


(def stage (run-now
             (doto
               (.build (StageBuilder/create))
               (.setTitle "stupid-fx-app")
               (.setScene (Scene. (FXMLLoader/load (clojure.java.io/resource "stupid-app.fxml")))))))
(run-now (.show stage))

(def button-stream
  (r/filter #(= "rotateButton" (.getId (.getSource %))) spinal))
 
(def text-stream
  (r/filter #(= "buttonText" (.getId (.getSource %))) spinal))

(defn rotate [node]
  (.setRotate node (+ (.getRotate node) 20)))

(r/map (fn [event]
         (-> (.getSource event)
           (.getScene)
           (.lookup "#buttonText")
           rotate))
       button-stream)

(defn change-text-color [node text]
  (try (.setTextFill node (Color/valueOf text))
    (catch IllegalArgumentException e)))
 
(r/map (fn [event]
         (let [text (.getText (.getSource event))]
           (-> (.getSource event)
             (.getScene)
             (.lookup "#rotateButton")
             (change-text-color text))))
       text-stream)





