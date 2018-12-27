
/************************************************************
 *                                                          *
 *                       Assignment 9            Fall 2018  *
 *                                                          *
 *  Programmer: Sneha Ravi Chandran                         *
 *                                                          *                                                         *
 *  Purpose:    The java application displays a GUI that    *
 *              shows a graphical animation of different    *
 *              sorting algorithms using concurrency and    *
 *              graphics.                                   *
 *                                                          *
 ***********************************************************/

import java.awt.GridLayout;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class SortApplication extends JFrame
{
   /* multiplier number of frames to scale to increase the number to add more panels*/
   private static final int NUMBER_OF_SORTING_FRAMES = 2;
   /* All panels are a save to list. */
  /* private  ArrayList<SortPanel> sortPanels = new ArrayList<SortPanel>(); */
   SortPanel panel1;
   SortPanel panel2;
   
   public static void main(String[] args)
   {
      // Create a class object (window)
      SortApplication sortApp = new SortApplication();
      sortApp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      sortApp.setSize(510*NUMBER_OF_SORTING_FRAMES, 640);
      sortApp.setResizable(false);
      sortApp.setVisible(true);
   }
   
   public SortApplication()
   {
      super("Sorting Animation");
      panel1 = new SortPanel();
      panel2 = new SortPanel();
      /* setting up panels */
      this.add(panel1);
      this.add(panel2);

      /* set up panel instances. 
       * Alternate method. 
      for (int panelCount=0;panelCount<NUMBER_OF_SORTING_FRAMES; panelCount++ )
      {
         sortPanels.add(new SortPanel());
         this.add(sortPanels.get(panelCount));
      }
      */
      
      /* GUI Window Information*/

      this.setLayout(new GridLayout(1, NUMBER_OF_SORTING_FRAMES,10,10));
      this.setVisible(true);
   }
   
}