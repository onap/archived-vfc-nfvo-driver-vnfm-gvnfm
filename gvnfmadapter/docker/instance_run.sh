#!/bin/bash

cd /service/vfc/nfvo/driver/vnfm/gvnfm/gvnfmadapter
./run.sh

while [ ! -f logs/runtime_driver.log ]; do
    sleep 1
done
tail -F logs/runtime_driver.log