(ns
 com.widdindustries.chronos
 (:refer-clojure :exclude [min max > < >= <= >> <<])
 (:require
  [com.widdindustries.chronos.temporal-comparison
   :as
   temporal-comparison]
  [com.widdindustries.chronos.chronos-clock :as chronos-clock]
  [goog.object]))

^{:line 30, :column 11} (comment "accessors")

(defn zdt->year [^js/Temporal.ZonedDateTime foo] (-> foo .-year))

(defn
 datetime->month
 [^js/Temporal.PlainDateTime foo]
 (->
  foo
  ^{:line 135, :column 78}
  (->
   ^{:line 135, :column 82}
   (.-monthCode)
   ^{:line 135, :column 96}
   (subs 1 3)
   js/parseInt)))

(defn
 datetime->day-of-week
 [^js/Temporal.PlainDateTime foo]
 (-> foo .-dayOfWeek))

(defn
 datetime->microsecond
 [^js/Temporal.PlainDateTime foo]
 (-> foo .-microsecond))

(defn
 datetime->second
 [^js/Temporal.PlainDateTime foo]
 (-> foo .-second))

(defn zdt->date [^js/Temporal.ZonedDateTime foo] (-> foo .toPlainDate))

(defn
 zdt->timezone
 [^js/Temporal.ZonedDateTime foo]
 (-> foo .-timeZoneId))

(defn
 datetime->monthday
 [^js/Temporal.PlainDateTime foo]
 (->
  foo
  ^{:line 77, :column 33}
  (-> ^{:line 77, :column 37} (js/Temporal.PlainMonthDay.from))))

(defn
 datetime->yearmonth
 [^js/Temporal.PlainDateTime foo]
 (->
  foo
  ^{:line 63, :column 33}
  (-> ^{:line 63, :column 37} (js/Temporal.PlainYearMonth.from))))

(defn yearmonth->year [^js/Temporal.PlainYearMonth foo] (-> foo .-year))

(defn
 zdt->millisecond
 [^js/Temporal.ZonedDateTime foo]
 (-> foo .-millisecond))

(defn
 zdt->nanosecond
 [^js/Temporal.ZonedDateTime foo]
 (-> foo .-nanosecond))

(defn zdt->second [^js/Temporal.ZonedDateTime foo] (-> foo .-second))

(defn
 datetime->minute
 [^js/Temporal.PlainDateTime foo]
 (-> foo .-minute))

(defn
 datetime->day-of-month
 [^js/Temporal.PlainDateTime foo]
 (-> foo .-day))

(defn zdt->hour [^js/Temporal.ZonedDateTime foo] (-> foo .-hour))

(defn zdt->instant [^js/Temporal.ZonedDateTime foo] (-> foo .toInstant))

(defn
 zdt->month
 [^js/Temporal.ZonedDateTime foo]
 (->
  foo
  ^{:line 135, :column 78}
  (->
   ^{:line 135, :column 82}
   (.-monthCode)
   ^{:line 135, :column 96}
   (subs 1 3)
   js/parseInt)))

(defn
 zdt->monthday
 [^js/Temporal.ZonedDateTime foo]
 (->
  foo
  ^{:line 77, :column 33}
  (-> ^{:line 77, :column 37} (js/Temporal.PlainMonthDay.from))))

(defn
 monthday->month
 [^js/Temporal.PlainMonthDay foo]
 (->
  foo
  ^{:line 135, :column 78}
  (->
   ^{:line 135, :column 82}
   (.-monthCode)
   ^{:line 135, :column 96}
   (subs 1 3)
   js/parseInt)))

(defn
 datetime->date
 [^js/Temporal.PlainDateTime foo]
 (-> foo .toPlainDate))

(defn
 zdt->microsecond
 [^js/Temporal.ZonedDateTime foo]
 (-> foo .-microsecond))

(defn zdt->day-of-month [^js/Temporal.ZonedDateTime foo] (-> foo .-day))

(defn
 date->monthday
 [^js/Temporal.PlainDate foo]
 (->
  foo
  ^{:line 77, :column 33}
  (-> ^{:line 77, :column 37} (js/Temporal.PlainMonthDay.from))))

