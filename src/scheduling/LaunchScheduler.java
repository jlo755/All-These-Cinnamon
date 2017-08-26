package scheduling;
import java.io.IOException;
import org.jgrapht.ext.ImportException;

import inputParse.DotParser;
import visualisation.ProcessorScreen;

/*
* This class recursively calls the recursive method to get the children nodes of a particular node. As this occurs recursively,
* we traverse through the entire graph. When a node is visited, we check all possible time costs for that particular node being
* placed on the different processors. We then check which of these is the best time. This process continues until all the nodes
* in the network are visited and we have obtained the best scheduling times.
*/
public class LaunchScheduler {

   public static DotParser dotParser;
   public static int _noOfProcessors;
   public static String _fileName;

   public static void main(String[] args) throws IOException, ImportException {

      _fileName=args[0];
      _noOfProcessors = Integer.parseInt(args[1]);
      dotParser = new DotParser(_fileName);
      dotParser.parseInput();
      ProcessorScreen processor = new ProcessorScreen();
      processor.beginProcessing();
   }

   public void setProcessor(int processorCount) {
      _noOfProcessors = processorCount;

   }
   public void setFileName(String fileName) {
      _fileName = fileName;

   }

}
