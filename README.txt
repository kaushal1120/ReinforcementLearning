Reinforcement Learning:
1. The main function in java file Executor.java in package ai.mdp.rl runs the Reinforcement Learning program.
2. The input for this program is defined in ReinforcementLearning-master/src/input_files/mdp_input.txt. Replace the contents
of this input file to run reinforcement learning on any other input.

To run the above program:
1. Unzip ReinforcementLearning-master.zip.
2. cd ReinforcementLearning-master
3. Replace the contents of src/input_files/mdp_input.txt to change the input on which the program is run.

Commands to run the above program on Windows:

dir /s /B *.java > sources.txt (To populate a list of .java files in a single file sources.txt)
javac -d classes @sources.txt (To compile the java files populated in sources.txt)
java -cp classes ai.mdp.rl.Executor (To run Executor.java)

Commands to run the above programs on Linux/Mac

find . -name "*.java" > sources.txt (To populate a list of .java files in a single file sources.txt)
mkdir classes
javac -d classes @sources.txt (To compile the java files populated in sources.txt)
java -cp classes ai.mdp.rl.Executor (To run Executor.java)