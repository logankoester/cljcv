(defproject cljcv "0.1.0"
  :description "Produce a nicely formatted curriculum vitae or résumé in various formats."
  :url "https://github.com/logankoester/cljcv"
  :license {:name "MIT"
            :url "https://opensource.org/licenses/MIT"}
  :dependencies [
                 [org.clojure/clojure "1.9.0"]
                 [clj-pdf "2.3.1"]
                 [dali "0.7.5"]
                 [org.clojure/tools.cli "0.4.1"]
                 [me.raynes/fs "1.4.6"]
                 [hawk "0.2.11"]
                ]
  :plugins [[lein-auto "0.1.3"]]
            
  :main ^:skip-aot cljcv.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
