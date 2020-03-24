#!/bin/bash

newline=province,country,lastUpdate,confirmed,deaths,recovered,latitude,longitude
currdir=/tmp/COVID-19/csse_covid_19_data/csse_covid_19_daily_reports
cd /tmp/
git clone https://github.com/CSSEGISandData/COVID-19.git
find $currdir -name "*.csv" -type f -exec sed -i.bak "1 s/^.*$/$newline/" {} \;
find $currdir -type f ! -iname "*.csv" -delete

echo "sync with s3 bucket cocovid19"
aws s3 sync $currdir s3://cocovid.19/

rm -rf /tmp/COVID-19
echo "finished"
