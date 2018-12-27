
/************************************************************
 *                                                          *
 *                       Assignment 9            Fall 2018  *
 *                                                          *
 *  Programmer: Sneha Ravi Chandran                         *
 *                                                          *
 *  Purpose:    The java application displays a GUI that    *
 *              shows a graphical animation of different    *
 *              sorting algorithms using concurrency and    *
 *              graphics.                                   *
 *                                                          *
 ***********************************************************/

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.ImageObserver;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.util.Arrays;
import java.util.Random;

/******************************************************************************************************
*                                                                                                     *
*  SortPanel class containing components to set up for each of the panels and also defines the        *
*  animation thread.                                                                                  *
*                                                                                                     *
*******************************************************************************************************/
public class SortPanel extends JPanel implements ActionListener
{
   
   private static final long serialVersionUID = 1L;
   /* Default Dimension variable set up for scaling. */
   private static final int GRAPH_DIMENSION = 500;
   private SortAnimationPanel animationPanel = new SortAnimationPanel();
   private JPanel buttonPanel;
   
   /* Set up lists for all combo box inputs. */
   private final String[] sortMethods = { "Quick", "Bubble", "Shell", "Insertion", "Selection","Heap" };
   private final String[] sortingOrder = { "Ascending", "Descending" };
   private final String[] initialOrderArray = { "Random", "Ascending", "Descending" };
   private final String[] speedSettomgArray = { "Fast","Medium", "Slow" };
   
   /* Speed parameters. */
   private static final int SLOW_SPEED_MILLIS_WAIT = 5000;
   private static final int MEDIUM_SPEED_MILLIS_WAIT = 1000;
   private static final int FAST_SPEED_MILLIS_WAIT = 100;
   
   /* Integer array to be sorted */
   private int[] integerArrayToSort = new int[500];
   /*Boolean to select ascending or descending. */
   private boolean isAscending;
   
   // Speed integer
   int sortingSpeedWaitTime;
   /* Define all input buttons and combo boxes*/
   private JComboBox<String> sortingTypeComboBox = new JComboBox<String>(sortMethods);
   private JComboBox<String> sortingOrderType = new JComboBox<String>(sortingOrder);
   private JComboBox<String> initialDataOrdering = new JComboBox<String>(initialOrderArray);
   private JComboBox<String> sortingSpeeds = new JComboBox<String>(speedSettomgArray);
   private JButton populateArrayButton = new JButton("Populate Array");
   private JButton startSortingButton = new JButton("Sort");
   private JButton stopSortingButton = new JButton("Stop");
   private JButton pauseResumeButton = new JButton("Pause");
   /* Declare a thread for runnable SortAnimationPanel*/
   private Thread animationThread;

