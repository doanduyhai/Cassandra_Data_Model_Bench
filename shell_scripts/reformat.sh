#!/bin/sh
echo "duration in sec;user;system;idle;context switch" > $2
awk '
BEGIN { OFS = ";"; i=10}
{
	if($0 == "procs -----------memory---------- ---swap-- -----io---- --system-- -----cpu------ ---timestamp---" || $0 == " r  b   swpd   free   buff  cache   si   so    bi    bo   in   cs us sy id wa st") 
	{ 
		next 
	} 
	else if($15 == 100 && $14 == 0 && $13 == 0)
	{
		i=i+10		
		next;
	}
	else 
	{
		print i,$13,$14,$15,$12
		i=i+10
	}
}' $1 >> $2

