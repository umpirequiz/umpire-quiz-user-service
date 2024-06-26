FROM icr.io/appcafe/open-liberty:kernel-slim-java21-openj9-ubi-minimal
USER root
COPY --chown=0:0 --chmod=755 /src/main/liberty/config /config
COPY --chown=0:0 --chmod=755 /target/lib/*.jar /opt/ol/wlp/usr/shared/resources
RUN features.sh

COPY --chown=0:0 --chmod=755 /target/user-service.war /config/apps
RUN configure.sh

USER 1001