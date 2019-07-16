#/bin/bash

export ADVERTISED_LISTENERS=PLAINTEXT://127.0.0.1:9092

if [ ! -z "$ADVERTISED_LISTENERS" ]; then
    echo "advertised listeners: $ADVERTISED_LISTENERS"
    export ESCAPED_ADVERTISED_LISTENERS=$(echo $ADVERTISED_LISTENERS | sed 's/\//\\\//g');
    sed -r -i "s/#(advertised.listeners)=(.*)/\1=$ESCAPED_ADVERTISED_LISTENERS/g" server.properties
fi
