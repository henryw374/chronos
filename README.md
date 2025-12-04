# Chronos


[![Tests build](https://github.com/henryw374/chronos/actions/workflows/tests.yaml/badge.svg)](https://github.com/henryw374/chronos/actions/workflows/tests.yaml)
[![bb compatible](https://raw.githubusercontent.com/babashka/babashka/master/logo/badge.svg)](https://babashka.org)

Clojure(Script) API to `java.time` on the JVM and `Temporal` on JS runtimes

Learn Chronos [live in a browser REPL](https://widdindustries.com/chronos-docs/public/)

## About

### java.time vs Temporal

The graph below shows the entities in [Temporal](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Temporal)  - the new platform date-time API for Javascript. If you know [java.time](https://docs.oracle.com/javase/8/docs/api/java/time/package-summary.html) already, it should look familiar to you. There are many differences, but there is a lot the same. The Chronos API leverages common ground between Temporal and java.time - aiming to be sufficient to satisfy the majority of everyday use cases.

Where the application developers need to use some feature that is not common, such as formatting, it is recommended to use the platform API with reader conditionals as required.

![graph of entities in Temporal](https://tc39.es/proposal-temporal/docs/object-model.svg)

## Rationale

Since it was introduced in Java 8, use of the java.time API has become more and more widespread because:

* it improves on the legacy `java.util.Date` API
* it is a platform API - developers and library authors can be confident that other developers will know the API and be
  happy to use it.

The same benefits will apply to the Temporal API when it is widely available.

Cross-platform date/time APIs for Clojure have already proven popular. It seems logical that one should exist targeting
both java.time and Temporal.

#### Some differences between java.time and Temporal

In java.time, there are entities (classes) for 

* year
* month
* timezone
* day-of-week

In `Temporal`, these are represented by numbers (or string, in the case of timezone). `Chronos` uses the Temporal approach in its API, rather than creating new entities for these types in  JS environments.

Following is some more detail:

*features of only java.time*

* parsing non-iso
  strings ([Temporal may have this in the future](https://github.com/js-temporal/proposal-temporal-v2/issues/2))
* 2 types to represent temporal-amounts: `Duration` and `Period`
* OffsetDateTime, OffsetTime, Month, Year and DayOfWeek entities
    * Chronos adds a cljs version of DayOfWeek, so there is e.g. `c/weekday-saturday`
    * OffsetDateTime & OffsetTime are not in Chronos
    * Month and Year are just represented by integers in Chronos
* A single field is used to represent nanoseconds-of-second - Temporal has separate fields for milliseconds, microseconds and nanoseconds. Chronos takes the Temporal approach and exposes the separate fields in both Clojure and Clojurescript.

*features of only temporal*

* Duration type matching ISO spec
* user-controllable rounding and conflict resolution - Chronos doesn't expose this and chooses same behaviour as java.time
* first-class support for non-ISO calendars

### What about Existing Cross-platform date/time APIs?

[Tick](https://github.com/juxt/tick) is great for application developers who want a
cross-platform date-time library based on the java.time API. Tick provides much useful functionality
on top of java.time, but users know they can always drop
to [cljc.java-time](https://github.com/henryw374/cljc.java-time),
to access the full java.time API directly when needed.

Even when Temporal is widely available, I would imagine many Clojure developers will want to keep using Tick because

* It is based on the same java.time API in both JVM and Javascript environments - so the full capability of java.time is available as required.
* The additional build size of Tick in
  Javascript [does not degrade application performance](https://widdindustries.com/blog/clojurescript-datetime-lib-comparison.html) for e.g. typical SPA use cases.
* Switching away from it will require significant time investment

Since `tick` is based on `java.time`, in its entirety it is incompatible with Temporal. Having said that a `chronos.tick`
namespace exists which contains a subset of the functions from `tick.core` which are compatible - and some commentary about those which are not. This is a WIP.

## Goals 

* make it hard for less experienced users to make mistakes
  * never use implicit clock or zone
  * have many non-polymorphic functions e.g. `(c/datetime->year x)` vs single generic `(c/year x)` to help users understand what types they are dealing with
  * block non-commutative operations by default (see guard-rails section in tutorial)
  * 
* restrict the API to core use-cases (e.g. construction, access etc)
  * e.g. no `range` function with implicit step
  * most projects have a `date-utils` ns for their specific use-cases, which is the place for that kind of thing
* In a cljs environment, the library code is amenable to dead code elimination, so only the functions used in the application are included in the final build - and avoid those functions dragging in large things from the core 

## Usage

### Depend

[![Clojars Project](https://img.shields.io/clojars/v/com.widdindustries/chronos.svg)](https://clojars.org/com.widdindustries/chronos)

        ; to get data-literals for java.time and Temporal, also add...


[![Clojars Project](https://img.shields.io/clojars/v/com.widdindustries/time-literals-temporal.svg)](https://clojars.org/com.widdindustries/time-literals-temporal)

As of October 2025

* [Temporal](https://github.com/tc39/proposal-temporal) has semi-stabilized at
  `ecma stage 3`, meaning implementors
  can still suggest changes 
* See [current browser support on MDN](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Temporal)
* Until widely available in browsers, a polyfill (e.g. [this](https://github.com/fullcalendar/temporal-polyfill)) of Temporal can be used.

```html
    <script>
    if(!window.Temporal){
      console.log('Temporal polyfill required');
      var head = document.getElementsByTagName('head')[0];
      var js = document.createElement("script");
      js.type = "text/javascript";
      js.id = "temporal-polyfill"
      js.src = "https://cdn.jsdelivr.net/npm/@js-temporal/polyfill@0.5.0/dist/index.umd.js"
      head.appendChild(js);
      js.addEventListener('load',function(){
        console.log('loaded' + window.temporal)
        window.Temporal = window.temporal.Temporal;
        window.Intl = window.temporal.Intl;
        Date.prototype.toTemporalInstant = window.temporal.toTemporalInstant;      
      });
    }
    else {      
        console.log('Temporal polyfill not required');
    }
</script>
```

java 9+ is required to use Chronos on the JVM - although everything except accessing sub-second property fields will work on java 8.

Chronos works on Babashka versions 1.12.212 and later, apart from the `clock` function.

### Require and use

```clojure
(ns my.cljc.namespace
  (:require [com.widdindustries.chronos :as c]
            [time-literals.read-write]))

(c/date-parse "2020-02-02")

(defn initialise []
  ; optional, print objects as data-literals
  (time-literals.read-write/print-time-literals-clj!)
  (time-literals.read-write/print-time-literals-cljs!)

  ;optional - make comparison, e.g. =,sort,compare etc work for all js/Temporal entities
  (c/enable-comparison-for-all-temporal-entities))


```

Now, learn the API [live in a browser REPL](https://widdindustries.com/chronos-docs/public/)

## Dev

see dev.clj for instructions  

