(ns abricot.core
  (:require [mirabelle.action :as a]
            [mirabelle.io :as io]
            [com.stuartsierra.component :as component]
            [riemann.client :refer [tcp-client send-events]]))

(defn keep-if-greater-than*
  [context threshold & children]
  (fn [event]
    (when (> (:metric event) threshold)
      (a/call-rescue event children))))

(defrecord CustomFileOutput [registry path]
  io/Output
  (inject! [this events]
    (doseq [event events]
      (spit path (str (pr-str event) "\n") :append true))))

(defrecord RiemannForward [registry config
                           client]
  component/Lifecycle
  (start [this]
    (if client
      this
      (let [c (riemann.client/tcp-client config)]
        (assoc this
               :config config
               :client c))))
  (stop [this]
    (.close client)
    (assoc this :client nil))
  io/Output
  (inject! [this events]
    @(riemann.client/send-event client events)))
