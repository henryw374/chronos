(ns
 com.widdindustries.chronos
 (:refer-clojure :exclude [min max > < >= <= >> <<])
 (:import
  [java.time
   Clock
   MonthDay
   ZoneId
   ZoneOffset
   Instant
   Duration
   Period
   DayOfWeek
   Month
   ZonedDateTime
   LocalTime
   LocalDateTime
   LocalDate
   Year
   YearMonth
   ZoneId
   OffsetDateTime
   OffsetTime]
  [java.time.temporal
   Temporal
   TemporalAmount
   TemporalUnit
   TemporalAccessor
   ChronoUnit
   ChronoField
   ValueRange]
  [java.util Date]))

(set! *warn-on-reflection* true)

(defprotocol HasTime (getFractional [_]) (setFractional [_ _]))

(defn
 -millisecond
 [f]
 (-> (getFractional f) (Duration/ofNanos) (.toMillisPart) long))

(defn -microsecond [f] (-> (getFractional f) (/ 1000) long (mod 1000)))

(defn -nanosecond [f] (-> (getFractional f) (mod 1000)))

^{:line 30, :column 11} (comment "accessors")

(defn zdt->year [^ZonedDateTime foo] (-> foo .getYear))

(defn datetime->month [^LocalDateTime foo] (-> foo .getMonthValue))

(defn
 datetime->day-of-week
 [^LocalDateTime foo]
 (->
  foo
  ^{:line 89, :column 26}
  (->
   ^{:line 89, :column 30}
   (.getDayOfWeek)
   ^{:line 89, :column 46}
   (.getValue))))

(defn
 datetime->microsecond
 [^LocalDateTime foo]
 (-> foo ^{:line 160, :column 80} (-> -microsecond)))

(defn datetime->second [^LocalDateTime foo] (-> foo .getSecond))

(defn zdt->date [^ZonedDateTime foo] (-> foo .toLocalDate))

(defn
 zdt->timezone
 [^ZonedDateTime foo]
 (->
  foo
  ^{:line 120, :column 54}
  (->
   ^{:line 120, :column 58}
   (.getZone)
   ^{:line 120, :column 69}
   (.getId))))

(defn
 datetime->monthday
 [^LocalDateTime foo]
 (->
  foo
  ^{:line 79, :column 33}
  (-> ^{:line 79, :column 37} (MonthDay/from))))

(defn
 datetime->yearmonth
 [^LocalDateTime foo]
 (->
  foo
  ^{:line 65, :column 33}
  (-> ^{:line 65, :column 37} (YearMonth/from))))

(defn yearmonth->year [^YearMonth foo] (-> foo .getYear))

(defn
 zdt->millisecond
 [^ZonedDateTime foo]
 (-> foo ^{:line 157, :column 80} (-> -millisecond)))

(defn
 zdt->nanosecond
 [^ZonedDateTime foo]
 (-> foo ^{:line 163, :column 80} (-> -nanosecond)))

(defn zdt->second [^ZonedDateTime foo] (-> foo .getSecond))

(defn datetime->minute [^LocalDateTime foo] (-> foo .getMinute))

(defn
 datetime->day-of-month
 [^LocalDateTime foo]
 (-> foo .getDayOfMonth))

(defn zdt->hour [^ZonedDateTime foo] (-> foo .getHour))

(defn zdt->instant [^ZonedDateTime foo] (-> foo .toInstant))

(defn zdt->month [^ZonedDateTime foo] (-> foo .getMonthValue))

(defn
 zdt->monthday
 [^ZonedDateTime foo]
 (->
  foo
  ^{:line 79, :column 33}
  (-> ^{:line 79, :column 37} (MonthDay/from))))

(defn monthday->month [^MonthDay foo] (-> foo .getMonthValue))

(defn datetime->date [^LocalDateTime foo] (-> foo .toLocalDate))

(defn
 zdt->microsecond
 [^ZonedDateTime foo]
 (-> foo ^{:line 160, :column 80} (-> -microsecond)))

(defn zdt->day-of-month [^ZonedDateTime foo] (-> foo .getDayOfMonth))