   /******************************************************************************************************
   *                                                                                                     *
   *  SortPanel() constructor that sets up the entire layout of the GUI and sets up action listener for  *
   *  all the inputs and adds bounds to all components.                                                  *
   *                                                                                                     *
   *******************************************************************************************************/
   public SortPanel()
   {
      /* Dummy pane for padding. */
      JPanel dummyPane =  new JPanel();
      /* Combo box input panel containing all combo box inputs */
      JPanel comboBoxInputPanel = new JPanel();
      dummyPane.setPreferredSize(new Dimension(GRAPH_DIMENSION,5));

      /* Selection panels for each combo box input. */
      JPanel sortingSpeedPanel = new JPanel();
      JPanel sortingTypePanel = new JPanel();
      JPanel populatingOrderPanel = new JPanel();
      JPanel sortingOrderPanel = new JPanel();
      /* Label and Filed layout, Hence 2 columns in 1 row. */
      sortingSpeedPanel.setLayout(new GridLayout(1, 2));
      sortingTypePanel.setLayout(new GridLayout(1, 2));
      populatingOrderPanel.setLayout(new GridLayout(1, 2));
      sortingOrderPanel.setLayout(new GridLayout(1, 2));
      
      /* Label for all combo list inputs. */
      JLabel sortingSpeedLabel = new JLabel("Speed");
      sortingSpeedLabel.setFont(new Font("Arial", Font.BOLD, 12));
      JLabel sortyingTypeLabel = new JLabel("Sort Method");
      sortyingTypeLabel.setFont(new Font("Arial", Font.BOLD, 12));
      JLabel populatingOrderLabel = new JLabel("Initial Data Order");
      populatingOrderLabel.setFont(new Font("Arial", Font.BOLD, 12));
      JLabel sortingOrderLabel = new JLabel("Order To Sort");
      sortingOrderLabel.setFont(new Font("Arial", Font.BOLD, 12));

      /* instance assigned to animation panel. */
      animationPanel = new SortAnimationPanel();
      /* Define size and*/
      this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
      this.setPreferredSize(new Dimension(500,500));
      comboBoxInputPanel.setPreferredSize(new Dimension(495, 60));
      comboBoxInputPanel.setLayout(new GridLayout(2, 2,10,5));
      setVisible(true);
      buttonPanel = new JPanel();

      sortingSpeedWaitTime = 100;
      
      populateArrayButton.addActionListener(this);
      startSortingButton.addActionListener(this);
      stopSortingButton.addActionListener(this);
      initialDataOrdering.addActionListener(this);
      sortingOrderType.addActionListener(this);
      pauseResumeButton.addActionListener(this);
      sortingSpeeds.addActionListener(this);
      /* Set up Button Panel */
      buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
      populateArrayButton.setPreferredSize(new Dimension(150, 25));
      populateArrayButton.setFont(new Font("Arial", Font.BOLD, 12));
      startSortingButton.setPreferredSize(new Dimension(110, 25));
      startSortingButton.setFont(new Font("Arial", Font.BOLD, 12));
      stopSortingButton.setPreferredSize(new Dimension(110, 25));
      stopSortingButton.setFont(new Font("Arial", Font.BOLD, 12));
      pauseResumeButton.setPreferredSize(new Dimension(110, 25));
      pauseResumeButton.setFont(new Font("Arial", Font.BOLD, 12));

      initialDataOrdering.setFont(new Font("Arial", Font.BOLD, 12));
      sortingSpeeds.setFont(new Font("Arial", Font.BOLD, 12));
      sortingTypeComboBox.setFont(new Font("Arial", Font.BOLD, 12));
      sortingOrderType.setFont(new Font("Arial", Font.BOLD, 12));

      /*Add all Combo  box inputs to a separate layout. */
      populatingOrderPanel.add(populatingOrderLabel);
      populatingOrderPanel.add(initialDataOrdering);
      sortingSpeedPanel.add(sortingSpeedLabel);
      sortingSpeedPanel.add(sortingSpeeds);
      sortingTypePanel.add(sortyingTypeLabel);
      sortingTypePanel.add(sortingTypeComboBox);
      sortingOrderPanel.add(sortingOrderLabel);
      sortingOrderPanel.add(sortingOrderType);
      
      /* Add Tool Tips for each input.  */
      populateArrayButton.setToolTipText("Click to populate data based on initial ordering selected.");
      stopSortingButton.setToolTipText("Click to stop the sorting.");
      initialDataOrdering.setToolTipText("Select initial ordering to populate data.");
      startSortingButton.setToolTipText("Click to start sorting.");
      pauseResumeButton.setToolTipText("Click to pause or resume sorting.");
      sortingTypeComboBox.setToolTipText("Select the method to sort.");
      sortingOrderType.setToolTipText("Select the order in which data is to be sorted ascending or descendng.");
      sortingSpeeds.setToolTipText("Select the sorting speeds.");
      /* Configure and set up button panel */
      buttonPanel.setPreferredSize(new Dimension(ImageObserver.WIDTH, 30));
      buttonPanel.add(populateArrayButton);
      buttonPanel.add(startSortingButton);
      buttonPanel.add(stopSortingButton);
      buttonPanel.add(pauseResumeButton);
      comboBoxInputPanel.add(populatingOrderPanel);
      comboBoxInputPanel.add(sortingSpeedPanel);
      comboBoxInputPanel.add(sortingTypePanel);
      comboBoxInputPanel.add(sortingOrderPanel);
      /* Add all panels together. */
      this.add(dummyPane);
      this.add(animationPanel);
      this.add(buttonPanel);
      this.add(comboBoxInputPanel);
      /* Set up inputs allowed by default.*/
      populateArrayButton.setEnabled(true);
      startSortingButton.setEnabled(false);
      stopSortingButton.setEnabled(false);
      pauseResumeButton.setEnabled(false);
      /* Assign thread instance. */
      animationThread = new Thread(animationPanel);
      /*set default ascending flag to true. */
      isAscending = true;
   }
   
