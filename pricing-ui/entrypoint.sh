#!/bin/sh

sed -i -e "s~<getBaseUrl_replacement>~${PRICING_BFF_URL}~g" \
    -e "s/production: false/production: ${PRODUCTION:-false}/g" \
    /usr/share/nginx/html/index.html

CONFIG_FILE=/usr/share/nginx/html/config.json
echo "{ \"ALGARROBO_WAFFLE_BASE_URL\": \"${PRICING_BFF_URL}\"}" > ${CONFIG_FILE}

exec "$@"