(defn
 date->monthday
 [^LocalDate foo]
 (->
  foo
  ^{:line 79, :column 33}
  (-> ^{:line 79, :column 37} (MonthDay/from))))

(defn
 zdt->day-of-week
 [^ZonedDateTime foo]
 (->
  foo
  ^{:line 89, :column 26}
  (->
   ^{:line 89, :column 30}
   (.getDayOfWeek)
   ^{:line 89, :column 46}
   (.getValue))))

(defn time->second [^LocalTime foo] (-> foo .getSecond))

(defn date->month [^LocalDate foo] (-> foo .getMonthValue))

(defn
 instant->legacydate
 [^Instant foo]
 (->
  foo
  ^{:line 101, :column 44}
  (->
   ^{:line 101, :column 48}
   (.toEpochMilli)
   ^{:line 101, :column 64}
   (java.util.Date.))))

(defn date->year [^LocalDate foo] (-> foo .getYear))

(defn
 datetime->nanosecond
 [^LocalDateTime foo]
 (-> foo ^{:line 163, :column 80} (-> -nanosecond)))

(defn datetime->year [^LocalDateTime foo] (-> foo .getYear))

(defn instant->epochmilli [^Instant foo] (-> foo .toEpochMilli))

(defn datetime->hour [^LocalDateTime foo] (-> foo .getHour))

(defn date->day-of-month [^LocalDate foo] (-> foo .getDayOfMonth))

(defn zdt->datetime [^ZonedDateTime foo] (-> foo .toLocalDateTime))

(defn
 time->microsecond
 [^LocalTime foo]
 (-> foo ^{:line 160, :column 80} (-> -microsecond)))

(defn
 time->nanosecond
 [^LocalTime foo]
 (-> foo ^{:line 163, :column 80} (-> -nanosecond)))

(defn time->minute [^LocalTime foo] (-> foo .getMinute))

(defn time->hour [^LocalTime foo] (-> foo .getHour))

(defn zdt->time [^ZonedDateTime foo] (-> foo .toLocalTime))

(defn monthday->day-of-month [^MonthDay foo] (-> foo .getDayOfMonth))

(defn
 date->day-of-week
 [^LocalDate foo]
 (->
  foo
  ^{:line 89, :column 26}
  (->
   ^{:line 89, :column 30}
   (.getDayOfWeek)
   ^{:line 89, :column 46}
   (.getValue))))

(defn zdt->minute [^ZonedDateTime foo] (-> foo .getMinute))

(defn
 datetime->millisecond
 [^LocalDateTime foo]
 (-> foo ^{:line 157, :column 80} (-> -millisecond)))

(defn
 zdt->yearmonth
 [^ZonedDateTime foo]
 (->
  foo
  ^{:line 65, :column 33}
  (-> ^{:line 65, :column 37} (YearMonth/from))))

(defn yearmonth->month [^YearMonth foo] (-> foo .getMonthValue))

(defn
 time->millisecond
 [^LocalTime foo]
 (-> foo ^{:line 157, :column 80} (-> -millisecond)))

(defn datetime->time [^LocalDateTime foo] (-> foo .toLocalTime))

(defn
 date->yearmonth
 [^LocalDate foo]
 (->
  foo
  ^{:line 65, :column 33}
  (-> ^{:line 65, :column 37} (YearMonth/from))))

^{:line 32, :column 11} (comment "parsers")

(defn instant-parse [^java.lang.String foo] (Instant/parse foo))

(defn zdt-parse [^java.lang.String foo] (ZonedDateTime/parse foo))

(defn datetime-parse [^java.lang.String foo] (LocalDateTime/parse foo))

(defn date-parse [^java.lang.String foo] (LocalDate/parse foo))

(defn monthday-parse [^java.lang.String foo] (MonthDay/parse foo))

(defn time-parse [^java.lang.String foo] (LocalTime/parse foo))

(defn yearmonth-parse [^java.lang.String foo] (YearMonth/parse foo))

^{:line 34, :column 11} (comment "nowers")

