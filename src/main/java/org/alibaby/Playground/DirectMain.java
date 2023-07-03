

package org.alibaby.Playground;

import com.google.cloud.firestore.Firestore;
import org.alibaby.View.*;
import org.alibaby.Model.*;
import org.alibaby.Playground.*;
import org.alibaby.Controller.*;

public class DirectMain {
	public static void main(String args[]){

		Firestore db = new Database().db;
		VibeBayinMain2 vb = new VibeBayinMain2(db, 2);
       	vb.setVisible(true);
	
		// VibeBayinMain2 vb2 = new VibeBayinMain2(db, 2);
       	// vb2.setVisible(true);
		// vb = new VibeBayinMain2(db, 2);
       	// vb.setVisible(true);
		// // VibeBayinMain vb1 = new VibeBayinMain2(db, 2);
       	// vb1.setVisible(true);
	

	}

}