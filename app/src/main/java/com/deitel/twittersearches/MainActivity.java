// MainActivity.java
// Manages your favorite Twitter searches for easy  
// access and display in the device's web browser
package com.deitel.twittersearches;

import java.util.ArrayList;
import java.util.Collections;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends ListActivity
{
   // name of SharedPreferences XML file that stores the saved searches 
   private static final String SEARCHES = "searches";
   
   private EditText queryEditText; // EditText where user enters a query
   private EditText tagEditText; // EditText where user tags a query
   private SharedPreferences savedSearches; // user's favorite searches
   private ArrayList<String> tags; // list of tags for saved searches
   private ArrayAdapter<String> adapter; // binds tags to ListView
   private String colorStr = "";

   private ImageButton imageButton;
   private ImageButton imageButton2;
   private ImageButton imageButton3;
   private ImageButton imageButton5;
   private ImageButton imageButton6;
   private ImageButton imageButton7;
   private ImageButton imageButton8;
   private ImageButton imageButton9;
   private ImageButton imageButton10;
   private ImageButton imageButton11;
   private ImageButton imageButton12;
   private ImageButton imageButton13;
   private ImageButton imageButton14;
   private ImageButton imageButton15;

   /*private OnClickListener listener;
   private OnClickListener listener2;
   private OnClickListener listener3;
   private OnClickListener listener4;
   private OnClickListener listener5;
   private OnClickListener listener6;
   private OnClickListener listener7;
   private OnClickListener listener8;
   private OnClickListener listener9;
   private OnClickListener listener10;
   private OnClickListener listener11;
   private OnClickListener listener12;
   private OnClickListener listener13;
   private OnClickListener listener14;
   private OnClickListener listener15;
*/




   // called when MainActivity is first created
   @Override
   protected void onCreate(Bundle savedInstanceState)
   {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);

      // get references to the EditTexts  
      queryEditText = (EditText) findViewById(R.id.queryEditText);
      tagEditText = (EditText) findViewById(R.id.tagEditText);
      
      // get the SharedPreferences containing the user's saved searches 
      savedSearches = getSharedPreferences(SEARCHES, MODE_PRIVATE); 

      // store the saved tags in an ArrayList then sort them
      tags = new ArrayList<String>(savedSearches.getAll().keySet());
      Collections.sort(tags, String.CASE_INSENSITIVE_ORDER); 
      
      // create ArrayAdapter and use it to bind tags to the ListView
      adapter = new ArrayAdapter<String>(this, R.layout.list_item, tags);
      setListAdapter(adapter);
      
      // register listener to save a new or edited search 
      ImageButton saveButton = (ImageButton)
              findViewById(R.id.saveButton);
      saveButton.setOnClickListener(saveButtonListener);

      ImageButton imageButton = (ImageButton) findViewById(R.id.imageButton);
      imageButton.setOnClickListener(listener);

      ImageButton imageButton2 = (ImageButton) findViewById(R.id.imageButton2);
      imageButton2.setOnClickListener(listener2);

      ImageButton imageButton3 = (ImageButton) findViewById(R.id.imageButton3);
      imageButton3.setOnClickListener(listener3);

      ImageButton imageButton4 = (ImageButton) findViewById(R.id.imageButton4);
      imageButton4.setOnClickListener(listener4);

      ImageButton imageButton5 = (ImageButton) findViewById(R.id.imageButton5);
      imageButton5.setOnClickListener(listener5);

      ImageButton imageButton6 = (ImageButton) findViewById(R.id.imageButton6);
      imageButton6.setOnClickListener(listener6);

      ImageButton imageButton7 = (ImageButton) findViewById(R.id.imageButton7);
      imageButton7.setOnClickListener(listener7);

      ImageButton imageButton8 = (ImageButton) findViewById(R.id.imageButton8);
      imageButton8.setOnClickListener(listener8);

      ImageButton imageButton9 = (ImageButton) findViewById(R.id.imageButton9);
      imageButton9.setOnClickListener(listener9);

      ImageButton imageButton10 = (ImageButton) findViewById(R.id.imageButton10);
      imageButton10.setOnClickListener(listener10);

      ImageButton imageButton11 = (ImageButton) findViewById(R.id.imageButton11);
      imageButton11.setOnClickListener(listener11);

      ImageButton imageButton12 = (ImageButton) findViewById(R.id.imageButton12);
      imageButton12.setOnClickListener(listener12);

      ImageButton imageButton13 = (ImageButton) findViewById(R.id.imageButton13);
      imageButton13.setOnClickListener(listener13);

      ImageButton imageButton14 = (ImageButton) findViewById(R.id.imageButton14);
      imageButton14.setOnClickListener(listener14);

      ImageButton imageButton15 = (ImageButton) findViewById(R.id.imageButton15);
      imageButton15.setOnClickListener(listener15);


      // register listener that searches Twitter when user touches a tag
      getListView().setOnItemClickListener(itemClickListener);  
      
      // set listener that allows user to delete or edit a search
      getListView().setOnItemLongClickListener(itemLongClickListener);  
   } // end method onCreate

   // saveButtonListener saves a tag-query pair into SharedPreferences
   public OnClickListener saveButtonListener = new OnClickListener() 
   {
      @Override
      public void onClick(View v) 
      {
         // create tag if neither queryEditText nor tagEditText is empty
         if (queryEditText.getText().length() > 0 &&
            tagEditText.getText().length() > 0)
         {
            if(colorStr.length()>0)
            {
               addTaggedSearch((queryEditText.getText().toString()+"&")
                               +getResources().getString(R.string.searchColor, colorStr),
                       tagEditText.getText().toString());

            }
            else {
               addTaggedSearch(queryEditText.getText().toString(),
                       tagEditText.getText().toString());
            }
            queryEditText.setText(""); // clear queryEditText
            tagEditText.setText(""); // clear tagEditText
            
            ((InputMethodManager) getSystemService(
               Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
               tagEditText.getWindowToken(), 0);  
         } 
         else // display message asking user to provide a query and a tag
         {
            // create a new AlertDialog Builder
            AlertDialog.Builder builder = 
               new AlertDialog.Builder(MainActivity.this);

            // set dialog's message to display
            builder.setMessage("Enter both a flickr search and a tag for it:");
            
            // provide an OK button that simply dismisses the dialog
            builder.setPositiveButton(R.string.OK, null); 
            
            // create AlertDialog from the AlertDialog.Builder
            AlertDialog errorDialog = builder.create();
            errorDialog.show(); // display the modal dialog
         } 
      } // end method onClick
   }; // end OnClickListener anonymous inner class

   // add new search to the save file, then refresh all Buttons
   private void addTaggedSearch(String query, String tag)
   {
      // get a SharedPreferences.Editor to store new tag/query pair
      SharedPreferences.Editor preferencesEditor = savedSearches.edit();
      preferencesEditor.putString(tag, query); // store current search
      preferencesEditor.apply(); // store the updated preferences
      
      // if tag is new, add to and sort tags, then display updated list
      if (!tags.contains(tag))
      {
         tags.add(tag); // add new tag
         Collections.sort(tags, String.CASE_INSENSITIVE_ORDER);
         adapter.notifyDataSetChanged(); // rebind tags to ListView
      }
   } 
   
   // itemClickListener launches a web browser to display search results
   OnItemClickListener itemClickListener = new OnItemClickListener() 
   {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, 
         int position, long id) 
      {
         // get query string and create a URL representing the search
         String tag = ((TextView) view).getText().toString();
         String urlString = getString(R.string.searchURL) +
            Uri.encode(savedSearches.getString(tag, ""), "UTF-8");
         
         // create an Intent to launch a web browser    
         Intent webIntent = new Intent(Intent.ACTION_VIEW, 
            Uri.parse(urlString));                      

         startActivity(webIntent); // launches web browser to view results
      } 
   }; // end itemClickListener declaration
   
   // itemLongClickListener displays a dialog allowing the user to delete 
   // or edit a saved search
   OnItemLongClickListener itemLongClickListener = 
      new OnItemLongClickListener()
      {
         @Override
         public boolean onItemLongClick(AdapterView<?> parent, View view,
            int position, long id)
         {
            // get the tag that the user long touched
            final String tag = ((TextView) view).getText().toString();
            
            // create a new AlertDialog
            AlertDialog.Builder builder = 
               new AlertDialog.Builder(MainActivity.this);
            
            // set the AlertDialog's title
            builder.setTitle(
               getString(R.string.shareEditDeleteTitle, tag));
            
            // set list of items to display in dialog
            builder.setItems(R.array.dialog_items, 
               new DialogInterface.OnClickListener()
               {
                  // responds to user touch by sharing, editing or 
                  // deleting a saved search
                  @Override
                  public void onClick(DialogInterface dialog, int which)
                  {
                     switch (which)
                     {
                        case 0: // share
                           shareSearch(tag);
                           break;
                        case 1: // edit
                           // set EditTexts to match chosen tag and query
                           tagEditText.setText(tag);
                           queryEditText.setText(
                              savedSearches.getString(tag, ""));
                           break;
                        case 2: // delete
                           deleteSearch(tag);
                           break;
                     } 
                  }
               } // end DialogInterface.OnClickListener
            ); // end call to builder.setItems
            
            // set the AlertDialog's negative Button
            builder.setNegativeButton(getString(R.string.cancel), 
               new DialogInterface.OnClickListener() 
               {
                 // called when the "Cancel" Button is clicked
                  public void onClick(DialogInterface dialog, int id) 
                  {
                     dialog.cancel(); // dismiss the AlertDialog
                  }
               } 
            ); // end call to setNegativeButton
            
            builder.create().show(); // display the AlertDialog            
            return true;
         } // end method onItemLongClick  
      }; // end OnItemLongClickListener declaration

   OnClickListener listener = new OnClickListener() {
      @Override
      public void onClick(View v){

         colorStr="0";

      }//end onclick
   };//end conclick listner

   OnClickListener listener2 = new OnClickListener() {
      @Override
      public void onClick(View v){

         colorStr="1";

      }//end onclick
   };//end conclick listner

   OnClickListener listener3 = new OnClickListener() {
      @Override
      public void onClick(View v){

         colorStr="2";

      }//end onclick
   };//end conclick listner

   OnClickListener listener4 = new OnClickListener() {
      @Override
      public void onClick(View v){

         colorStr="b";

      }//end onclick
   };//end conclick listner

   OnClickListener listener5 = new OnClickListener() {
      @Override
      public void onClick(View v){

         colorStr="3";

      }//end onclick
   };//end conclick listner

   OnClickListener listener6 = new OnClickListener() {
      @Override
      public void onClick(View v){

         colorStr="4";

      }//end onclick
   };//end conclick listner
   OnClickListener listener7 = new OnClickListener() {
      @Override
      public void onClick(View v){

         colorStr="5";

      }//end onclick
   };//end conclick listner
   OnClickListener listener8 = new OnClickListener() {
      @Override
      public void onClick(View v){

         colorStr="6";

      }//end onclick
   };//end conclick listner
   OnClickListener listener9 = new OnClickListener() {
      @Override
      public void onClick(View v){

         colorStr="7";

      }//end onclick
   };//end conclick listner
   OnClickListener listener10 = new OnClickListener() {
      @Override
      public void onClick(View v){

         colorStr="8";

      }//end onclick
   };//end conclick listner
   OnClickListener listener11 = new OnClickListener() {
      @Override
      public void onClick(View v){

         colorStr="9";

      }//end onclick
   };//end conclick listner

   OnClickListener listener12 = new OnClickListener() {
      @Override
      public void onClick(View v){

         colorStr="a";

      }//end onclick
   };//end conclick listner

   OnClickListener listener13 = new OnClickListener() {
      @Override
      public void onClick(View v){

         colorStr="c";

      }//end onclick
   };//end conclick listner

   OnClickListener listener14 = new OnClickListener() {
      @Override
      public void onClick(View v){

         colorStr="d";

      }//end onclick
   };//end conclick listner

   OnClickListener listener15 = new OnClickListener() {
      @Override
      public void onClick(View v){

         colorStr="e";

      }//end onclick
   };//end conclick listner






   // allows user to choose an app for sharing a saved search's URL
   private void shareSearch(String tag)
   {
      // create the URL representing the search
      String urlString = getString(R.string.searchURL) +
         Uri.encode(savedSearches.getString(tag, ""), "UTF-8");

      // create Intent to share urlString
      Intent shareIntent = new Intent();
      shareIntent.setAction(Intent.ACTION_SEND);
      shareIntent.putExtra(Intent.EXTRA_SUBJECT, 
         getString(R.string.shareSubject));
      shareIntent.putExtra(Intent.EXTRA_TEXT, 
         getString(R.string.shareMessage, urlString));
      shareIntent.setType("text/plain");
      
      // display apps that can share text
      startActivity(Intent.createChooser(shareIntent, 
         getString(R.string.shareSearch)));   
   }

   // deletes a search after the user confirms the delete operation
   private void deleteSearch(final String tag)
   {
      // create a new AlertDialog
      AlertDialog.Builder confirmBuilder = new AlertDialog.Builder(this);
      
      // set the AlertDialog's message
      confirmBuilder.setMessage(
         getString(R.string.confirmMessage, tag));

      // set the AlertDialog's negative Button
      confirmBuilder.setNegativeButton( getString(R.string.cancel), 
         new DialogInterface.OnClickListener() 
         {
            // called when "Cancel" Button is clicked
            public void onClick(DialogInterface dialog, int id) 
            {
               dialog.cancel(); // dismiss dialog
            } 
         } 
      ); // end call to setNegativeButton
      
      // set the AlertDialog's positive Button
      confirmBuilder.setPositiveButton(getString(R.string.delete), 
         new DialogInterface.OnClickListener() 
         {
            // called when "Cancel" Button is clicked
            public void onClick(DialogInterface dialog, int id) 
            {
               tags.remove(tag); // remove tag from tags
               
               // get SharedPreferences.Editor to remove saved search
               SharedPreferences.Editor preferencesEditor = 
                  savedSearches.edit();                   
               preferencesEditor.remove(tag); // remove search
               preferencesEditor.apply(); // saves the changes

               // rebind tags ArrayList to ListView to show updated list
               adapter.notifyDataSetChanged();                    
            }
         } // end OnClickListener
      ); // end call to setPositiveButton

      confirmBuilder.create().show(); // display AlertDialog    
   } // end method deleteSearch
} // end class MainActivity


/**************************************************************************
 * (C) Copyright 1992-2014 by Deitel & Associates, Inc. and               *
 * Pearson Education, Inc. All Rights Reserved.                           *
 *                                                                        *
 * DISCLAIMER: The authors and publisher of this book have used their     *
 * best efforts in preparing the book. These efforts include the          *
 * development, research, and testing of the theories and programs        *
 * to determine their effectiveness. The authors and publisher make       *
 * no warranty of any kind, expressed or implied, with regard to these    *
 * programs or to the documentation contained in these books. The authors *
 * and publisher shall not be liable in any event for incidental or       *
 * consequential damages in connection with, or arising out of, the       *
 * furnishing, performance, or use of these programs.                     *
 **************************************************************************/