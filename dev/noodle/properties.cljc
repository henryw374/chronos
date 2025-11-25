(ns noodle.properties
  (:require [com.widdindustries.chronos :as c]))

(comment 
  
  (c/zdt-deref (c/clock-system-default-zone))
  
  (let [dt (c/datetime-parse "3030-03-03T11:22:33.123456789")]
    {:second  (c/datetime->second dt) ;; 33
     :milli  (c/datetime->millisecond dt) ; 123
     :micro  (c/datetime->microsecond dt) ; 456
     :nano  (c/datetime->nanosecond dt) ; 789
     :java-second (java.time.LocalDateTime/.getSecond dt) ; 33
     :java-fractional (java.time.LocalDateTime/.getNano dt) ; 123456789
     })
  
  ; {:second 33, :milli 123, :micro 456, :nano 789, :java-second 33, :java-fractional 123456789}

  )
