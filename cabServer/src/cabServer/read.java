/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cabServer;

/**
 *
 * @author Dimpy
 */
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.JOptionPane;

public class read{
	public static void main(String[] args) throws NumberFormatException, IOException{
		String filename = JOptionPane.showInputDialog("Enter the name of the file");
	   	try {
			FileInputStream fin = new FileInputStream("filename");
			DataInputStream in = new DataInputStream(fin);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strline;
			int size = Integer.parseInt(br.readLine());
			int[][] mat = new int[size][size];
			String[] temp;
			int i=0;
			while ((strline = br.readLine()) != null)   {
				  temp = strline.split(" ");
				  for(int j=0;j<size;j++){
					  mat[i][j] = Integer.parseInt(temp[j]);
				  }
				  i++;
				  }
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
