(ns com.widdindustries.gen.gen-in.tempo)

(ns com.widdindustries.chronos
  (:refer-clojure :exclude [min max > < >= <= >> <<])
  #?(:cljay
     (:import
       [java.time Clock MonthDay ZoneId ZoneOffset Instant Duration Period DayOfWeek Month ZonedDateTime LocalTime LocalDateTime LocalDate Year YearMonth ZoneId OffsetDateTime OffsetTime]
       [java.time.temporal Temporal TemporalAmount TemporalUnit TemporalAccessor ChronoUnit ChronoField ValueRange]
       [java.util Date])
     :cljs (:require [com.widdindustries.chronos.temporal-comparison :as temporal-comparison]
             [com.widdindustries.chronos.chronos-clock :as chronos-clock]
             [goog.object])))

#?(:cljay (set! *warn-on-reflection* true))

#?(:cljay
   (defprotocol HasTime
     (getFractional [_])
     (setFractional [_ _])))

#?(:cljay (defn -millisecond [f]
            (-> (getFractional f) (Duration/ofNanos) (.toMillisPart) long)))
#?(:cljay (defn -microsecond [f]
            (-> (getFractional f) (/ 1000) long (mod 1000))))
#?(:cljay (defn -nanosecond [f]
            (-> (getFractional f) (mod 1000))))

(comment "after-graph")

(defn enable-comparison-for-all-temporal-entities
  "in cljs envs, this makes `=`, `compare` and `hash` work on the value of Temporal entities.
  It is optional, so that if this behaviour is not required, the resulting build size can be reduced. 
  "
  []
  #?(:cljs
     (temporal-comparison/enable-for-all)))

(defn legacydate? [v]
  #?(:cljs (instance? js/Date v)
     :cljay (instance? java.util.Date v)
     ))

(defn instant? [v] #?(:cljay (instance? Instant v)
                      :cljs (instance? js/Temporal.Instant v)))
(defn date? [v] #?(:cljay (instance? LocalDate v)
                   :cljs (instance? js/Temporal.PlainDate v)))
(defn datetime? [v] #?(:cljay (instance? LocalDateTime v)
                       :cljs (instance? js/Temporal.PlainDateTime v)))
(defn time? [v] #?(:cljay (instance? LocalTime v)
                   :cljs (instance? js/Temporal.PlainTime v)))
(defn monthday? [v] #?(:cljay (instance? MonthDay v)
                       :cljs (instance? js/Temporal.PlainMonthDay v)))
(defn yearmonth? [v] #?(:cljay (instance? YearMonth v)
                        :cljs (instance? js/Temporal.PlainYearMonth v)))
(defn zdt? [v] #?(:cljay (instance? ZonedDateTime v)
                  :cljs (instance? js/Temporal.ZonedDateTime v)))

#?(:cljay
   (defn- java-time-clock 
     "returns a partial implementation of java.time.Clock sufficient for all the java.time 'deref' methods"
     [instant-fn timezone-fn]
     (proxy [java.time.Clock] []
       (getZone [] (java.time.ZoneId/of (timezone-fn)))
       (instant [] (instant-fn)))))

;; construction of clocks
(defn clock-system-default-zone
  "a ticking clock having the ambient zone. "
  []
  #?(:cljay (Clock/systemDefaultZone)
     :cljs js/Temporal.Now))

(defn clock-fixed 
  "create a stopped clock"
  ([^ZonedDateTime zdt]
   #?(:cljay (Clock/fixed (.toInstant zdt)   (.getZone zdt))
      :cljs (chronos-clock/clock (constantly (.toInstant zdt)) (constantly (.-timeZoneId zdt)))))
  ([^Instant instant ^String zone-str]
   #?(:cljay (Clock/fixed instant (ZoneId/of zone-str))
      :cljs (chronos-clock/clock (constantly instant) (constantly zone-str)))))

(defn clock-with-timezone 
  "ticking clock in given timezone" 
  [^String timezone]
  #?(:cljay (Clock/system (ZoneId/of timezone))
     :cljs (chronos-clock/clock js/Temporal.Now.instant (constantly timezone))))

