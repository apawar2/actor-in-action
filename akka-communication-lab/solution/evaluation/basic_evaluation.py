import subprocess
import re
import os
import numpy
import json

#Worker Array
workers=[1, 2, 4, 10, 100, 1000, 10000, 100000]

rangeFrom=1000000
rangeTo=9999999

d = {}

#Three parameters for our Graph
# here process is same as Other
d['total']={}
d['process'] = {}
d['worker'] = {}
os.chdir("../")

#Iterations for different worker count
for i in workers:
	print "Range: From: ", rangeFrom, "To: ", rangeTo, "workers: ", i
	d['total'][i]=[]
	d['process'][i] = []
	d['worker'][i] = []
	# four iterations for each worker value
	for j in range(1, 5):
		cmd = 'mvn exec:java -Dexec.mainClass="com.spotify.akka.exercise4.PrimesFinder" -Dexec.args="' + str(i) + ' '+ str(rangeFrom) +' '+ str(rangeTo) +'"'
		output = subprocess.check_output(cmd, shell=True)
		#List of all master input time
		#(master input time: time for a master to process an input message)
		minput = map(int, re.findall(r'Master Input: (\d+)', output, re.DOTALL))
		#List of all master output time
		#(master output time: time for a master to process an output message)
		moutput = map(int, re.findall(r'Master Output: (\d+)', output, re.DOTALL))
		#List of all worker time
		#(worker time: time for a worker to process an input message)
		worker = map(int, re.findall(r'Worker: (\d+)', output, re.DOTALL))
		#Time Taken by Main to execute
		main_exec = map(int, re.findall(r'Main: (\d+)', output, re.DOTALL))
		#Total time taken by application to process
		total = map(float, re.findall(r'Total time: ([-+]?[0-9]*\.?[0-9]+) s', output, re.DOTALL))

		worker_max = numpy.amax(map(int, worker))	# select max worker time
		total_time = total[0]*1000*1000000	# Convert to nano seconds
		worker_time = worker_max
		# Other time: Summation of Master Output Message Processing Time + Time to Execute Main
		# + Time for Master to process Input Message
		processing_time = minput[0] + sum(moutput) + main_exec[0]

		d['total'][i].append(total_time)
		d['process'][i].append(processing_time)
		d['worker'][i].append(worker_time)

os.chdir("evaluation/")

#Save the results in JSON Format for graph creation
with open('basic.json', 'w') as outfile:
    json.dump(d, outfile)
