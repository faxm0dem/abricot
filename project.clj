(defproject abricot "0.1.0"
  :description "Mirabelle plugin to send events to riemann"
  :url "https://github.com/faxm0dem/abricot"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[fr.mcorbin/mirabelle "0.13.0"]]
  :plugins [[lein-localrepo "0.5.4"]]
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all
                       :jvm-opts ["-Dclojure.compiler.direct-linking=true"]}})
