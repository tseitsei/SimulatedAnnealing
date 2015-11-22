# SimulatedAnnealing

(C) Juha Kari 2012.

Decomposing a graph by using simulated annealing.

This program requires a directory containing graph files as an argument.

For example:
java SimulatedAnnealing C:\Graphs

Example graphs can be downloaded from:

http://www.graphdrawing.org/download/rome-graphml.tgz

NOTE!  The graphml files in the file mentioned above have an outdated URL for the DTD.

You can fix the graphml files by running this script:

#!/bin/bash
sed -i -- 's/http:\/\/www.graphdrawing.org\/dtds\/graphml.dtd/http:\/\/graphml.graphdrawing.org\/dtds\/1.0rc\/graphml.dtd/g' *
