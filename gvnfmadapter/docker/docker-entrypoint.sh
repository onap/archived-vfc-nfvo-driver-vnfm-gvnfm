#!/bin/bash

# Configure service based on docker environment variables
python vfc/nfvo/driver/vnfm/gvnfm/gvnfmadapter/driver/pub/config.py
cat vfc/nfvo/driver/vnfm/gvnfm/gvnfmadapter/driver/pub/config.py

# microservice-specific one-time initialization
vfc/nfvo/driver/vnfm/gvnfm/gvnfmadapter/docker/instance_init.sh

date > init.log

# Start the microservice
vfc/nfvo/driver/vnfm/gvnfm/gvnfmadapter/docker/instance_run.sh
