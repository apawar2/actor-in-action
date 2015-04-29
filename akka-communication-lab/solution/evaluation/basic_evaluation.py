import subprocess
import re
import os
import numpy
import json

iteration=[1, 2, 4, 10, 100, 1000, 10000, 100000]

rangeFrom=1000000
rangeTo=9999999

d = {}

d['total']={}
d['process'] = {}
d['worker'] = {}
os.chdir("../")

for i in iteration:
	print "Range: From: ", rangeFrom, "To: ", rangeTo, "Iteration: ", i
	d['total'][i]=[]
	d['process'][i] = []
	d['worker'][i] = []
	for j in range(1, 5):
		cmd = 'mvn exec:java -Dexec.mainClass="com.spotify.akka.exercise4.PrimesFinder" -Dexec.args="' + str(i) + ' '+ str(rangeFrom) +' '+ str(rangeTo) +'"'
		output = subprocess.check_output(cmd, shell=True)
		#print output
		minput = map(int, re.findall(r'Master Input: (\d+)', output, re.DOTALL))
		moutput = map(int, re.findall(r'Master Output: (\d+)', output, re.DOTALL))
		worker = map(int, re.findall(r'Worker: (\d+)', output, re.DOTALL))
		main_exec = map(int, re.findall(r'Main: (\d+)', output, re.DOTALL))
		total = map(float, re.findall(r'Total time: ([-+]?[0-9]*\.?[0-9]+) s', output, re.DOTALL))

		worker_max = numpy.amax(map(int, worker))
		total_time = total[0]*1000*1000000
		worker_time = worker_max
		processing_time = minput[0] + sum(moutput) + main_exec[0]

		d['total'][i].append(total_time)
		d['process'][i].append(processing_time)
		d['worker'][i].append(worker_time)


os.chdir("evaluation/")

with open('basic.json', 'w') as outfile:
    json.dump(d, outfile)