   public void quickSort(int integerArrayForSort[], int start, int end)
   {
      int startElementIndex = start;
      int endElementIndex = end;
      if (Thread.currentThread().isInterrupted())
      {
         start = end;
         return;
      }
      try
      {
         if (end - start >= 1)
         {
            int pivot = integerArrayForSort[start];

            while (endElementIndex > startElementIndex)
            {
               /* sort in ascending order if true.*/
               if (isAscending)
               {
                  /*Determine new start element and end element around the pivot element of the section. */
                  while (integerArrayForSort[startElementIndex] <= pivot && startElementIndex <= end && endElementIndex > startElementIndex)
                  {
                     startElementIndex++;
                  }
                  
                  while (integerArrayForSort[endElementIndex] > pivot && endElementIndex >= start && endElementIndex >= startElementIndex)
                  {
                     endElementIndex--;
                  }
                  /* end element is greater than the one started with then swap! */
                  if (endElementIndex > startElementIndex)
                  {
                     int temp = integerArrayForSort[startElementIndex];
                     integerArrayForSort[startElementIndex] = integerArrayForSort[endElementIndex];
                     integerArrayForSort[endElementIndex] = temp;
                     repaint();
                  }
               }
               else
               {
                  /*Determine new start element and end element around the pivot element of the section. */
                  while (integerArrayForSort[startElementIndex] >= pivot && startElementIndex <= end && endElementIndex > startElementIndex)
                  {
                     startElementIndex++;
                  }
                  
                  while (integerArrayForSort[endElementIndex] < pivot && endElementIndex >= start && endElementIndex >= startElementIndex)
                  {
                     endElementIndex--;
                  }
                  /* end element is greater than the one started with then swap! */
                  if (endElementIndex > startElementIndex)
                  {
                     int temp = integerArrayForSort[startElementIndex];
                     integerArrayForSort[startElementIndex] = integerArrayForSort[endElementIndex];
                     integerArrayForSort[endElementIndex] = temp;
                     repaint();
                  }
               }
               if (Thread.currentThread().isInterrupted())
               {
                  start = end;
                  return;
               }
            }
            
            int temp1 = integerArrayForSort[start];
            integerArrayForSort[start] = integerArrayForSort[endElementIndex];
            integerArrayForSort[endElementIndex] = temp1;
            repaint();
            /*the outer most call is a recursive loop hence called here.*/
            Thread.sleep(sortingSpeedWaitTime);
            if (Thread.currentThread().isInterrupted())
            {
               start = end;
               return;
            }
            /* wait till resume is pressed if isPaused is true. */
            waitTillResume();
            /* Recursively sort elements before partition and after partition */
               quickSort(integerArrayForSort, start, endElementIndex - 1);
               quickSort(integerArrayForSort, endElementIndex + 1, end);
         }
         else
         {
            return;
         }
      }
      catch (InterruptedException e)
      {
         Thread.currentThread().interrupt();
         return;
      }
   }
   
