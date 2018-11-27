#!/bin/sh
APP_NAME=server-report-0.0.1-SNAPSHOT

tpid=`ps -ef|grep $APP_NAME|grep -v grep|grep -v kill|awk '{print $2}'`
echo $tpid
for pid in $tpid ; do
   echo $pid
   if [ -n $pid ]; then
       echo 'Kill Process!'
       kill -9 $pid
   fi
done