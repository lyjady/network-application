#!/bin/bash
number=$[$RANDOM % 100]
while true
do
  read -p "Please Input Number: " input
  valid=`echo $input | sed 's/[0-9]//g'`
  if [ ! -z $valid ]
  then
    echo "Please Input Right Number"
    continue
  fi
  if (( $input > $number ))
  then
    echo "Input Is Big, Please Again"
    continue
  elif (( $input < $number ))
  then
    echo "Input Is Small, Please Again"
    continue
  else
    echo "Hit"
    break
  fi
done
