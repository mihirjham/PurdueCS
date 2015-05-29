#!/bin/bash

echo "Hello $USER!" > tmp_msg
echo >> tmp_msg
echo "Today is" | date  >> tmp_msg
echo >> tmp_msg
echo "Sincerely," >> tmp_msg
echo "Myself" >> tmp_msg
/usr/bin/mailx -s "mail-hello" $USER < tmp_msg
echo "Message sent"
