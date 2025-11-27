(ns com.widdindustries.chronos-test
  (:require [clojure.test :refer [deftest is testing]]
            [com.widdindustries.chronos :as t]
            [com.widdindustries.chronos :as c]
            [time-literals.read-write])
  #?(:clj (:import [java.util Date])))


(defn ^:export initialise []
  (c/enable-comparison-for-all-temporal-entities)
  (time-literals.read-write/print-time-literals-clj!)
  (time-literals.read-write/print-time-literals-cljs!))

(comment
  (clojure.test/run-tests)
  (remove-ns (.name *ns*))
  (remove-ns 'com.widdindustries.chronos)
  (require '[com.widdindustries.chronos] :reload-all)
  (require '[time-literals.read-write])
  #time/date "2020-02-02"

  )

 

(deftest construction-from-parts-test ;

  (testing ""
    c/monthday-from
    c/yearmonth-from
    )
  ;todo
  (testing "level 0"
    (let [nanos 789
          micros 456
          millis 123]
      ;todo

      )
    )
  (testing "level 1"
    (testing "setting zone on zdt"
      (let [timezone "Pacific/Honolulu"
            zdt (c/zdt-deref (c/clock-with-timezone timezone))]
        (testing "roundtrip same instant"
          (is (= zdt (c/zdt-from
                       {:timezone timezone
                        :instant     (c/instant-from {:zdt zdt})}))))
        (testing "keep wall time, change zone"
          (let [zdt-2 (c/zdt-from {:zdt      zdt
                                   :timezone "Europe/London"})]
            (is (= (c/zdt->datetime zdt) (c/zdt->datetime zdt-2)))
            (is (not= (c/zdt->timezone zdt) (c/zdt->timezone zdt-2)))))))
    (let [datetime (c/datetime-deref (c/clock-system-default-zone))
          timezone (str (c/timezone-deref (c/clock-system-default-zone)))
          zdt (c/zdt-from {:datetime datetime :timezone timezone})]
      (is (c/zdt? zdt))
      (is (= datetime (c/zdt->datetime zdt)))
      (is (= timezone (c/zdt->timezone zdt)))))
  (testing "level 2"
    (let [date (c/date-deref (c/clock-system-default-zone))
          time (c/time-deref (c/clock-system-default-zone))
          timezone (str (c/timezone-deref (c/clock-system-default-zone)))
          zdt (c/zdt-from {:date date :time time :timezone timezone})]
      (is (c/zdt? zdt))
      (is (= time (c/zdt->time zdt)))
      (is (= date (c/zdt->date zdt)))
      (is (= timezone (c/zdt->timezone zdt))))
    )
  (testing "level 3"
    (let [ym (c/yearmonth-parse "2020-02")
          timezone  "Pacific/Honolulu"
          zdt (c/zdt-from {:yearmonth ym :day-of-month 1
                           :hour      1
                           :timezone  timezone})]
      (is (c/zdt? zdt))
      (is (= (c/yearmonth->year ym) (c/zdt->year zdt)))
      (is (= 1 (c/zdt->day-of-month zdt)))
      (is (= c/weekday-saturday-name (get c/weekday->weekday-name (c/zdt->day-of-week zdt))))
      (is (= 1 (c/zdt->hour zdt)))))
  (testing "level 4"
    (let [zdt (c/zdt-deref (c/clock-system-default-zone))]
      (is (c/instant? (c/instant-from {:zdt zdt})))
      (let [i (c/instant-parse "2024-01-16T12:43:44.196000Z")]
        (is (= i (c/instant-from {:epochmilli (c/instant->epochmilli i)}))))
      (let [d #?(:clj (Date.) :cljs (js/Date.))
            i (c/instant-from {:legacydate d})]
        (is (= (.getTime d) (c/instant->epochmilli i)))
        (is (= i (-> i (c/instant->legacydate) (c/legacydate->instant))))
        (is (= (.getTime d) (-> (.getTime d) (c/epochmilli->instant) (c/instant->epochmilli))))))
    (testing "zdt-instant"
      (let [i (c/instant-parse "2024-01-16T12:43:44.196000Z")]
        (is (= i (-> i (c/instant->zdt-in-UTC) (c/zdt->instant))))))
    (testing "zdt with offset"
      (is (= "+05:50"
            (->
              (c/zdt-from {:instant  (c/instant-deref (c/clock-system-default-zone))
                           :timezone "+05:50"})
              (c/zdt->timezone)))))))

#_(deftest parsing-duration
    (is (c/duration? (d/duration-parse "PT1S"))))

#_(deftest equals-hash-compare-duration
  (let [make-middle #(d/duration-parse "PT1S")
        middle (make-middle)
        smallest (d/duration-parse "PT0S")
        largest (d/duration-parse "PT2S")]
    (is (not= middle smallest))
    (is (= middle (make-middle)))
    (is (= (sort [largest smallest middle]) [smallest middle largest]))
    (is (= (hash middle) (hash (make-middle))))
    (is (not= (hash smallest) (hash (make-middle))))))

(deftest now-date
  (is (c/date? (c/date-deref (c/clock-system-default-zone))))
  ;todo - test zone that'll return different date to the sys default?
  (is (c/date? (c/date-deref (c/clock-system-default-zone))))
  (is (= "2020-02-02"
        (str (c/date-deref
               (c/clock-fixed (c/instant-parse "2020-02-02T09:19:42.128946Z") "UTC"))))))

(deftest equals-hash-compare-date
  (let [middle (c/date-deref (c/clock-system-default-zone))
        earliest (c/<< middle 1 c/days-property)
        latest (c/>> middle 1 c/days-property)]
    (is (not= middle earliest))
    (is (= middle (c/date-deref (c/clock-system-default-zone))))
    ;(compare earliest middle)
    (is (= (sort [latest earliest middle]) [earliest middle latest]))
    (is (= (hash middle) (hash (c/date-deref (c/clock-system-default-zone)))))
    (is (not= (hash earliest) (hash (c/date-deref (c/clock-system-default-zone)))))))

(deftest preds
  (is (c/date? (c/date-deref (c/clock-system-default-zone))))
  (is (not (c/zdt? (c/date-deref (c/clock-system-default-zone))))))

(deftest parsing-test
  (is (c/date? (c/date-parse "2020-02-02"))))

(deftest equals-hash
  (is (= (c/date-parse "2020-02-02") (c/date-parse "2020-02-02")))
  (is (= 1 (get {(c/date-parse "2020-02-02") 1} (c/date-parse "2020-02-02")))))

(deftest shift
  ;todo - generate for combinations of duration/period and entity
  (let [a-date (c/date-deref (c/clock-system-default-zone))
        ;period (d/period-parse "P3D")
        ]
    (is (= a-date (-> (c/>> a-date 3 c/days-property)
                      (c/<< 3 c/days-property))))
    #_(is (= a-date (-> (c/>> a-date period)
                        (c/<< period))))))

