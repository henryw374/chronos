(ns dev
  (:require [clojure.java.io :as io]
            [com.widdindustries.tiado-cljs2 :as util]
            [widdindustries.capture :as capt]
            [kaocha.repl :as kaocha]
            [babashka.fs :as fs]
            [clojure.tools.namespace.repl :as refresh]
            [com.widdindustries.gen.gen.tempo :as gen]))

(defn refresh []
  (refresh/refresh-all))

(defn run-clj-tests* []
  (kaocha/run 'com.widdindustries.chronos-test))

(defn run-clj-tests [_]
  (refresh/refresh-all :after 'dev/run-clj-tests*))

(defn browser-test-build [compile-mode opts]
  ((get util/compile-fns compile-mode)
   (util/browser-test-config) opts)
  (.mkdirs (io/file "web-target" "public" "browser-test"))
  ; https://esm.sh/temporal-polyfill@0.2.3
  (spit "web-target/public/browser-test/index.html"
    "<!DOCTYPE html>
    <html><head>
    <title>kaocha.cljs2.shadow-runner</title>
    <meta charset=\"utf-8\">
    </head>
    <body>
    <script src=\"/browser-test/js/test.js\"></script>
       
    <script>
    if(!window.Temporal){
      console.log('Temporal polyfill required');
      var head = document.getElementsByTagName('head')[0];
      var js = document.createElement(\"script\");
      js.type = \"text/javascript\";
      js.id = \"temporal-polyfill\"
      js.src = \"https://cdn.jsdelivr.net/npm/@js-temporal/polyfill@0.5.0/dist/index.umd.js\"
      head.appendChild(js);
      js.addEventListener('load',function(){
        console.log('loaded' + window.temporal)
        window.Temporal = window.temporal.Temporal;
          window.Intl = window.temporal.Intl;
          Date.prototype.toTemporalInstant = window.temporal.toTemporalInstant;
          com.widdindustries.chronos_test.initialise();
      });
      }
      else {
        com.widdindustries.chronos_test.initialise();
        console.log('Temporal polyfill not required');
      }
</script>
   
    
    
    <script>kaocha.cljs2.shadow_runner.init();</script></body></html>"
    ))

(defn test-watch []
  (browser-test-build :watch {}))

(defn tests-ci-shadow [{:keys [compile-mode]}]
  (util/start-server)
  (browser-test-build compile-mode {})
  (try
    (util/kaocha-exit-if-fail (util/run-tests-headless nil))
    (catch Exception e
      (println e)
      (System/exit 1))))

(comment
  (util/stop-server)
  ; start up live-compilation of tests
  (test-watch)
  
  (refresh/clear)
  
  (capt/capt
    '(do
      (require '[com.widdindustries.gen.gen.tempo] :reload-all)
      (require '[com.widdindustries.gen.gen.accessors] :reload-all)
      (gen/gen-after)))
  
  (capt/exec)
  (gen/generate-all nil)
  (run-clj-tests nil)
  ; start a cljs repl session in the test build. :cljs/quit to exit
  (util/repl :browser-test-build)
  :cljs/quit
  ; show what npm libs needed by cljs libs
  (util/show-npm-deps)
  
 
  ; you can stop/start etc as required
  (util/stop-server)
  (util/clean-build)
  )

(defn app-config 
  "investigate dead code elimination with a pretend client ns that uses chronos"
  []
  (->
    (util/browser-app-config)
    (merge
      {:modules          {:main {:entries ['noodle.client-app]}}})))

(defn app-release []
  (util/prod-build
    (-> (app-config)
        (assoc :compiler-options {:pretty-print true
                                  :pseudo-names true})
        (dissoc :devtools))))

(comment

  ; do the release build
  (widdindustries.capture/capt
    '(do (app-release)
         (-> (fs/file "web-target/public/cljs-out/main.js")
                        (.length)
             (- 518559))))

  (util/build-report (app-config) "build-report.html")

  )