(defn clock-offset-millis 
  "offset an existing clock by offset-millis"
  [a-clock offset-millis]
  #?(:cljay (Clock/offset a-clock (Duration/ofMillis offset-millis))
     :cljs (chronos-clock/clock
             (fn [] (.add (.instant ^js a-clock) (js-obj "milliseconds" offset-millis)))
             (constantly (chronos-clock/timezone a-clock)))))

(defn clock [instant-fn timezone-fn]
  #?(:cljay (java-time-clock instant-fn timezone-fn)
     :cljs (chronos-clock/clock instant-fn timezone-fn)))

(defn clock-zdt-atom
  "create a clock which will dereference the zdt-atom.
  
  The caller must first construct the atom and by keeping a reference to it,
   may change its value and therefore the value of the clock.
  "
  [zdt-atom]
  (clock
    (fn get-instant []
      (zdt->instant @zdt-atom))
    (fn get-zone []
      (zdt->timezone @zdt-atom))))

(defn timezone-deref
  ([clock] #?(:cljay (str (.getZone ^Clock clock))
              :cljs (chronos-clock/timezone clock))))

(defn legacydate->instant [d]
  #?(:cljay (.toInstant ^java.util.Date d)
     :cljs (.toTemporalInstant ^js d)))

(defn instant->zdt-in-UTC [instant]
  #?(:cljay (ZonedDateTime/ofInstant instant (ZoneId/of "UTC"))
     :cljs (.toZonedDateTimeISO ^js instant "UTC")))

(defn- greater [x y]
  (if (neg? (compare x y)) y x))

(defn max
  "Find the latest of the given arguments. Callers should ensure that no
  argument is nil."
  [arg & args]
  (assert (every? some? (cons arg args)))
  (reduce #(greater %1 %2) arg args))

(defn- lesser [x y]
  (if (neg? (compare x y)) x y))

(defn min
  "Find the earliest of the given arguments. Callers should ensure that no
  argument is nil."
  [arg & args]
  (assert (every? some? (cons arg args)))
  (reduce #(lesser %1 %2) arg args))

(defn <
  ([_x] true)
  ([x y] (neg? (compare x y)))
  ([x y & more] (if (< x y)
                  (if (next more)
                    (recur y (first more) (next more))
                    (< y (first more)))
                  false)))

(defn <=
  ([_x] true)
  ([x y] (not (pos? (compare x y))))
  ([x y & more] (if (<= x y)
                  (if (next more)
                    (recur y (first more) (next more))
                    (<= y (first more)))
                  false)))

(defn >
  ([_x] true)
  ([x y] (pos? (compare x y)))
  ([x y & more] (if (> x y)
                  (if (next more)
                    (recur y (first more) (next more))
                    (> y (first more)))
                  false)))

(defn >=
  ([_x] true)
  ([x y] (not (neg? (compare x y))))
  ([x y & more] (if (>= x y)
                  (if (next more)
                    (recur y (first more) (next more))
                    (>= y (first more)))
                  false)))

(defn coincident? [start end event]
  (and (<= start event)
    (>= end event)))

#?(:cljay (defprotocol Property
            (unit [_])
            (field [_])))

#?(:cljay
   (extend-protocol HasTime
     ZonedDateTime
     (getFractional [x] (.getNano (.toLocalTime x)))
     (setFractional [x t] (.withNano x t))
     LocalDateTime
     (getFractional [x] (.getNano (.toLocalTime x)))
     (setFractional [x t] (.withNano x t))
     LocalTime
     (getFractional [x] (.getNano x))
     (setFractional [x t] (.withNano x t))
     Instant
     (getFractional [x] (.getNano x))
     (setFractional [x ^long t] (.with x ChronoField/NANO_OF_SECOND t))
     ))

#?(:cljay (def ^ValueRange sub-second-range (ValueRange/of 0 999)))

(def nanoseconds-property
  #?(:cljay (reify
              Object (toString [_] "nanoseconds-property")
              Property
              (unit [_] ChronoUnit/NANOS)
              (field [_] (reify java.time.temporal.TemporalField
                           (rangeRefinedBy [_ _temporal] sub-second-range)
                           (getFrom [_ temporal] (-nanosecond temporal))
                           (adjustInto [_ temporal value]
                             (.checkValidValue sub-second-range value nil)
                             (let [fractional (getFractional temporal)
                                   millis+micros (-> fractional (/ 1000) long (* 1000))
                                   new-fractional (+ millis+micros value)]
                               (-> temporal (setFractional new-fractional)))))))
     :cljs "nanosecond"))


