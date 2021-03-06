/*
 * PeekCalculator.java
 *
 * Created on 26 March 2007, 00:02
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package matt.dsp;

import matt.*;
import java.util.*;
/**
 *
 * @author bduggan
 */
public class PeakCalculator {
    
    /** Creates a new instance of PeekCalculator */
    public PeakCalculator() {
    }
    
    public static Vector<Integer> calculatePeaks2(float[] data, int border, int howFar, float thresholdNormal)
    {
        float thresholdValue = 0;
        // First calculate the threshold
        if (thresholdNormal > 0)
        {
            for (int i = 0 ; i < howFar ; i ++)
            {
                if (data[i] > thresholdValue)
                {
                    thresholdValue = data[i];
                }
            }
        }        
        
        thresholdValue = thresholdValue * thresholdNormal;        
        Vector peaks = new Vector();
        
        if (howFar >= border)
        {
            for (int i = border ; i < howFar - border ; i ++)
            {
                boolean addPeak = true;
                if (data[i] >= thresholdValue)
                {
                    for (int j = 0 ; j < border ; j ++)
                    {
                        if ((data[i] < data[i - j]) || (data[i] < data[i + j]))
                        {
                            addPeak = false;
                            break;
                        }                    
                    }
                }
                else
                {
                    addPeak = false;              
                }
                if (addPeak)
                {
                    peaks.add(new Integer(i));
                }
            }
        }
        return peaks;
    }
    
    public static Vector calculatePeaks(float[] data, int border, int howFar, float thresholdNormal)
    {
        float thresholdValue = 0;
        // First calculate the threshold
        if (thresholdNormal > 0)
        {
            for (int i = 0 ; i < howFar ; i ++)
            {
                if (data[i] > thresholdValue)
                {
                    thresholdValue = data[i];
                }
            }
        }        
        
        thresholdValue = thresholdValue * thresholdNormal;        
        Vector peaks = new Vector();
        
        if (howFar >= border)
        {
            for (int i = border ; i < howFar - border ; i ++)
            {
                boolean addPeak = true;
                if (data[i] >= thresholdValue)
                {
                    for (int j = 0 ; j < border ; j ++)
                    {
                        //if ((data[i] < data[i - j]) || (data[i] < data[i + j]))
                        if ((data[i-j] <= data[(i - j)-1]) || (data[i+j] <= data[i + j + 1]))                        
                        {
                            addPeak = false;
                            break;
                        }                    
                    }
                }
                else
                {
                    addPeak = false;              
                }
                if (addPeak)
                {
                    peaks.add(new Integer(i));
                }
            }
        }
        return peaks;
    }
    
    public static Vector<Integer> calculateTrough(float[] data, int border, int howFar, float thresholdNormal, Graph g, int sj)
    {        
        Vector<Integer> troughs = new Vector();
        float thresholdValue = Float.MAX_VALUE;
        // First calculate the threshold
        float min = Float.MAX_VALUE, max = Float.MIN_VALUE;
        if (thresholdNormal > 0)
        {
            for (int i = 0 ; i < howFar ; i ++)
            {
                if (data[i] < min)
                {
                    min = data[i];
                }
                else if (data[i] >= max)
                {
                    max = data[i];
                }
            }
        }        
        float range = max - min;
        thresholdValue = min + (thresholdNormal * range);
        g.getDefaultSeries().addHorizontalLine(thresholdValue);
        System.out.println("Threshold: " + thresholdValue);  
        
        if (howFar >= border)
        {
            for (int i = border ; i < howFar; i ++)
            {
                boolean addPeak = true;
                for (int j = 0 ; j < border ; j ++)
                {                       
                    if ((data[i] > data[i - j]))
                    {
                        addPeak = false;
                        break;
                    }                    
                }
                if (addPeak)
                {
                    if (data[i] <= thresholdValue)
                    {
                        // Dont add 2 consecutive troughs
                        if ((troughs.size() > 0) && (i <= troughs.elementAt(troughs.size() -1).intValue() + sj))                    
                        {
                            troughs.set(troughs.size() - 1, new Integer(i));
                        }
                        else
                        {
                            troughs.add(new Integer(i));
                        }
                    }
                }
            }
        }
        return troughs;
    }
}
