#! /bin/bash



for file in `ls -1 *.fhi`
do
 newfilename=`echo $file | sed 's/8\.fhi/LDA\.fhi/'`

 mv $file $newfilename

done