(deftest prop-test
  (let [combos [[c/instant-deref [c/nanoseconds-property c/microseconds-property c/milliseconds-property
                                ; not days - mistakenly assumed to be 24hr in java.time
                                ; ofc no props second or greater, as there is no calendar
                                ] 
                 ]
                [c/zdt-deref [c/nanoseconds-property c/microseconds-property c/milliseconds-property
                            c/seconds-property c/hours-property
                            c/days-property ;t/months-property t/years-property
                            ]]
                [c/datetime-deref [c/nanoseconds-property c/microseconds-property c/milliseconds-property
                                 c/seconds-property c/hours-property
                                 c/days-property ;t/months-property t/years-property
                                 ]]
                [c/date-deref [c/days-property ;t/months-property t/years-property
                             ]]
                [c/yearmonth-deref [c/months-property c/years-property]]
                ;[t/monthday-deref [t/months-property t/days-property]]
                ]]
    (doseq [[now props xtras] combos
            shiftable-prop (concat props xtras)]
      (let [i-1 (now (c/clock-system-default-zone))
            i-2 (-> i-1
                    (c/>> 1 shiftable-prop))]
        (testing (str "shift until" i-1 " by " shiftable-prop)
          (is (= 1 (c/until i-1 i-2 shiftable-prop)))
          (is (= -1 (c/until i-2 i-1 shiftable-prop))))))
    (doseq [[now props] combos
            withable-prop props]
      (let [i-1 (now (c/clock-system-default-zone))
            current (c/get-field i-1 withable-prop)]
        (when-not (c/instant? i-1)
          (testing (str "with " i-1 " prop " (str withable-prop) " current " current
                     " " withable-prop)
            (is (not= i-1 (c/with i-1 (if (= 1 current) 2 1) withable-prop)) (str i-1 " " withable-prop))))))))

(comment
  (-> (c/instant-deref (c/clock-system-default-zone))
      ;(c/-millisecond)
      (.get (c/field t/seconds-property))
      )

  (def x (c/instant-deref (c/clock-system-default-zone)))
  x
  (c/getFractional x)
  (c/get-field x t/days-property)
  )
