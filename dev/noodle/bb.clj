(ns noodle.bb
  (:import 
    (java.time Instant)
    (java.time.temporal TemporalField ValueRange)))

(def field
  (reify
    TemporalField
    (rangeRefinedBy [_ _temporal] (ValueRange/of 0 999))
    (getFrom [this temporal]  1)
    (adjustInto [this temporal value] temporal)))

(comment
  ;; exercise the methods of 'field'
  (.rangeRefinedBy field nil)
  (.getFrom  field nil)
  (.adjustInto field (Instant/now) 1)
  )
