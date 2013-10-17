(ns stupid-fx-app.run-now)

(defonce force-toolkit-init (javafx.embed.swing.JFXPanel.))
 
(defn run-later*
[f]
(javafx.application.Platform/runLater f))
 
(defmacro run-later
[& body]
`(run-later* (fn [] ~@body)))
 
(defn run-now*
[f]
(let [result (promise)]
(run-later
(deliver result (try (f) (catch Throwable e e))))
@result))
 
(defmacro run-now
[& body]
`(run-now* (fn [] ~@body)))