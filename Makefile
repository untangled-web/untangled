LEIN_RUN = rlwrap lein run -m clojure.main ./script/figwheel.clj

dev:
	JVM_OPTS="-server -Ddev -Dtest" ${LEIN_RUN}

test:
	JVM_OPTS="-server -Dtest" ${LEIN_RUN}

cards:
	JVM_OPTS="-server -Dcards" ${LEIN_RUN}

server:
	rlwrap lein run -m clojure.main

tests:
	npm install
	lein doo chrome automated-tests once

help:
	@ make -rpn | sed -n -e '/^$$/ { n ; /^[^ ]*:/p; }' | sort | egrep --color '^[^ ]*:'

.PHONY: dev test cards server tests help
