(defproject cvc-spec-presentation "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [org.clojure/test.check "0.10.0"]
                 [com.datomic/datomic-free "0.9.5697"]
                 [orchestra "2018.12.06-2"]]
  :main ^:skip-aot cvc-spec-presentation.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
