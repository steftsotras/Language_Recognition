/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Stefanos
 */

import com.rapidminer.tools.XMLException;
import com.rapidminer.Process;
import com.rapidminer.operator.OperatorException;
import com.rapidminer.RapidMiner;
import com.rapidminer.operator.Operator;
import com.rapidminer.example.ExampleSet;
import com.rapidminer.operator.IOContainer;
import com.rapidminer.operator.IOObject;
import com.rapidminer.repository.IOObjectEntry;
import com.rapidminer.operator.ModelApplier;
import com.rapidminer.operator.Operator;
import com.rapidminer.tools.OperatorService;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.border.LineBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import java.awt.Color;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.FileNotFoundException;
import java.io.File;
import com.rapidminer.Process;
import com.rapidminer.example.Attribute;
import com.rapidminer.example.Example;
import java.util.Iterator;

public class Lang_Recongition extends javax.swing.JFrame{

    /**
     * @param args the command line arguments
     */
    
    public static void main(String[] args)throws IOException, BadLocationException {
        new Lang_Recongition();
    }
    
    
   public Lang_Recongition() throws IOException, BadLocationException{
       String data = "";
        
        JTextPane text = new JTextPane();
        SimpleAttributeSet attributeSet = new SimpleAttributeSet();
        Document doc = text.getStyledDocument();  
        doc.insertString(doc.getLength(), data,attributeSet);
        text.setPreferredSize(new java.awt.Dimension(500, 400));
        
        JScrollPane jScrollPane = new JScrollPane(text);
        jScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        jScrollPane.setViewportBorder(new LineBorder(Color.RED));
        
        JOptionPane.showMessageDialog(this, jScrollPane,"Give us your text", JOptionPane.PLAIN_MESSAGE);
        
        data = text.getText();
        
        
        try (PrintWriter out = new PrintWriter(new FileWriter("data_check.txt", false))) {
            out.write(data);
            out.close();
        } catch (FileNotFoundException ex) {
            System.out.println("File not Found!");
        }
        
        // sundesh me to rapidminer
        try {
            
            
            RapidMiner.setExecutionMode(RapidMiner.ExecutionMode.COMMAND_LINE);
            RapidMiner.init();
              
            // epilegei to arxeio rpm(rapid miner project) 
            
            File f = new File("D:\\RapidMinerProjects\\Project_1.rmp");
            Process process = new  Process(f);
            
            
            // epilegei to operator sto opoio tha steilei thn eikona
            Operator ProcessDocumentsOperator = process.getOperator("ReadDocument");
            ProcessDocumentsOperator.setParameter("file", "data_check.txt");

            IOContainer ioResult = process.run();
            
            if (ioResult.getElementAt(0) instanceof ExampleSet) {
                ExampleSet resultSet = (ExampleSet)ioResult.getElementAt(0);
                
                for (Example example : resultSet) {
                    Iterator<Attribute> allAtts = example.getAttributes().allAttributes();
                        while (allAtts.hasNext()) {
                            Attribute a = allAtts.next();
                            if (a.isNumerical()) {
                                double value = example.getValue(a);
                                System.out.print(value + " ");
                            } else {
                                String value = example.getValueAsString(a);
                                System.out.print(value + " ");
                            }
                        }
                        System.out.println("\n");
                 }
                
                
            }
            
        }
        catch (IOException | XMLException | OperatorException ex) {
        }
   }
    
    
   
    
    
    
}