(defn zdt-deref ([^java.time.Clock clock] (ZonedDateTime/now clock)))

(defn
 datetime-deref
 ([^java.time.Clock clock] (LocalDateTime/now clock)))

(defn date-deref ([^java.time.Clock clock] (LocalDate/now clock)))

(defn monthday-deref ([^java.time.Clock clock] (MonthDay/now clock)))

(defn time-deref ([^java.time.Clock clock] (LocalTime/now clock)))

(defn instant-deref ([^java.time.Clock clock] (Instant/now clock)))

(defn yearmonth-deref ([^java.time.Clock clock] (YearMonth/now clock)))

^{:line 36, :column 11} (comment "constructors")

(defn
 time-from
 [thing]
 (let
  [hour
   (get thing :hour 0)
   minute
   (get thing :minute 0)
   second
   (get thing :second 0)
   milli
   (get thing :millisecond 0)
   micro
   (get thing :microsecond 0)
   nano
   (get thing :nanosecond 0)]
  (LocalTime/of
   ^int hour
   ^int minute
   ^int second
   ^{:tag int}
   (+ (* milli 1000000) (* micro 1000) nano))))

(defn
 date-from
 [thing]
 (let
  [year
   (or (some-> (get thing :yearmonth) yearmonth->year) (:year thing))
   month
   (or
    (some-> (get thing :yearmonth) yearmonth->month)
    (some-> (get thing :monthday) monthday->month)
    (:month thing))
   day
   (or
    (some-> (get thing :monthday) monthday->day-of-month)
    (get thing :day-of-month))]
  (LocalDate/of ^int year ^int month ^int day)))

(defn
 yearmonth-from
 [{:keys [year month]}]
 (YearMonth/of ^int year ^int month))

(defn
 monthday-from
 [{:keys [month day-of-month]}]
 (MonthDay/of ^int month ^int day-of-month))

(defn
 datetime-from
 [thing]
 (let
  [date
   (or (get thing :date) (date-from thing))
   time
   (or (get thing :time) (time-from thing))]
  (LocalDateTime/of ^LocalDate date ^LocalTime time)))

(defn
 zdt-from
 [thing]
 (let
  [instant
   (get thing :instant)
   ldt
   (or
    instant
    (some-> (get thing :zdt) zdt->datetime)
    (get thing :datetime)
    (datetime-from thing))
   zone
   (get thing :timezone)]
  (if
   instant
   (ZonedDateTime/ofInstant instant (ZoneId/of zone))
   (ZonedDateTime/of
    ^LocalDateTime ldt
    ^{:tag ZoneId}
    (ZoneId/of zone)))))

(defn
 instant-from
 [thing]
 (or
  (some-> (get thing :epochmilli) (Instant/ofEpochMilli))
  (when-let [d (get thing :legacydate)] (.toInstant ^Date d))
  (some-> (or (get thing :zdt) (zdt-from thing)) (zdt->instant))))

^{:line 38, :column 11} (comment "other")

(comment "after-graph")

(defn
 enable-comparison-for-all-temporal-entities
 "in cljs envs, this makes `=`, `compare` and `hash` work on the value of Temporal entities.\n  It is optional, so that if this behaviour is not required, the resulting build size can be reduced. \n  "
 [])

(defn legacydate? [v] (instance? java.util.Date v))

(defn instant? [v] (instance? Instant v))

(defn date? [v] (instance? LocalDate v))

(defn datetime? [v] (instance? LocalDateTime v))

(defn time? [v] (instance? LocalTime v))

(defn monthday? [v] (instance? MonthDay v))

(defn yearmonth? [v] (instance? YearMonth v))

(defn zdt? [v] (instance? ZonedDateTime v))

(defn-
 java-time-clock
 "returns a partial implementation of java.time.Clock sufficient for all the java.time 'deref' methods"
 [instant-fn timezone-fn]
 (proxy
  [java.time.Clock]
  []
  (getZone [] (java.time.ZoneId/of (timezone-fn)))
  (instant [] (instant-fn))))

(defn
 clock-system-default-zone
 "a ticking clock having the ambient zone. "
 []
 (Clock/systemDefaultZone))

