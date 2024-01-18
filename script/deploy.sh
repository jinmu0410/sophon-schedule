#!/bin/sh
# ./ry.sh start 启动 stop 停止 restart 重启 status 状态
AppName=xxx.jar

# JVM参数
JVM_OPTS="-Dname=$AppName  -Duser.timezone=Asia/Shanghai -Xms512m -Xmx1024m -XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=512m"
export basedir=$(cd `dirname $0`/..; pwd)
cd $basedir

if [ "$1" = "" ]; then
    echo -e "\033[0;31m 未输入操作名 \033[0m  \033[0;34m {start|stop|restart|status} \033[0m"
    exit 1
fi

if [ "$AppName" = "" ]; then
    echo -e "\033[0;31m 未输入应用名 \033[0m"
    exit 1
fi

case $1 in
    start)
    {
      PID=`ps -ef |grep java|grep $AppName|grep -v grep|awk '{print $2}'`

      if [ x"$PID" != x"" ]; then
          echo "$AppName is running..."
      else
          nohup java $JVM_OPTS -Dloader.path=conf -jar ${basedir}/jar/$AppName > /dev/null 2>&1 &
          echo "Start $AppName success..."
      fi
    }
    ;;
    stop)
    {
      echo "Stop $AppName"

      PID=""
      query(){
          PID=`ps -ef |grep java|grep $AppName|grep -v grep|awk '{print $2}'`
      }

      query
      if [ x"$PID" != x"" ]; then
          kill -TERM $PID
          echo "$AppName (pid:$PID) exiting..."
          while [ x"$PID" != x"" ]
          do
              sleep 1
              query
          done
          echo "$AppName exited."
      else
          echo "$AppName already stopped."
      fi
    }
    ;;
    restart)
    {
      stop
      sleep 2
      start
    }
    ;;
    status)
    {
      PID=`ps -ef |grep java|grep $AppName|grep -v grep|wc -l`
      if [ $PID != 0 ];then
          echo "$AppName is running..."
      else
          echo "$AppName is not running..."
      fi
    }
    ;;
    *)

esac
