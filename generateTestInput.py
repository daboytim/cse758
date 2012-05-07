import random

f= open('testInput','w')
for i in range(0,100):
	year = random.randint(1990,2012)
	month = random.randint(1,12)
	if month<10:
		month='0'+str(month)
	date = random.randint(1,28)
	if date<10:
		date = '0' +str(date)
	f.write(str(i)+",Student,No."+str(i)+","+str(year)+"-"+str(month)+"-"+str(date)+","+str(random.randint(0,8))+","+str(random.randint(0,8))+","+str(random.randint(0,8))+","+str(random.randint(1,3))+"\n")
f.close()