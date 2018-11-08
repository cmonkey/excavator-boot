#!/bin/bash 

### execute data to zk 

    create /config/iboot,dev ""
    create /config/iboot,dev/idWorker.group "default"
    create /config/iboot,dev/idWorker.serverList "www.excavator.boot:2181"
    create /config/iboot,dev/idWorker.namespace "idWorker"
    create /config/iboot,dev/idWorker.baseSleepTimeMillisSeconds 1000
    create /config/iboot,dev/idWorker.maxSleepTimeMillisSeconds 3000
    create /config/iboot,dev/idWorker.maxRetries 3
    create /config/iboot,dev/idWorker.sessionTimeoutMillisSeconds 3000
    create /config/iboot,dev/idWorker.connectionTimeoutMillisSeconds 3000
    create /config/iboot,dev/idWorker.digest ""