(def microseconds-property #?(:cljay (reify
                                       Object (toString [_] "microseconds-property")
                                       Property (unit [_] ChronoUnit/MICROS)
                                       (field [_] (reify java.time.temporal.TemporalField
                                                    (rangeRefinedBy [_ _temporal] sub-second-range)
                                                    (getFrom [_ temporal] (-microsecond temporal))
                                                    (adjustInto [_ temporal value]
                                                      (.checkValidValue sub-second-range value nil)
                                                      (let [fractional (getFractional temporal)
                                                            millis (-> fractional (/ 1000000) long (* 1000000))
                                                            nanos (-> fractional (mod 1000))
                                                            new-fractional (+ millis nanos (-> value (* 1000)))]
                                                        (-> temporal (setFractional new-fractional)))))))
                              :cljs "microsecond"))

#_(defmethod print-method (type microseconds-property)
    [_, ^java.io.Writer w]
    (print-simple "microseconds-property" w))

(def milliseconds-property #?(:cljay (reify
                                       Object (toString [_] "milliseconds-property")
                                       Property (unit [_] ChronoUnit/MILLIS)
                                       (field [_] (reify java.time.temporal.TemporalField
                                                    (rangeRefinedBy [_ _temporal] sub-second-range)
                                                    (getFrom [_ temporal] (-millisecond temporal))
                                                    (adjustInto [_ temporal value]
                                                      (.checkValidValue sub-second-range value nil)
                                                      (let [fractional (getFractional temporal)
                                                            micros+nanos (-> fractional (mod 1000000))]
                                                        (-> temporal (setFractional (+ micros+nanos (-> value (* 1000000))))))))))
                              :cljs "millisecond"))

#_(defmethod print-method (type milliseconds-property)
    [_, ^java.io.Writer w]
    (print-simple "milliseconds-property" w))

(def seconds-property #?(:cljay (reify
                                  Object (toString [_] "seconds-property")
                                  Property (unit [_] ChronoUnit/SECONDS)
                                  (field [_] ChronoField/SECOND_OF_MINUTE))
                         :cljs "second"))

#_(defmethod print-method (type seconds-property)
    [_, ^java.io.Writer w]
    (print-simple "seconds-property" w))

(def minutes-property #?(:cljay (reify
                                  Object (toString [_] "minutes-property")
                                  Property (unit [_] ChronoUnit/MINUTES)
                                  (field [_] ChronoField/MINUTE_OF_HOUR))
                         :cljs "minute"))

#_(defmethod print-method (type minutes-property)
    [_, ^java.io.Writer w]
    (print-simple "minutes-property" w))

(def hours-property #?(:cljay (reify
                                Object (toString [_] "hours-property")
                                Property (unit [_] ChronoUnit/HOURS)
                                (field [_] ChronoField/HOUR_OF_DAY))
                       :cljs "hour"))

#_(defmethod print-method (type hours-property)
    [_, ^java.io.Writer w]
    (print-simple "hours-property" w))

(def days-property #?(:cljay (reify
                               Object (toString [_] "days-property")
                               Property (unit [_] ChronoUnit/DAYS)
                               (field [_] ChronoField/DAY_OF_MONTH))
                      :cljs "day"))

#_(defmethod print-method (type days-property)
    [_, ^java.io.Writer w]
    (print-simple "days-property" w))

(def months-property #?(:cljay (reify
                                 Object (toString [_] "months-property")
                                 Property (unit [_] ChronoUnit/MONTHS)
                                 (field [_] ChronoField/MONTH_OF_YEAR))
                        :cljs "month"))

#_(defmethod print-method (type months-property)
    [_, ^java.io.Writer w]
    (print-simple "months-property" w))