   /******************************************************************************************************
   *                                                                                                     *
   *  bubbleSort() implementation that sorts a given array using bubble sort algorithm.                  *
   *                                                                                                     *
   *******************************************************************************************************/
   public void bubbleSort(int[] integerArrayForSort)
   {
      
      try
      {
         int temp = 0;
         /* start with 1st element. */
         for (int elementIndex = 0; elementIndex < integerArrayForSort.length; elementIndex++)
         {
            /* start and run till n-1th element*/
            for (int otherElementIndex = 1; otherElementIndex < (integerArrayForSort.length - elementIndex); otherElementIndex++)
            {
               if (isAscending)
               {
                  if (integerArrayForSort[otherElementIndex - 1] > integerArrayForSort[otherElementIndex])
                  {
                     /* swap the elements!  in the other element is (n-1) th element is greater than nth*/
                     temp = integerArrayForSort[otherElementIndex - 1];
                     integerArrayForSort[otherElementIndex - 1] = integerArrayForSort[otherElementIndex];
                     integerArrayForSort[otherElementIndex] = temp;
                     repaint();
                  }
               }
               else
               {
                  if (integerArrayForSort[otherElementIndex - 1] < integerArrayForSort[otherElementIndex])
                  {
                     /* swap the elements!  in the other element is (n-1) th element is smaller nth*/
                     temp = integerArrayForSort[otherElementIndex - 1];
                     integerArrayForSort[otherElementIndex - 1] = integerArrayForSort[otherElementIndex];
                     integerArrayForSort[otherElementIndex] = temp;
                     repaint();
                  }
               }
            }
            /* wait till resume is pressed if isPaused is true. */
            waitTillResume();
            /* Delay for animation. */
            Thread.sleep(sortingSpeedWaitTime);
            if (Thread.currentThread().isInterrupted())
            {
               /* If interrupted then return control. */
               return;
            }
         }
      }
      catch (InterruptedException e)
      {
         Thread.currentThread().interrupt();
         e.printStackTrace(System.out);
         return;
      }
   }
   
   /******************************************************************************************************
   *                                                                                                     *
   *  shellSort() implementation that sorts a given array using shell sort algorithm.                    *
   *                                                                                                     *
   *******************************************************************************************************/
   public void shellSort(int[] integerArrayForSort)
   {
      try
      {

         int arrayLength = integerArrayForSort.length; 
         
         // Start with a big gap, then reduce the gap 
         for (int sectionGap = arrayLength/2; sectionGap > 0; sectionGap /= 2) 
         { 
            /* Do a gap by gap  insertion sort for this gap size.   The first gap elements integerArrayForSort[0..gap-1] are already 
              in gaped order keep adding one more element  until the entire array is gap sorted  */
            for (int iter = sectionGap; iter < arrayLength; iter += 1) 
            { 

               int temp = integerArrayForSort[iter]; 
               
               int shifted; 
               if(isAscending)
               {
                  for (shifted = iter; shifted >= sectionGap && integerArrayForSort[shifted - sectionGap] > temp; shifted -= sectionGap)
                  {
                     integerArrayForSort[shifted] = integerArrayForSort[shifted - sectionGap];
                     repaint();
                  }
               }
               else
               {
                  for (shifted = iter; shifted >= sectionGap && integerArrayForSort[shifted - sectionGap] < temp; shifted -= sectionGap)
                  {
                     integerArrayForSort[shifted] = integerArrayForSort[shifted - sectionGap];
                     repaint();
                  }
               }
              
               integerArrayForSort[shifted] = temp; 
               repaint();
               /*there are two outer loops, so dividing the time.*/
               Thread.sleep(sortingSpeedWaitTime);
               waitTillResume();
               if (Thread.currentThread().isInterrupted())
               {
                  return;
               }
            }
            repaint();
         } 
      }
      catch (InterruptedException e)
      {
         Thread.currentThread().interrupt();
         e.printStackTrace(System.out);
         return;
      }
      
   }
   