(defn
 clock-fixed
 "create a stopped clock"
 ([^ZonedDateTime zdt] (Clock/fixed (.toInstant zdt) (.getZone zdt)))
 ([^Instant instant ^String zone-str]
  (Clock/fixed instant (ZoneId/of zone-str))))

(defn
 clock-with-timezone
 "ticking clock in given timezone"
 [^String timezone]
 (Clock/system (ZoneId/of timezone)))

(defn
 clock-offset-millis
 "offset an existing clock by offset-millis"
 [a-clock offset-millis]
 (Clock/offset a-clock (Duration/ofMillis offset-millis)))

(defn
 clock
 [instant-fn timezone-fn]
 (java-time-clock instant-fn timezone-fn))

(defn
 clock-zdt-atom
 "create a clock which will dereference the zdt-atom.\n  \n  The caller must first construct the atom and by keeping a reference to it,\n   may change its value and therefore the value of the clock.\n  "
 [zdt-atom]
 (clock
  (fn get-instant [] (zdt->instant @zdt-atom))
  (fn get-zone [] (zdt->timezone @zdt-atom))))

(defn timezone-deref ([clock] (str (.getZone ^Clock clock))))

(defn legacydate->instant [d] (.toInstant ^java.util.Date d))

(defn
 instant->zdt-in-UTC
 [instant]
 (ZonedDateTime/ofInstant instant (ZoneId/of "UTC")))

(defn- greater [x y] (if (neg? (compare x y)) y x))

