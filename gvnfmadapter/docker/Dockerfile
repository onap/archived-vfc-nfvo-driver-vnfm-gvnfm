FROM python:2-alpine

ARG HTTP_PROXY=${HTTP_PROXY}
ARG HTTPS_PROXY=${HTTPS_PROXY}
ENV http_proxy $HTTP_PROXY
ENV https_proxy $HTTPS_PROXY
ADD . /service
WORKDIR /service

RUN sh /service/docker-env-conf.sh

EXPOSE 8484
USER onap
WORKDIR /service
ENTRYPOINT vfc/nfvo/driver/vnfm/gvnfm/gvnfmadapter/docker/docker-entrypoint.sh
