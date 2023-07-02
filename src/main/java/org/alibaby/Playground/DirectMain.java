

package org.alibaby.Playground;

import com.google.cloud.firestore.Firestore;
import org.alibaby.View.*;
import org.alibaby.Model.*;
import org.alibaby.Playground.*;
import org.alibaby.Controller.*;

public class DirectMain {
	public static void main(String args[]){

		Firestore db = new Database().db;
		VibeBayinMain vb = new VibeBayinMain(db, 1);
       	vb.setVisible(true);
	
		VibeBayinMain vb1 = new VibeBayinMain(db, 2);
       	vb1.setVisible(true);
	

	}

}