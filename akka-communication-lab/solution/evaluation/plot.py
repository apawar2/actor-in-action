import numpy
import json
import math
import matplotlib.pyplot as plt
from matplotlib.ticker import MultipleLocator, FormatStrFormatter

workers = [1, 10, 100, 1000, 10000, 100000]
legends = ["total", "worker", "process"]
legend_map = {"total": "Total", "process": "Other", "worker": "Workers"}
colors = ['g','m','c','r','b','k','w']
markers = ['o','*','^','s','d','3','d','o','*','^','1','4']

def get_key(d, n):
    for k in d:
        if int(k) == n:
            return k

def process_input_data(input_data):
    plot_data = {'median': {}, 'err': {}}

    for k, v in data.iteritems():
        k = str(k)
        plot_data['median'][k] = []
        plot_data['err'][k] = []
        for n_workers in workers:
            readings = [float(x)/1000000 for x in v[get_key(v, n_workers)]]
            tmp = numpy.array(readings)
            median, err = numpy.mean(tmp, axis = 0), numpy.std(tmp, axis = 0)
            plot_data['median'][k].append(median)
            plot_data['err'][k].append(err)

    return plot_data


def plot(input_data):
    # plot the readings after processing the input data
    plot_data = process_input_data(input_data)
    fig = plt.figure(figsize=(12,12))
    ax = fig.add_subplot(1,1,1)
    xlabels = [math.log10(x) for x in workers]
    plots = []
    i = 0
    legend_tmp = []
    for k in legends:
        legend_tmp.append(k)
        if str(k) in legends:
            print "Plotting the graph for: ", k
            tmp = plt.errorbar(xlabels, plot_data['median'][k],
                               yerr = plot_data['err'][k], markerfacecolor = colors[i],
                               color = 'k', markersize = 20, ecolor = 'r',
                               marker = markers[i], label = k, linewidth = 4.0)

            plots.append(tmp)
            i += 1

    for tick in ax.xaxis.get_major_ticks():
        tick.label.set_fontsize(18)
    for tick in ax.yaxis.get_major_ticks():
        tick.label.set_fontsize(18)

    plt.legend((plots),[legend_map[i] for i in legend_tmp], loc = 'upper right',prop={'size':32})
    ax.grid(True)
    plt.xlim(-.1, 5.1)
    plt.xlabel('Number of Workers', fontsize = 24)
    plt.ylabel('Time (ms)', fontsize = 24)
    ax.set_xticklabels(workers)
    plt.savefig('actors_eval.eps')


if __name__ == '__main__':
    dname = "data.json"
    data=json.load(open(dname, 'r'))
    plot(data)
