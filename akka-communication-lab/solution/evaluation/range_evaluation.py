import subprocess
import re
import numpy
import json
from collections import defaultdict
import matplotlib.pyplot as plt
from matplotlib.ticker import MultipleLocator, FormatStrFormatter

iteration=[10, 100, 1000, 10000, 100000, 1000000]
ranges={'r1': [100000,999999], 'r2': [100000,9999999] }

d = {}
d['r1']={}
d['r2'] = {}
os.chdir("../")

for key in ranges:
	d[key]['total']={}
	d[key]['process'] = {}
	d[key]['worker'] = {}

	for i in iteration:
		print "Range: From: ", ranges[key][0], "To: ", ranges[key][1], "Iteration: ", i
		d[key]['total'][i]=[]
		d[key]['process'][i] = []
		d[key]['worker'][i] = []
		for j in range(1, 5):
			os.chdir("../")
			cmd = 'mvn exec:java -Dexec.mainClass="com.spotify.akka.exercise4.PrimesFinder" -Dexec.args="' + str(i) + ' '+ str(ranges[key][0]) +' '+ str(ranges[key][1]) +'"'
			output = subprocess.check_output(cmd, shell=True)
			minput = map(int, re.findall(r'Master Input: (\d+)', output, re.DOTALL))
			moutput = map(int, re.findall(r'Master Output: (\d+)', output, re.DOTALL))
			worker = map(int, re.findall(r'Worker: (\d+)', output, re.DOTALL))
			main_exec = map(int, re.findall(r'Main: (\d+)', output, re.DOTALL))
			total = map(float, re.findall(r'Total time: ([-+]?[0-9]*\.?[0-9]+) s', output, re.DOTALL))

			worker_max = numpy.amax(map(int, worker))
			total_time = total[0]*1000*1000000
			worker_time = worker_max
			processing_time = minput[0] + sum(moutput) + main_exec[0]

			d[key]['total'][i].append(total_time)
			d[key]['process'][i].append(processing_time)
			d[key]['worker'][i].append(worker_time)

os.chdir("evaluation/")

with open('ranges.json', 'w') as outfile:
    json.dump(d, outfile)