   /******************************************************************************************************
   *                                                                                                     *
   *  insertionSort() implementation that sorts a given array using insertion sort algorithm.            *
   *                                                                                                     *
   *******************************************************************************************************/
   public void insertionSort(int[] integerArrayForSort)
   {
      try
      {
         for (int elementIndex = 1; elementIndex < integerArrayForSort.length; elementIndex++)
         {
            int otherElementIndex = elementIndex;
            int temp = integerArrayForSort[elementIndex];
            if (isAscending)
            {
               while ((otherElementIndex > 0) && (integerArrayForSort[otherElementIndex - 1] > temp))
               {
                  
                  integerArrayForSort[otherElementIndex] = integerArrayForSort[otherElementIndex - 1];
                  otherElementIndex--;
                  repaint();
               }
            } else
            {
               while ((otherElementIndex > 0) && (integerArrayForSort[otherElementIndex - 1] < temp))
               {
                  
                  integerArrayForSort[otherElementIndex] = integerArrayForSort[otherElementIndex - 1];
                  otherElementIndex--;
                  repaint();
               }
            }
            integerArrayForSort[otherElementIndex] = temp;
            Thread.sleep(sortingSpeedWaitTime);
            repaint();
            waitTillResume();
            if (Thread.currentThread().isInterrupted())
            {
               break;
            }
         }
      }
      catch (InterruptedException e)
      {
         Thread.currentThread().interrupt();
         e.printStackTrace(System.out);
         return;
      }
   }

   public void selectionSort(int[] integerArrayForSort)
   {
      try
      {
         /* Iterate through all elements. */
         for (int elementIndex = 0; elementIndex < integerArrayForSort.length - 1; elementIndex++)
         {
            /* Iterate through all elements. */
            for (int otherElementIndex = elementIndex + 1; otherElementIndex < integerArrayForSort.length; otherElementIndex++)
            {
               if (isAscending)
               {
                  /*Swap if bigger.*/
                  if (integerArrayForSort[elementIndex] > integerArrayForSort[otherElementIndex])
                  {
                     int temp = integerArrayForSort[elementIndex];
                     integerArrayForSort[elementIndex] = integerArrayForSort[otherElementIndex];
                     integerArrayForSort[otherElementIndex] = temp;
                     repaint();
                  }
               } 
               else
               {
                  /*Swap if smaller..*/
                  if (integerArrayForSort[elementIndex] < integerArrayForSort[otherElementIndex])
                  {
                     int temp = integerArrayForSort[elementIndex];
                     integerArrayForSort[elementIndex] = integerArrayForSort[otherElementIndex];
                     integerArrayForSort[otherElementIndex] = temp;
                     repaint();
                  }
               }
            }
            /* sleep if paused wait till resume.*/
            waitTillResume();
            if (Thread.currentThread().isInterrupted())
            {
               break;
            } 
            Thread.sleep(sortingSpeedWaitTime);
         }
         
      }
      
      catch (InterruptedException e)
      {
         Thread.currentThread().interrupt();
         e.printStackTrace(System.out);
         return;
      }
   }
   
   @SuppressWarnings("serial")
   private class SortAnimationPanel extends JPanel implements Runnable
   {
      /* Flag to check if pause button was pressed. */
      private boolean isPaused=false;

      @Override
      public void run()
      {
         /* Disable sorting button till sorting is finished. */
         startSortingButton.setEnabled(false);
         
         String selectedSortingType = sortingTypeComboBox.getSelectedItem().toString();

         if (selectedSortingType.equals("Quick"))
         {
            quickSort(integerArrayToSort, 0, integerArrayToSort.length - 1);
         }
         else if (selectedSortingType.equals("Bubble"))
         {
            bubbleSort(integerArrayToSort);
         }
         else if (selectedSortingType.equals("Shell"))
         {
            shellSort(integerArrayToSort);
         }
         else if (selectedSortingType.equals("Insertion"))
         {
            insertionSort(integerArrayToSort);
         }
         else if (selectedSortingType.equals("Selection"))
         {
            selectionSort(integerArrayToSort);
         }
         else if (selectedSortingType.equals("Heap"))
         {
               heapSort(integerArrayToSort);
         }
         resetAllInput();
      }
      