(defn
 zdt->day-of-week
 [^js/Temporal.ZonedDateTime foo]
 (-> foo .-dayOfWeek))

(defn time->second [^js/Temporal.PlainTime foo] (-> foo .-second))

(defn date->month [^js/Temporal.PlainDate foo] (-> foo .-month))

(defn
 instant->legacydate
 [^js/Temporal.Instant foo]
 (->
  foo
  ^{:line 103, :column 44}
  (->
   ^{:line 103, :column 48}
   (.-epochMilliseconds)
   ^{:line 103, :column 70}
   (js/Date.))))

(defn date->year [^js/Temporal.PlainDate foo] (-> foo .-year))

(defn
 datetime->nanosecond
 [^js/Temporal.PlainDateTime foo]
 (-> foo .-nanosecond))

(defn datetime->year [^js/Temporal.PlainDateTime foo] (-> foo .-year))

(defn
 instant->epochmilli
 [^js/Temporal.Instant foo]
 (->
  foo
  ^{:line 52, :column 23}
  (-> ^{:line 52, :column 27} (.-epochMilliseconds))))

(defn datetime->hour [^js/Temporal.PlainDateTime foo] (-> foo .-hour))

(defn date->day-of-month [^js/Temporal.PlainDate foo] (-> foo .-day))

(defn
 zdt->datetime
 [^js/Temporal.ZonedDateTime foo]
 (-> foo .toPlainDateTime))

(defn
 time->microsecond
 [^js/Temporal.PlainTime foo]
 (-> foo .-microsecond))

(defn
 time->nanosecond
 [^js/Temporal.PlainTime foo]
 (-> foo .-nanosecond))

(defn time->minute [^js/Temporal.PlainTime foo] (-> foo .-minute))

(defn time->hour [^js/Temporal.PlainTime foo] (-> foo .-hour))

(defn zdt->time [^js/Temporal.ZonedDateTime foo] (-> foo .toPlainTime))

(defn
 monthday->day-of-month
 [^js/Temporal.PlainMonthDay foo]
 (-> foo .-day))

(defn
 date->day-of-week
 [^js/Temporal.PlainDate foo]
 (-> foo .-dayOfWeek))

(defn zdt->minute [^js/Temporal.ZonedDateTime foo] (-> foo .-minute))

(defn
 datetime->millisecond
 [^js/Temporal.PlainDateTime foo]
 (-> foo .-millisecond))

(defn
 zdt->yearmonth
 [^js/Temporal.ZonedDateTime foo]
 (->
  foo
  ^{:line 63, :column 33}
  (-> ^{:line 63, :column 37} (js/Temporal.PlainYearMonth.from))))

(defn
 yearmonth->month
 [^js/Temporal.PlainYearMonth foo]
 (-> foo .-month))

(defn
 time->millisecond
 [^js/Temporal.PlainTime foo]
 (-> foo .-millisecond))

(defn
 datetime->time
 [^js/Temporal.PlainDateTime foo]
 (-> foo .toPlainTime))

(defn
 date->yearmonth
 [^js/Temporal.PlainDate foo]
 (->
  foo
  ^{:line 63, :column 33}
  (-> ^{:line 63, :column 37} (js/Temporal.PlainYearMonth.from))))

^{:line 32, :column 11} (comment "parsers")

(defn
 instant-parse
 [^java.lang.String foo]
 (js/Temporal.Instant.from foo))

(defn
 zdt-parse
 [^java.lang.String foo]
 (js/Temporal.ZonedDateTime.from foo))

(defn
 datetime-parse
 [^java.lang.String foo]
 (js/Temporal.PlainDateTime.from foo))

(defn
 date-parse
 [^java.lang.String foo]
 (js/Temporal.PlainDate.from foo))

(defn
 monthday-parse
 [^java.lang.String foo]
 (js/Temporal.PlainMonthDay.from foo))

(defn
 time-parse
 [^java.lang.String foo]
 (js/Temporal.PlainTime.from foo))