(def years-property #?(:cljay (reify
                                Object (toString [_] "years-property")
                                Property (unit [_] ChronoUnit/YEARS)
                                (field [_] ChronoField/YEAR))
                       :cljs "year"))

#_(defmethod print-method (type years-property)
    [_, ^java.io.Writer w]
    (print-simple "years-property" w))

(def ^:dynamic *block-non-commutative-operations* true)

(defn throw-if-set-months-or-years [temporal temporal-property]
  (when
    (and *block-non-commutative-operations* 
      (contains? #{years-property months-property} temporal-property)
         (not (or (monthday? temporal) (yearmonth? temporal))))
    (throw (ex-info
             "see guardrails section at https://github.com/henryw374/chronos?tab=readme-ov-file#guardrails"
             {}))))

(defn throw-if-months-or-years-in-amount [temporal temporal-amount]
  (when
    (and *block-non-commutative-operations*
      (not (or (monthday? temporal) (yearmonth? temporal)))
      #?(:cljay (and (instance? Period temporal-amount)
                  (or
                    (not (zero? (.getYears ^Period temporal-amount)))
                    (not (zero? (.getMonths ^Period temporal-amount)))))
         :cljs (or
                 (not (zero? (.-years ^js temporal-amount)))
                 (not (zero? (.-months ^js temporal-amount))))))
    (throw (ex-info
             "see guardrails section at https://github.com/henryw374/chronos?tab=readme-ov-file#guardrails"
             {}))))

(defn with 
  "from temporal arg, derive a new temporal object with property field set to value
  (t/with date 3 t/days-property) "
  [temporal value property]
  (throw-if-set-months-or-years temporal property)
  #?(:cljay (.with ^Temporal temporal ^TemporalField (field property) ^long value)
     :cljs (.with ^js temporal (js-obj property value) (js-obj "overflow" "reject"))))

(defn until [v1 v2 property]
  #?(:cljay (.until ^Temporal v1 v2 (unit property))
     ;https://tc39.es/proposal-temporal/docs/instant.html#until
     :cljs 
     (-> (.until ^js v1 ^js v2 (js-obj "smallestUnit" property
                                 "largestUnit" property))
         (goog.object/get (str property "s")))))

(defn >>
  "move a temporal forward by an amount"
  ([temporal temporal-amount]
     (throw-if-months-or-years-in-amount temporal temporal-amount)
     #?(:cljay (.plus ^Temporal temporal ^TemporalAmount temporal-amount)
        :cljs (.add ^js temporal temporal-amount)))
  ([temporal amount temporal-property]
   (throw-if-set-months-or-years temporal temporal-property)
   #?(:cljay (.plus ^Temporal temporal amount ^TemporalUnit (unit temporal-property))
      :cljs (.add ^js temporal (js-obj (str temporal-property "s") amount)))))

(defn <<
  "move a temporal backward by an amount"
  ([temporal temporal-amount]
     (throw-if-months-or-years-in-amount temporal temporal-amount)
     #?(:cljay (.minus ^Temporal temporal ^TemporalAmount temporal-amount)
        :cljs (.subtract ^js temporal temporal-amount)))
  ([temporal amount temporal-property]
   (throw-if-set-months-or-years temporal temporal-property)
   #?(:cljay (.minus ^Temporal temporal amount ^TemporalUnit (unit temporal-property))
      :cljs (.subtract ^js temporal (js-obj (str temporal-property "s") amount)))))

(def weekday-monday 1)
(def weekday-tuesday 2)
(def weekday-wednesday 3)
(def weekday-thursday 4)
(def weekday-friday 5)
(def weekday-saturday 6)
(def weekday-sunday 7)

(def weekday-monday-name "monday")
(def weekday-tuesday-name "tuesday")
(def weekday-wednesday-name "wednesday")
(def weekday-thursday-name "thursday")
(def weekday-friday-name "friday")
(def weekday-saturday-name "saturday")
(def weekday-sunday-name "sunday")

(def weekday->weekday-name
  {weekday-monday weekday-monday-name
   weekday-tuesday weekday-tuesday-name
   weekday-wednesday weekday-wednesday-name
   weekday-thursday weekday-thursday-name
   weekday-friday weekday-friday-name
   weekday-saturday weekday-saturday-name
   weekday-sunday weekday-sunday-name})

