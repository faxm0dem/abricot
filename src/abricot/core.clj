(ns abricot.core
  (:require [mirabelle.action :as a]
            [mirabelle.io :as io]))

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
