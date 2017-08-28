# Group 15: All These Cinnamon

##  Project Overview
This is currently the task scheduling algorithm, and overall data visualization. It currently utilises an iterative DFS search in order to traverse all possible 'reachable nodes'. It has branching and bounding to find the best solution and schedule. Parallel programming is also used to make the application multithreaded and this enables the scheduling to occur concurrently. In order to get more detail about the development process, and the algorithm in development, data visualization and testing refer to the Wiki pages.

## How to Build the Project
Run the jar file along with the appropriate input ".dot" file, number of processors, and the option.
* On Command line: java -jar scheduler.jar INPUT.dot P [OPTION]
* INPUT= a task graph with integer weights in dot format
* P= number of processors to schedule the INPUT graph on
* [OPTION] =  
* −p N use N cores for execution in parallel (default is sequential)
* −v visualise the search
* −o OUPUT output file is named OUTPUT (default is INPUT−output . dot)

The application correctly needs to have the correct file name and the file needs to exists in the directory.
