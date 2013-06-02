package com.bishal.android.taskmanager;

import com.bishal.android.taskmanager.tasks.Task;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class AddTaskActivity extends TaskManagerActivity {
	
	protected static final int REQUEST_CHOOSE_ADDRESS = 1;
	
	private EditText taskNameEditText;
	private ImageButton addButton;
	private ImageButton cancelButton;
	private boolean changesPending;
	private AlertDialog unsavedChangesDialog;
	private ImageButton addLocationButton;
	private ImageButton addTimeButton;
	private Address address;
	private TextView addressText;
	final Context context=this;
	String newString;
	
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_task);
         setUpViews();
         if (savedInstanceState == null) {
             Bundle extras = getIntent().getExtras();
             if(extras == null) {
                 newString= null;
             } else {
            	
                 newString= extras.getString("feedback");
                 taskNameEditText.setText(newString);
             }
         } else {
             newString= (String) savedInstanceState.getSerializable("feedback");
             
             taskNameEditText.setText(newString);
         }
     }
	
	@Override
	protected void onResume() {
		super.onResume();
		
		if (null == address) {
			addLocationButton.setVisibility(View.VISIBLE);
			addressText.setVisibility(View.GONE);
		} else {
			addLocationButton.setVisibility(View.GONE);
			addressText.setVisibility(View.VISIBLE);
			addressText.setText(address.getAddressLine(0));
		}
	}


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (REQUEST_CHOOSE_ADDRESS == requestCode && RESULT_OK == resultCode) {
			address = data.getParcelableExtra(AddLocationMapActivity.ADDRESS_RESULT);
		} else {
			super.onActivityResult(requestCode, resultCode, data);
		}
	}

	protected void addTask() {
		String	 taskName= taskNameEditText.getText().toString();
		Task t = new Task(taskName);
		t.setAddress(address);
		getStuffApplication().addTask(t);
		finish();
		Intent i=new Intent(AddTaskActivity.this,ViewTasksActivity.class);
		startActivity(i);
	}

		protected void cancel() {
		if (changesPending) {
			unsavedChangesDialog = new AlertDialog.Builder(this)
				.setTitle(R.string.unsaved_changes_title)
				.setMessage(R.string.unsaved_changes_message)
				.setPositiveButton(R.string.add_task, new AlertDialog.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						addTask();
					}
				})
				.setNeutralButton(R.string.discard, new AlertDialog.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						finish();
					}
				})
				.setNegativeButton(android.R.string.cancel, new AlertDialog.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						unsavedChangesDialog.cancel();
					}
				})
				.create();
			unsavedChangesDialog.show();
		} else {
			finish();
		}
	}

	private void setUpViews() {
		taskNameEditText = (EditText)findViewById(R.id.task_name);
		addButton = (ImageButton)findViewById(R.id.add_button);
		addLocationButton = (ImageButton)findViewById(R.id.add_location_button);
		addTimeButton = (ImageButton)findViewById(R.id.add_time_button);
		addressText = (TextView)findViewById(R.id.address_text);
		cancelButton = (ImageButton)findViewById(R.id.cancel_button);
		
		
		addButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				addTask();
			}
		});
		
		addLocationButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(AddTaskActivity.this, AddLocationMapActivity.class);
				startActivityForResult(intent, REQUEST_CHOOSE_ADDRESS);
			}
		});
		
		addTimeButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
			    Intent intent = new Intent(AddTaskActivity.this, SpecifyTime.class);
			    String here=taskNameEditText.getText().toString();
			    intent.putExtra("task",here);
			    startActivity(intent);
			    }
			});
		
		cancelButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				cancel();
			}
		});
		taskNameEditText.addTextChangedListener(new TextWatcher() {
			public void onTextChanged(CharSequence s, int start, int before, int count) {
					changesPending = true;
				}
			public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
			public void afterTextChanged(Editable s) { }
		});
	}

}