      public SortAnimationPanel()
      {
         /* set the size of the animation window */
         this.setPreferredSize(new Dimension(500, 500));
         /*  set the background to white */
         this.setBackground(new Color(255, 255, 255));
         isPaused=false;
         
      }
      /*******************************************************************************************************
       *                                                                                                     *
       *  isPaused() returns the state of the member variable isPaused                                       *
       *                                                                                                     *
       *******************************************************************************************************/
      @Override
      public void paintComponent(Graphics graphics)
      {
         super.paintComponent(graphics);
         if (integerArrayToSort[0] != 0)
         {
            
            for (int i = 0; i < integerArrayToSort.length; i++)
            {
                                /*x1 y1  x2      y2*/
               graphics.drawLine(i, 500, i, 500 - integerArrayToSort[i]);
               graphics.setColor(new Color(25,25,112));
            }
         }
      }
      
     /*******************************************************************************************************
      *                                                                                                     *
      *  isPaused() returns the state of the member variable isPaused                                       *
      *                                                                                                     *
      *******************************************************************************************************/
      public boolean isPaused()
      {
         return this.isPaused;
      }
      
     /*******************************************************************************************************
      *                                                                                                     *
      *  isPaused() set the state of the member variable isPaused  to true.                                 *
      *                                                                                                     *
      *******************************************************************************************************/
      public void pause()
      {
         isPaused = true;
      }