(def weekday-name->weekday 
  {weekday-monday-name weekday-monday     
   weekday-tuesday-name weekday-tuesday    
   weekday-wednesday-name weekday-wednesday  
   weekday-thursday-name weekday-thursday   
   weekday-friday-name weekday-friday     
   weekday-saturday-name weekday-saturday   
   weekday-sunday-name weekday-sunday       })

(def month-january 1)
(def month-february 2)
(def month-march 3)
(def month-april 4)
(def month-may 5)
(def month-june 6)
(def month-july 7)
(def month-august 8)
(def month-september 9)
(def month-october 10)
(def month-november 11)
(def month-december 12)

(def month-january-name "january") 
(def month-february-name "february") 
(def month-march-name "march") 
(def month-april-name "april") 
(def month-may-name "may") 
(def month-june-name "june") 
(def month-july-name "july") 
(def month-august-name "august") 
(def month-september-name "september") 
(def month-october-name "october")  
(def month-november-name "november")  
(def month-december-name "december")  

#?(:clj (defprotocol JavaTruncateable
          (-truncate [_ unit])))

#?(:cljay
   (extend-protocol JavaTruncateable
     ZonedDateTime (-truncate [zdt unit] (.truncatedTo zdt unit))
     LocalDateTime (-truncate [zdt unit] (.truncatedTo zdt unit))
     LocalTime (-truncate [zdt unit] (.truncatedTo zdt unit))
     Instant (-truncate [zdt unit] (.truncatedTo zdt unit))))

(defn truncate 
  "zero property field (and smaller fields) of temporal"
  [temporal property]
  #?(:cljay (-truncate temporal (unit property))
     :cljs (.round ^js temporal 
             (js-obj "smallestUnit" property "roundingMode" "trunc"))))

(defn get-field [temporal property]
  #?(:cljay (.get ^TemporalAccessor temporal (field property))
     :cljs (goog.object/get temporal property)))

(defn yearmonth+day-at-end-of-month 
  "create a date having last day of month" 
  [ym]
  #?(:cljay (.atEndOfMonth ^YearMonth ym)
     :cljs (.toPlainDate ^js ym (js-obj "day" (.-daysInMonth ^js ym)))))

(defn monthday+year 
  "create a date"
  [monthday year]
  #?(:cljay (.atYear ^MonthDay monthday ^int year)
     :cljs (.toPlainDate ^js monthday (js-obj "year" year))))

(defn yearmonth+day-of-month
  "create a date"
  [yearmonth day]
  #?(:cljay (.atDay ^YearMonth yearmonth ^int day)
     :cljs (.toPlainDate ^js yearmonth (js-obj "day" day))))

(defn date+time
  "create a datetime"
  [date time]
  #?(:cljay (.atTime ^LocalDate date ^LocalTime time)
     :cljs (.toPlainDateTime ^js date time)))

(defn time+date
  "create a datetime" [time date]
  (date+time date time))

(defn datetime+timezone
  "create a zdt"
  [datetime timezone]
  #?(:cljay (.atZone ^LocalDateTime datetime (ZoneId/of timezone))
     :cljs (.toZonedDateTime ^js datetime timezone)))

(defn instant+timezone
  "create a zdt" [instant timezone]
  #?(:cljay (.atZone ^Instant instant (ZoneId/of timezone))
     :cljs (.toZonedDateTimeISO ^js instant timezone)))

(defn epochmilli->instant [milli]
  #?(:cljay (Instant/ofEpochMilli milli)
     :cljs (js/Temporal.Instant.fromEpochMilliseconds milli)))

(defn date-next-or-same-weekday [date desired-dow-number]
  (let [curr-day-of-week (date->day-of-week date)]
    (>> date
      (-> (abs (- curr-day-of-week (+ 7 desired-dow-number)))
          (mod 7))
      days-property)))

(defn date-prev-or-same-weekday [date desired-dow-number]
  (let [curr-day-of-week (date->day-of-week date)]
    (<< date
      (-> (abs (- (+ 7 curr-day-of-week) desired-dow-number))
          (mod 7))
      days-property)))