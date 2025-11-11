generate-all:
			clojure -Adev -X com.widdindustries.gen.gen.tempo/generate-all
test-cljs:
			clojure -Adev -X dev/tests-ci-shadow :compile-mode :release
test-clj:
			clojure -Adev -X dev/run-clj-tests
test-bb:
			bb test
clean:
			clj -T:build clean
install:
			make clean && clj -T:build jar && clj -T:build install && mkdir -p tmp && cd tmp
deploy:
			clj -T:build deploy
build-docs:
			cd chronos-docs && clj -A:intellij-hack -X dev/build-docs && cd ..
.PHONY: list
list:
		@$(MAKE) -pRrq -f $(lastword $(MAKEFILE_LIST)) : 2>/dev/null | awk -v RS= -F: '/^# File/,/^# Finished Make data base/ {if ($$1 !~ "^[#.]") {print $$1}}' | sort | egrep -v -e '^[^[:alnum:]]' -e '^$@$$' | xargs
