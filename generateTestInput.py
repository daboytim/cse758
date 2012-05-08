import random
import sys

if (len(sys.argv)>1):
	fileName = sys.argv[1]
else:
	fileName = raw_input("Input output file name:")

numOfRecord = input("Input number of test cases:")
minYear = input("Input lower limit of age(YYYY):")
maxYear = input("Input upper limit of age(YYYY):")
bh = raw_input("Do you want to enforce behavior level rules?(Y/N)")
bh=bh.lower()
if bh=='y':
	bh=True
else:
	bh=False

f= open(fileName,'w')
for i in range(0,int(numOfRecord)):
	year = random.randint(minYear,maxYear)
	month = random.randint(1,12)
	if month<10:
		month='0'+str(month)
	date = random.randint(1,28)
	if date<10:
		date = '0' +str(date)
	if bh:
		bhLvl =str(random.randint(1,3))
	else:
		bhLvl='1'
	
	f.write(str(i)+",Student,No."+str(i)+","+str(year)+"-"+str(month)+"-"+str(date)+","+str(random.randint(0,8))+","+str(random.randint(0,8))+","+str(random.randint(0,8))+","+bhLvl+"\n")
f.close()
print "Record written to file:"+fileName