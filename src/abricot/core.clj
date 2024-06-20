(ns abricot.core
  (:require [mirabelle.action :as a]
            [mirabelle.io :as io]
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

(defrecord RiemannForward [registry host port]
  io/Output
  (inject! [this events]
    (let [client (riemann.client/tcp-client :host host :port port)]
      @(riemann.client/send-event client events))))