(defn
 yearmonth-parse
 [^java.lang.String foo]
 (js/Temporal.PlainYearMonth.from foo))

^{:line 34, :column 11} (comment "nowers")

(defn zdt-deref ([^java.time.Clock clock] (chronos-clock/zdt clock)))

(defn
 datetime-deref
 ([^java.time.Clock clock] (chronos-clock/datetime clock)))

(defn date-deref ([^java.time.Clock clock] (chronos-clock/date clock)))

(defn
 monthday-deref
 ([^java.time.Clock clock] (chronos-clock/monthday clock)))

(defn time-deref ([^java.time.Clock clock] (chronos-clock/time clock)))

(defn
 instant-deref
 ([^java.time.Clock clock] (chronos-clock/instant clock)))

(defn
 yearmonth-deref
 ([^java.time.Clock clock] (chronos-clock/yearmonth clock)))

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
  (js/Temporal.PlainTime. hour minute second milli micro nano)))

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
  (js/Temporal.PlainDate. ^int year ^int month ^int day)))

(defn
 yearmonth-from
 [{:keys [year month]}]
 (js/Temporal.YearMonth. ^int year ^int month))

(defn
 monthday-from
 [{:keys [month day-of-month]}]
 (js/Temporal.YearMonth. ^int month ^int day-of-month))

(defn
 datetime-from
 [thing]
 (let
  [date
   (or (get thing :date) (date-from thing))
   time
   (or (get thing :time) (time-from thing))]
  (.toPlainDateTime ^js date time)))

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
   (.toZonedDateTimeISO ^js instant zone)
   (.toZonedDateTime ^js ldt zone))))

(defn
 instant-from
 [thing]
 (or
  (some->
   (get thing :epochmilli)
   (js/Temporal.Instant.fromEpochMilliseconds))
  (when-let [d (get thing :legacydate)] (.toTemporalInstant ^js d))
  (some-> (or (get thing :zdt) (zdt-from thing)) (zdt->instant))))

^{:line 38, :column 11} (comment "other")

(comment "after-graph")

(defn
 enable-comparison-for-all-temporal-entities
 "in cljs envs, this makes `=`, `compare` and `hash` work on the value of Temporal entities.\n  It is optional, so that if this behaviour is not required, the resulting build size can be reduced. \n  "
 []
 (temporal-comparison/enable-for-all))

(defn legacydate? [v] (instance? js/Date v))

(defn instant? [v] (instance? js/Temporal.Instant v))

(defn date? [v] (instance? js/Temporal.PlainDate v))

(defn datetime? [v] (instance? js/Temporal.PlainDateTime v))

(defn time? [v] (instance? js/Temporal.PlainTime v))

(defn monthday? [v] (instance? js/Temporal.PlainMonthDay v))

(defn yearmonth? [v] (instance? js/Temporal.PlainYearMonth v))

(defn zdt? [v] (instance? js/Temporal.ZonedDateTime v))

(defn
 clock-system-default-zone
 "a ticking clock having the ambient zone. "
 []
 js/Temporal.Now)

(defn
 clock-fixed
 "create a stopped clock"
 ([^ZonedDateTime zdt]
  (chronos-clock/clock
   (constantly (.toInstant zdt))
   (constantly (.-timeZoneId zdt))))
 ([^Instant instant ^String zone-str]
  (chronos-clock/clock (constantly instant) (constantly zone-str))))

(defn
 clock-with-timezone
 "ticking clock in given timezone"
 [^String timezone]
 (chronos-clock/clock js/Temporal.Now.instant (constantly timezone)))

(defn
 clock-offset-millis
 "offset an existing clock by offset-millis"
 [a-clock offset-millis]
 (chronos-clock/clock
  (fn
   []
   (.add (.instant ^js a-clock) (js-obj "milliseconds" offset-millis)))
  (constantly (chronos-clock/timezone a-clock))))

(defn
 clock
 [instant-fn timezone-fn]
 (chronos-clock/clock instant-fn timezone-fn))

