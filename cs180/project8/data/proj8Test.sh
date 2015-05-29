#!/bin/bash

score=0;
for fname in out_z_file1 out_z_file2 out_z_file3 out_z_file4 out_z_file5 out_z_file6 out_z_file7
do
fpart=`echo $fname| cut -d'_' -f 3`
diff ./$fname ./out_in_$fpart > diff_$fpart
if [[ -s diff_$fpart ]] ; then
echo Processing $fname : output does not match 0/10 [Check diff_$fpart file for details]
else
if [ ! -f out_in_$fpart ]
then
echo "out_in_$fpart does not exist. Make sure all output files are present before running the script. Exiting.."
exit 1
fi
score=$((score + 10));
echo Processing $fname : +10
fi
done
echo "score = $score/70"
