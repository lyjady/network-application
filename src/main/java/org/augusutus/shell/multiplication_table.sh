#!/bin/bash
for i in {1..9}
do
  for j in {1..9}
  do
    if (( $i >= $j ))
    then
      number=$[$i * $j]
     echo -n "$j x $i = $number    "
    else
      break
    fi
  done
  echo
done