(defn
 max
 "Find the latest of the given arguments. Callers should ensure that no\n  argument is nil."
 [arg & args]
 (assert (every? some? (cons arg args)))
 (reduce
  (fn* [p1__46806# p2__46807#] (greater p1__46806# p2__46807#))
  arg
  args))

(defn- lesser [x y] (if (neg? (compare x y)) x y))

(defn
 min
 "Find the earliest of the given arguments. Callers should ensure that no\n  argument is nil."
 [arg & args]
 (assert (every? some? (cons arg args)))
 (reduce
  (fn* [p1__46808# p2__46809#] (lesser p1__46808# p2__46809#))
  arg
  args))

(defn
 <
 ([_x] true)
 ([x y] (neg? (compare x y)))
 ([x y & more]
  (if
   (< x y)
   (if
    (next more)
    (recur y (first more) (next more))
    (< y (first more)))
   false)))

(defn
 <=
 ([_x] true)
 ([x y] (not (pos? (compare x y))))
 ([x y & more]
  (if
   (<= x y)
   (if
    (next more)
    (recur y (first more) (next more))
    (<= y (first more)))
   false)))

(defn
 >
 ([_x] true)
 ([x y] (pos? (compare x y)))
 ([x y & more]
  (if
   (> x y)
   (if
    (next more)
    (recur y (first more) (next more))
    (> y (first more)))
   false)))

(defn
 >=
 ([_x] true)
 ([x y] (not (neg? (compare x y))))
 ([x y & more]
  (if
   (>= x y)
   (if
    (next more)
    (recur y (first more) (next more))
    (>= y (first more)))
   false)))

(defn
 coincident?
 [start end event]
 (and (<= start event) (>= end event)))

(defprotocol Property (unit [_]) (field [_]))

(extend-protocol
 HasTime
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
 (setFractional [x ^long t] (.with x ChronoField/NANO_OF_SECOND t)))

(def ^ValueRange sub-second-range (ValueRange/of 0 999))

(def
 nanoseconds-property
 (reify
  Object
  (toString [_] "nanoseconds-property")
  Property
  (unit [_] ChronoUnit/NANOS)
  (field
   [_]
   (reify
    java.time.temporal.TemporalField
    (rangeRefinedBy [_ _temporal] sub-second-range)
    (getFrom [_ temporal] (-nanosecond temporal))
    (adjustInto
     [_ temporal value]
     (.checkValidValue sub-second-range value nil)
     (let
      [fractional
       (getFractional temporal)
       millis+micros
       (-> fractional (/ 1000) long (* 1000))
       new-fractional
       (+ millis+micros value)]
      (-> temporal (setFractional new-fractional))))))))

(def
 microseconds-property
 (reify
  Object
  (toString [_] "microseconds-property")
  Property
  (unit [_] ChronoUnit/MICROS)
  (field
   [_]
   (reify
    java.time.temporal.TemporalField
    (rangeRefinedBy [_ _temporal] sub-second-range)
    (getFrom [_ temporal] (-microsecond temporal))
    (adjustInto
     [_ temporal value]
     (.checkValidValue sub-second-range value nil)
     (let
      [fractional
       (getFractional temporal)
       millis
       (-> fractional (/ 1000000) long (* 1000000))
       nanos
       (-> fractional (mod 1000))
       new-fractional
       (+ millis nanos (-> value (* 1000)))]
      (-> temporal (setFractional new-fractional))))))))

(def
 milliseconds-property
 (reify
  Object
  (toString [_] "milliseconds-property")
  Property
  (unit [_] ChronoUnit/MILLIS)
  (field
   [_]
   (reify
    java.time.temporal.TemporalField
    (rangeRefinedBy [_ _temporal] sub-second-range)
    (getFrom [_ temporal] (-millisecond temporal))
    (adjustInto
     [_ temporal value]
     (.checkValidValue sub-second-range value nil)
     (let
      [fractional
       (getFractional temporal)
       micros+nanos
       (-> fractional (mod 1000000))]
      (->
       temporal
       (setFractional (+ micros+nanos (-> value (* 1000000)))))))))))

(def
 seconds-property
 (reify
  Object
  (toString [_] "seconds-property")
  Property
  (unit [_] ChronoUnit/SECONDS)
  (field [_] ChronoField/SECOND_OF_MINUTE)))

(def
 minutes-property
 (reify
  Object
  (toString [_] "minutes-property")
  Property
  (unit [_] ChronoUnit/MINUTES)
  (field [_] ChronoField/MINUTE_OF_HOUR)))

(def
 hours-property
 (reify
  Object
  (toString [_] "hours-property")
  Property
  (unit [_] ChronoUnit/HOURS)
  (field [_] ChronoField/HOUR_OF_DAY)))

(def
 days-property
 (reify
  Object
  (toString [_] "days-property")
  Property
  (unit [_] ChronoUnit/DAYS)
  (field [_] ChronoField/DAY_OF_MONTH)))

(def
 months-property
 (reify
  Object
  (toString [_] "months-property")
  Property
  (unit [_] ChronoUnit/MONTHS)
  (field [_] ChronoField/MONTH_OF_YEAR)))

(def
 years-property
 (reify
  Object
  (toString [_] "years-property")
  Property
  (unit [_] ChronoUnit/YEARS)
  (field [_] ChronoField/YEAR)))

(def ^{:dynamic true} *block-non-commutative-operations* true)

(defn
 throw-if-set-months-or-years
 [temporal temporal-property]
 (when
  (and
   *block-non-commutative-operations*
   (contains? #{months-property years-property} temporal-property)
   (not (or (monthday? temporal) (yearmonth? temporal))))
  (throw
   (ex-info
    "see guardrails section at https://github.com/henryw374/chronos?tab=readme-ov-file#guardrails"
    {}))))

(defn
 throw-if-months-or-years-in-amount
 [temporal temporal-amount]
 (when
  (and
   *block-non-commutative-operations*
   (not (or (monthday? temporal) (yearmonth? temporal)))
   (and
    (instance? Period temporal-amount)
    (or
     (not (zero? (.getYears ^Period temporal-amount)))
     (not (zero? (.getMonths ^Period temporal-amount))))))
  (throw
   (ex-info
    "see guardrails section at https://github.com/henryw374/chronos?tab=readme-ov-file#guardrails"
    {}))))

(defn
 with
 "from temporal arg, derive a new temporal object with property field set to value\n  (t/with date 3 t/days-property) "
 [temporal value property]
 (throw-if-set-months-or-years temporal property)
 (.with
  ^Temporal temporal
  ^{:tag TemporalField}
  (field property)
  ^long value))

(defn until [v1 v2 property] (.until ^Temporal v1 v2 (unit property)))

(defn
 >>
 "move a temporal forward by an amount"
 ([temporal temporal-amount]
  (throw-if-months-or-years-in-amount temporal temporal-amount)
  (.plus ^Temporal temporal ^TemporalAmount temporal-amount))
 ([temporal amount temporal-property]
  (throw-if-set-months-or-years temporal temporal-property)
  (.plus
   ^Temporal temporal
   amount
   ^{:tag TemporalUnit}
   (unit temporal-property))))

(defn
 <<
 "move a temporal backward by an amount"
 ([temporal temporal-amount]
  (throw-if-months-or-years-in-amount temporal temporal-amount)
  (.minus ^Temporal temporal ^TemporalAmount temporal-amount))
 ([temporal amount temporal-property]
  (throw-if-set-months-or-years temporal temporal-property)
  (.minus
   ^Temporal temporal
   amount
   ^{:tag TemporalUnit}
   (unit temporal-property))))

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

(def
 weekday->weekday-name
 {weekday-monday weekday-monday-name,
  weekday-tuesday weekday-tuesday-name,
  weekday-wednesday weekday-wednesday-name,
  weekday-thursday weekday-thursday-name,
  weekday-friday weekday-friday-name,
  weekday-saturday weekday-saturday-name,
  weekday-sunday weekday-sunday-name})

(def
 weekday-name->weekday
 {weekday-monday-name weekday-monday,
  weekday-tuesday-name weekday-tuesday,
  weekday-wednesday-name weekday-wednesday,
  weekday-thursday-name weekday-thursday,
  weekday-friday-name weekday-friday,
  weekday-saturday-name weekday-saturday,
  weekday-sunday-name weekday-sunday})

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

(defprotocol JavaTruncateable (-truncate [_ unit]))

(extend-protocol
 JavaTruncateable
 ZonedDateTime
 (-truncate [zdt unit] (.truncatedTo zdt unit))
 LocalDateTime
 (-truncate [zdt unit] (.truncatedTo zdt unit))
 LocalTime
 (-truncate [zdt unit] (.truncatedTo zdt unit))
 Instant
 (-truncate [zdt unit] (.truncatedTo zdt unit)))

(defn
 truncate
 "zero property field (and smaller fields) of temporal"
 [temporal property]
 (-truncate temporal (unit property)))

(defn
 get-field
 [temporal property]
 (.get ^TemporalAccessor temporal (field property)))

(defn
 yearmonth+day-at-end-of-month
 "create a date having last day of month"
 [ym]
 (.atEndOfMonth ^YearMonth ym))

(defn
 monthday+year
 "create a date"
 [monthday year]
 (.atYear ^MonthDay monthday ^int year))

(defn
 yearmonth+day-of-month
 "create a date"
 [yearmonth day]
 (.atDay ^YearMonth yearmonth ^int day))

(defn
 date+time
 "create a datetime"
 [date time]
 (.atTime ^LocalDate date ^LocalTime time))

(defn time+date "create a datetime" [time date] (date+time date time))

(defn
 datetime+timezone
 "create a zdt"
 [datetime timezone]
 (.atZone ^LocalDateTime datetime (ZoneId/of timezone)))

(defn
 instant+timezone
 "create a zdt"
 [instant timezone]
 (.atZone ^Instant instant (ZoneId/of timezone)))

(defn epochmilli->instant [milli] (Instant/ofEpochMilli milli))

(defn
 date-next-or-same-weekday
 [date desired-dow-number]
 (let
  [curr-day-of-week (date->day-of-week date)]
  (>>
   date
   (-> (abs (- curr-day-of-week (+ 7 desired-dow-number))) (mod 7))
   days-property)))

(defn
 date-prev-or-same-weekday
 [date desired-dow-number]
 (let
  [curr-day-of-week (date->day-of-week date)]
  (<<
   date
   (-> (abs (- (+ 7 curr-day-of-week) desired-dow-number)) (mod 7))
   days-property)))