(defn
 clock-zdt-atom
 "create a clock which will dereference the zdt-atom.\n  \n  The caller must first construct the atom and by keeping a reference to it,\n   may change its value and therefore the value of the clock.\n  "
 [zdt-atom]
 (clock
  (fn get-instant [] (zdt->instant @zdt-atom))
  (fn get-zone [] (zdt->timezone @zdt-atom))))

(defn timezone-deref ([clock] (chronos-clock/timezone clock)))

(defn legacydate->instant [d] (.toTemporalInstant ^js d))

(defn
 instant->zdt-in-UTC
 [instant]
 (.toZonedDateTimeISO ^js instant "UTC"))

(defn- greater [x y] (if (neg? (compare x y)) y x))

(defn
 max
 "Find the latest of the given arguments. Callers should ensure that no\n  argument is nil."
 [arg & args]
 (assert (every? some? (cons arg args)))
 (reduce
  (fn* [p1__46810# p2__46811#] (greater p1__46810# p2__46811#))
  arg
  args))

(defn- lesser [x y] (if (neg? (compare x y)) x y))

(defn
 min
 "Find the earliest of the given arguments. Callers should ensure that no\n  argument is nil."
 [arg & args]
 (assert (every? some? (cons arg args)))
 (reduce
  (fn* [p1__46812# p2__46813#] (lesser p1__46812# p2__46813#))
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

(def nanoseconds-property "nanosecond")

(def microseconds-property "microsecond")

(def milliseconds-property "millisecond")

(def seconds-property "second")

(def minutes-property "minute")

(def hours-property "hour")

(def days-property "day")

(def months-property "month")

(def years-property "year")

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
   (or
    (not (zero? (.-years ^js temporal-amount)))
    (not (zero? (.-months ^js temporal-amount)))))
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
  ^js temporal
  (js-obj property value)
  (js-obj "overflow" "reject")))

(defn
 until
 [v1 v2 property]
 (->
  (.until
   ^js v1
   ^js v2
   (js-obj "smallestUnit" property "largestUnit" property))
  (goog.object/get (str property "s"))))

(defn
 >>
 "move a temporal forward by an amount"
 ([temporal temporal-amount]
  (throw-if-months-or-years-in-amount temporal temporal-amount)
  (.add ^js temporal temporal-amount))
 ([temporal amount temporal-property]
  (throw-if-set-months-or-years temporal temporal-property)
  (.add ^js temporal (js-obj (str temporal-property "s") amount))))

(defn
 <<
 "move a temporal backward by an amount"
 ([temporal temporal-amount]
  (throw-if-months-or-years-in-amount temporal temporal-amount)
  (.subtract ^js temporal temporal-amount))
 ([temporal amount temporal-property]
  (throw-if-set-months-or-years temporal temporal-property)
  (.subtract ^js temporal (js-obj (str temporal-property "s") amount))))

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

(defn
 truncate
 "zero property field (and smaller fields) of temporal"
 [temporal property]
 (.round
  ^js temporal
  (js-obj "smallestUnit" property "roundingMode" "trunc")))

(defn get-field [temporal property] (goog.object/get temporal property))

(defn
 yearmonth+day-at-end-of-month
 "create a date having last day of month"
 [ym]
 (.toPlainDate ^js ym (js-obj "day" (.-daysInMonth ^js ym))))

(defn
 monthday+year
 "create a date"
 [monthday year]
 (.toPlainDate ^js monthday (js-obj "year" year)))

(defn
 yearmonth+day-of-month
 "create a date"
 [yearmonth day]
 (.toPlainDate ^js yearmonth (js-obj "day" day)))

(defn
 date+time
 "create a datetime"
 [date time]
 (.toPlainDateTime ^js date time))

(defn time+date "create a datetime" [time date] (date+time date time))

(defn
 datetime+timezone
 "create a zdt"
 [datetime timezone]
 (.toZonedDateTime ^js datetime timezone))

(defn
 instant+timezone
 "create a zdt"
 [instant timezone]
 (.toZonedDateTimeISO ^js instant timezone))

(defn
 epochmilli->instant
 [milli]
 (js/Temporal.Instant.fromEpochMilliseconds milli))

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