;(t/get-field (t/zdt-deref (t/clock-system-default-zone)) t/days-property)

(deftest clock-test
  (let [zone (c/timezone-deref (c/clock-system-default-zone))
        now (c/instant-deref (c/clock-system-default-zone))
        fixed (c/clock-fixed now zone)
        offset (c/clock-offset-millis fixed 1)
        zdt-atom (atom (c/zdt-parse "2024-02-22T00:00:00Z[Europe/London]"))
        clock-zdt-atom #?(:bb nil :default (c/clock-zdt-atom zdt-atom))]
    (is (= now (c/instant-deref fixed)))
    (is (= (c/>> now 1 c/milliseconds-property) (c/instant-deref offset)))
    (is (c/>= (c/instant-deref (c/clock-system-default-zone)) (c/instant-deref fixed)))
    (is (= (c/zdt->timezone (c/zdt-deref fixed)) (c/zdt->timezone (c/zdt-deref offset))))
    #?@(:bb []
         :default [
                    (is (= @zdt-atom (c/zdt-deref clock-zdt-atom)))
                    (swap! zdt-atom c/>> 1 c/hours-property)
                    (is (= @zdt-atom (c/zdt-deref clock-zdt-atom)))])))

(comment
  (def now (c/instant-deref (c/clock-system-default-zone)))
  (def fixed (c/clock-fixed now "Europe/London"))
  
  )