     /*******************************************************************************************************
      *                                                                                                     *
      *  resume() set the state of the member variable isPaused  to false.                                  *
      *                                                                                                     *
      *******************************************************************************************************/
      public void resume()
      {
         isPaused = false;
      }
   }
   
   
  /*******************************************************************************************************
   *                                                                                                     *
   *actionPerformed(ActionEvent event) function is an implementation of actionPerformed from             *
   * ActionListener allowing to perform specific function based on input.                                * 
   *                                                                                                     *
   *******************************************************************************************************/
   @SuppressWarnings("deprecation")
   @Override
   public void actionPerformed(ActionEvent event)
   {
      /* Populate Button is pressed. */
      if (event.getSource() == populateArrayButton)
      {
         /* create instance for Random class*/
         Random rand = new Random();
         for (int i = 0; i < integerArrayToSort.length; i++)
         {
            /*Generate a random number array element*/
            integerArrayToSort[i] = rand.nextInt((GRAPH_DIMENSION - 1) + 1) + 1;
         }
         /*get the initial ordering type. */
         String selectedValue = initialDataOrdering.getSelectedItem().toString();
         
         /* Show the array in the selected ordering */
         if (selectedValue.equals("Ascending"))
         {
            Arrays.sort(integerArrayToSort);
            repaint();
         }
         else if (selectedValue.equals("Descending"))
         {
            int startIndex = 0;
            int endIndex = integerArrayToSort.length - 1;
            int temp;

            /* Sort the array in ascending order */
            Arrays.sort(integerArrayToSort);

            // Reverse the array so it is not in descending order
            while (startIndex < endIndex)
            {
               temp = integerArrayToSort[startIndex];
               integerArrayToSort[startIndex] = integerArrayToSort[endIndex];
               integerArrayToSort[endIndex] = temp;
               startIndex++;
               endIndex--;
            }
            repaint();
         }
         else if (selectedValue.equals("Random"))
         {
            repaint();
         }
         
         /* Disable populate button and enable sort button*/
         startSortingButton.setEnabled(true);
         stopSortingButton.setEnabled(false);
         populateArrayButton.setEnabled(false);
         pauseResumeButton.setEnabled(false);
      }
      
      if (event.getSource() == startSortingButton)
      {
         sortingSpeeds.setEnabled(false);
         sortingOrderType.setEnabled(false);
         sortingTypeComboBox.setEnabled(false);
         initialDataOrdering.setEnabled(false);
         pauseResumeButton.setEnabled(true);
         populateArrayButton.setEnabled(false);
         startSortingButton.setEnabled(false);
         stopSortingButton.setEnabled(true);
         /* in case the thread is alive? */
         if (animationThread.isAlive())
         {
            animationThread.stop();

         }
         /* If no thread is already running then start a new thread? */
         animationPanel =  new SortAnimationPanel();
         animationThread = new Thread(animationPanel);
         pauseResumeButton.setText("Pause");
         
         animationThread.start();
         
      }
      
      /* actions to perform if stop button is pressed. */
      if (event.getSource() == stopSortingButton)
      {
         /* set up allowed inputs*/
         stopSortingButton.setEnabled(false);
         startSortingButton.setEnabled(false);
         populateArrayButton.setEnabled(true);
         sortingSpeeds.setEnabled(true);
         sortingOrderType.setEnabled(true);
         sortingTypeComboBox.setEnabled(true);
         initialDataOrdering.setEnabled(true);
         pauseResumeButton.setEnabled(false);
         animationThread.interrupt();
      }
      /* select to order in ascending or descending the inital data */
      if(event.getSource() == initialDataOrdering)
      {
         /* set up allowed inputs*/
         stopSortingButton.setEnabled(false);
         startSortingButton.setEnabled(true);
         populateArrayButton.setEnabled(true);
         sortingSpeeds.setEnabled(true);
         sortingOrderType.setEnabled(true);
         sortingTypeComboBox.setEnabled(true);
         initialDataOrdering.setEnabled(true);
         pauseResumeButton.setEnabled(false);
      }
      /* select to order in ascending or descending? */
      if (event.getSource() == sortingOrderType)
      {
         if (sortingOrderType.getSelectedItem().toString().equals("Ascending"))
         {
            isAscending = true;
         }
         else
         {
            isAscending = false;
         }
         /* set up allowed inputs*/
         stopSortingButton.setEnabled(false);
         pauseResumeButton.setEnabled(false);
         if(!populateArrayButton.isEnabled())
         {
            startSortingButton.setEnabled(true);
         }
         sortingSpeeds.setEnabled(true);
         sortingOrderType.setEnabled(true);
         sortingTypeComboBox.setEnabled(true);
         initialDataOrdering.setEnabled(true);
         
      }
      if(event.getSource() == sortingSpeeds)
      {
         /*Set sorting speed if any one is selected. */
         String selectedSortingSpeed = sortingSpeeds.getSelectedItem().toString();
         if (selectedSortingSpeed.compareTo("Slow") == 0)
         {
            sortingSpeedWaitTime = SLOW_SPEED_MILLIS_WAIT;
         }
         else if (selectedSortingSpeed.compareTo("Medium") == 0)
         {
            sortingSpeedWaitTime = MEDIUM_SPEED_MILLIS_WAIT;
         }
         else if (selectedSortingSpeed.compareTo("Fast") == 0)
         {
            sortingSpeedWaitTime = FAST_SPEED_MILLIS_WAIT;
         }

      }
      if(event.getSource() == pauseResumeButton)
      {
         /* Set Pause flag to true if button is pressed and make the button text dynamic. */
         if(pauseResumeButton.getText().compareTo("Pause") == 0)
         {
            animationPanel.pause();
            pauseResumeButton.setText("Resume");
         }
         else
         {
            /* Set the thread to resume. */
            pauseResumeButton.setText("Pause");
            animationPanel.resume();
         }
      }
   }
   
  /*******************************************************************************************************
   *                                                                                                     *
   * waitTillResume() function used to pause the thread if a the flag isPaused has been set to true.     *
   * and hence making the thread go to sleep until its set to go back to false.                          * 
   *                                                                                                     *
   *******************************************************************************************************/
   public void waitTillResume()
   {
      while(animationPanel.isPaused())
      {
         try
         {
            /* it the thread was alive then sleep. */
            if(Thread.currentThread().isAlive())
            {
               Thread.sleep(100);
            }
            else
            {
               return;
            }
         }
         catch (InterruptedException e)
         {
            return;
         }
      }
   }
   /*******************************************************************************************************
    *                                                                                                     *
    *  resetAllInputs() resets button configuration states for all inputs                                 *
    *                                                                                                     *
    *******************************************************************************************************/
   public void resetAllInput()
   {
      stopSortingButton.setEnabled(false);
      startSortingButton.setEnabled(false);
      populateArrayButton.setEnabled(true);
      pauseResumeButton.setEnabled(false);
      
      sortingSpeeds.setEnabled(true);
      sortingOrderType.setEnabled(true);
      sortingTypeComboBox.setEnabled(true);
      initialDataOrdering.setEnabled(true);
   }
   
