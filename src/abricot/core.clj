(ns abricot.core
  (:import (io.riemann.riemann.client RiemannClient))
  (:require [mirabelle.io :as io]
            [com.stuartsierra.component :as component]
            [riemann.client :refer [tcp-client send-event send-events]]))

(defrecord RiemannForward [config
                           ^RiemannClient client]
  component/Lifecycle
  (start [this]
    (if client
      this
      (let [^RiemannClient c (riemann.client/tcp-client config)]
        (assoc this
               :config config
               :client c))))
  (stop [this]
    (when client
      (.close client))
    (assoc this :client nil))
  io/Output
  (inject! [this events]
    (do
    (if (map? events)
      (deref (riemann.client/send-event client events))
      (deref (riemann.client/send-events client events))))))
