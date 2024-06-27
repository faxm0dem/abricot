(ns abricot.core
  (:import (io.riemann.riemann.client RiemannClient))
  (:require [mirabelle.io :as io]
            [com.stuartsierra.component :as component]
            [riemann.client :refer [tcp-client send-event send-events]]))

(defrecord RiemannForward [client
                           ^RiemannClient riemann-client]
  component/Lifecycle
  (start [this]
    (if riemann-client
      this
      (let [^RiemannClient c (riemann.client/tcp-client client)]
        (assoc this
               :client client
               :riemann-client c))))
  (stop [this]
    (when riemann-client
      (.close riemann-client))
    (assoc this :riemann-client nil))
  io/Output
  (inject! [this events]
    (do
    (if (map? events)
      (deref (riemann.client/send-event riemann-client events))
      (deref (riemann.client/send-events riemann-client events))))))