  /*******************************************************************************************************
   *                                                                                                     *
   *  heapSort(int arr[]) function to convert array to a  to perform heap sort of a given array.         *
   *                                                                                                     *
   *******************************************************************************************************/
   public void heapSort(int arr[])
   { 
      int arrayLength = arr.length; 
      try
      {
         /* Build heap (rearrange array) */
         for (int iterant = arrayLength / 2 - 1; iterant >= 0; iterant--)
         {
            heapify(arr, arrayLength, iterant); 
            repaint();
            Thread.sleep(sortingSpeedWaitTime);
            waitTillResume();
            if (Thread.currentThread().isInterrupted())
            {
               /* return control of the thread */
               return;
            }
         }
         /* One by one extract an element from heap */
         for (int iterant=arrayLength-1; iterant>=0; iterant--) 
         { 
            /* Move current root to end */
            int temp = arr[0]; 
            arr[0] = arr[iterant]; 
            arr[iterant] = temp; 

            /* call max/min heapify on the reduced heap*/
            heapify(arr, iterant, 0);
            Thread.sleep(sortingSpeedWaitTime);
            waitTillResume();
            if (Thread.currentThread().isInterrupted())
            {
               /* return control of the thread */
               return;
            }
         } 
      }
      catch (InterruptedException e)
      {
         Thread.currentThread().interrupt();
         e.printStackTrace(System.out);
         return;
      }
   } 

  /*******************************************************************************************************
   *                                                                                                     *
   *  heapify(int arrayToBeSorted[], int refIndex, int subTreeNode) function to convert array to a       *
   *  heap tree.                                                                                         *
   *                                                                                                     *
   *******************************************************************************************************/
   private void heapify(int arrayToBeSorted[], int refIndex, int subTreeNode) 
   { 
      int referenceValue = subTreeNode; // Initialize largest as root 
      int leftIndex = 2*subTreeNode + 1; // left = 2*i + 1 
      int rightIndex = 2*subTreeNode + 2; // right = 2*i + 2
         /* if sorting is ascending then sort in increasing order */
         if(isAscending)
         {
            /* If left child is larger than root */
            if (leftIndex < refIndex && arrayToBeSorted[leftIndex] > arrayToBeSorted[referenceValue])
            {
               referenceValue = leftIndex; 
            }
            /* If right child is larger than largest so far */
            if (rightIndex < refIndex && arrayToBeSorted[rightIndex] > arrayToBeSorted[referenceValue])
            {
               referenceValue = rightIndex;
            }
         }
         else
         {
            /* If left child is smaller than root */
            if (leftIndex < refIndex && arrayToBeSorted[leftIndex] < arrayToBeSorted[referenceValue])
            {
               referenceValue = leftIndex; 
            }
            /* If right child is smaller than smallest so far */
            if (rightIndex < refIndex && arrayToBeSorted[rightIndex] < arrayToBeSorted[referenceValue])
            {
               referenceValue = rightIndex;
            }

         }
         repaint();

         /* If largest is not root */
         if (referenceValue != subTreeNode) 
         { 
            int swap = arrayToBeSorted[subTreeNode]; 
            arrayToBeSorted[subTreeNode] = arrayToBeSorted[referenceValue]; 
            arrayToBeSorted[referenceValue] = swap; 
   
            /* Recursively heapify the affected sub-tree */
            heapify(arrayToBeSorted, refIndex, referenceValue);
            repaint();
         }

   } 
}