(deftest adjust-test
  (testing "adjusting date"
    (is (= (c/date-parse "0001-01-01")
          (binding [c/*block-non-commutative-operations* false]
            (-> (c/date-deref (c/clock-system-default-zone))
                (c/with 1 c/days-property)
                (c/with 1 c/months-property)
                (c/with 1 c/years-property))))))
  #?(:clj ; Temporal Instant has no 'with' method
     (testing "adjusting instant"
       (let [i (-> (c/instant-deref (c/clock-system-default-zone))
                   (c/with 123 c/milliseconds-property)
                   (c/with 456 c/microseconds-property)
                   (c/with 789 c/nanoseconds-property))]
         (is (= 123 (c/get-field i c/milliseconds-property)))
         (is (= 456 (c/get-field i c/microseconds-property)))
         (is (= 789 (c/get-field i c/nanoseconds-property))))))
  (doseq [[x hour minute second milli micro nano] [[(c/zdt-parse "2024-02-22T00:00:00Z[Europe/London]")
                                                    c/zdt->hour
                                                    c/zdt->minute
                                                    c/zdt->second
                                                    c/zdt->millisecond
                                                    c/zdt->microsecond
                                                    c/zdt->nanosecond]
                                                   [(c/time-from {:hour 0})
                                                    c/time->hour
                                                    c/time->minute
                                                    c/time->second
                                                    c/time->millisecond
                                                    c/time->microsecond
                                                    c/time->nanosecond]
                                                   [
                                                    (-> (c/datetime-from {:year 1 :month c/month-january :day-of-month 1})
                                                        ;(t/with 10 t/hours-property)
                                                        )
                                                    c/datetime->hour
                                                    c/datetime->minute
                                                    c/datetime->second
                                                    c/datetime->millisecond
                                                    c/datetime->microsecond
                                                    c/datetime->nanosecond]]]
    (testing (str "adjusting time " x)
      (let [y (-> x
                  (c/with 10 c/hours-property)
                  (c/with 55 c/minutes-property)
                  (c/with 30 c/seconds-property)
                  (c/with 123 c/milliseconds-property)
                  (c/with 456 c/microseconds-property)
                  (c/with 789 c/nanoseconds-property))]
        (is (= 10 (hour y)))
        (is (= 55 (minute y)))
        (is (= 30 (second y)))
        (is (= 123 (milli y)))
        (is (= 456 (micro y)))
        (is (= 789 (nano y)))))
    (testing (str "range errors on sub-second fields " x)
      (doseq [prop [c/milliseconds-property
                    c/microseconds-property
                    c/nanoseconds-property]]
        (is (thrown? #?(:clj Throwable :cljs js/Error) (-> x (c/with 1000 prop))) (str x " " prop))
        (is (thrown? #?(:clj Throwable :cljs js/Error) (-> x (c/with -1 prop))))))))

(deftest round-trip-legacy
  (let [i (c/instant-parse "2020-02-02T00:00:00Z")]
    (is (= i
          (-> i (c/instant->legacydate) (c/legacydate->instant))))))

(deftest truncate-test
  (doseq [[temporal props] [[(c/zdt-parse "2020-02-02T09:19:42.123456789Z[Europe/London]") [c/days-property c/hours-property c/minutes-property
                                                                                            c/seconds-property c/milliseconds-property c/microseconds-property
                                                                                            c/nanoseconds-property]]
                            [(c/datetime-parse "2020-02-02T09:19:42.123456789") [c/days-property c/hours-property c/minutes-property
                                                                                 c/seconds-property c/milliseconds-property c/microseconds-property
                                                                                 c/nanoseconds-property]]
                            [(c/time-parse "09:19:42.123456789") [c/hours-property c/minutes-property
                                                                  c/seconds-property c/milliseconds-property c/microseconds-property
                                                                  c/nanoseconds-property]]]
          prop props]
    (is (= (-> (c/truncate temporal prop) (c/get-field prop))
          (c/get-field temporal prop))))
  (let [i (c/instant-parse "2020-02-02T09:19:42.123456789Z")]
    (is (-> (c/truncate i c/hours-property) ; fyi hours is biggest
            (c/instant+timezone "Europe/London")
            (c/zdt->minute)
            (zero?))))
  )

(deftest guardrails-test
  (is (thrown? #?(:clj Throwable :cljs js/Error) (c/>> (c/date-parse "2020-02-02") 1 c/years-property)))
  ;(is (thrown? #?(:clj Throwable :cljs js/Error) (t/>> (t/date-parse "2020-02-02") (d/period-parse "P1Y"))))
  (binding [c/*block-non-commutative-operations* false]
    (is (c/>> (c/date-parse "2020-02-02") 1 c/years-property))
    ;(is (t/>> (t/date-parse "2020-02-02") (d/period-parse "P1Y")))

    ))

(deftest comparison-test
  (doseq [{:keys [startf endf]} [
                                 {:startf #(c/instant-parse "2020-02-01T00:00:00Z") :endf #(c/instant-parse "2020-02-02T00:00:00Z")}
                                 {:startf #(c/zdt-parse "2020-02-01T00:00Z[Europe/London]") :endf #(c/zdt-parse "2020-02-02T00:00Z[Europe/London]")}
                                 {:startf #(c/datetime-parse "2020-02-01T00:00") :endf #(c/datetime-parse "2020-02-02T00:00")}
                                 {:startf #(c/date-parse "2020-02-01") :endf #(c/date-parse "2020-02-02")}
                                 {:startf #(c/yearmonth-parse "2020-02") :endf #(c/yearmonth-parse "2020-03")}
                                 {:startf (constantly (c/monthday-parse "--12-01")) :endf (constantly (c/monthday-parse "--12-02"))}]]
    (let [start (startf)
          end (endf)]
      (is (= end (c/max start end start end)))
      (is (= start (c/min start end start end)))

      (is (c/>= end end start))
      (is (not (c/>= start end end start)))

      (is (c/<= start end end))
      (is (not (c/<= end start end)))

      (is (c/> end start))
      (is (not (c/> start end)))

      (is (c/< start end))
      (is (not (c/< end start))))
    )

  )

(deftest eom-test
  (is (= (c/date-parse "2020-02-29") (c/yearmonth+day-at-end-of-month (c/yearmonth-parse "2020-02")))))

(deftest plus-test
  (let [clock (c/clock-system-default-zone)
        month-day (c/monthday-deref clock)
        year-month (c/yearmonth-deref clock)]
    (is (= month-day
          (-> month-day
              (c/monthday+year 2021)
              (c/date+time (c/time-deref clock))
              (c/datetime+timezone "Pacific/Honolulu")
              (c/zdt->monthday))))
    (is (= year-month
          (-> year-month
              (c/yearmonth+day-of-month 1)
              (c/date+time (c/time-deref clock))
              (c/datetime+timezone "Pacific/Honolulu")
              (c/zdt->yearmonth))))))

(deftest or-same-test
  (let [start (c/date-parse "2024-03-19")]
    (is (= start (c/date-next-or-same-weekday start 2)))
    (is (= (c/>> start 6 c/days-property) (c/date-next-or-same-weekday start 1)))
    (is (= start (c/date-prev-or-same-weekday start 2)))
    (is (= (c/<< start 1 c/days-property) (c/date-prev-or-same-weekday start 1)))))

;(remove-ns (.name *ns*))



