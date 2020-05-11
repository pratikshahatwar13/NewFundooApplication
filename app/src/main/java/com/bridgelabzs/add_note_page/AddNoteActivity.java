package com.bridgelabzs.add_note_page;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bridgelabzs.add_note_page.model.Note;
import com.bridgelabzs.add_note_page.notification.NotificationJobService;
import com.bridgelabzs.add_note_page.view_model.NoteViewModel;
import com.bridgelabzs.common.DatePickerFragment;
import com.bridgelabzs.common.TimePickerFragment;
import com.bridgelabzs.common.ValidationHelper;
import com.bridgelabzs.dashboard.DashboardActivity;
import com.bridgelabzs.fundooapp.R;
//import com.google.firebase.database.DatabaseReference;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AddNoteActivity extends AppCompatActivity {
    private static final String TAG = "AddNoteActivity";
    private EditText mTextTitle;
    private EditText mTextDescription;
    private ImageButton imgBtnReminder;
    private ImageButton imgBtnArchive;
    private Note noteToEdit;
    private ImageButton imgBtnBackspace;
    private CheckBox cbPinned;
    private EditText mTextDate;
    private EditText mTextTime;
    private TextView mReminder;
    private Calendar calendar = Calendar.getInstance();
    private StringBuilder reminderStringBuilder = new StringBuilder();
    private RadioGroup radioGroup;
    private NotificationManagerCompat notificationManagerCompat;
    private NoteViewModel noteViewModel;
    private List<Note> noteList;
    private String notificationId ;
    private BroadcastReceiver alarmBroadcastReceiver;

//    private DatabaseReference mDatabase;



    ProgressDialog progressDialog;

  //  private AddNoteModel noteToEdit;
    ConstraintLayout rootViewGroup;
    RecyclerView recyclerView ;

    private String remainder = " ";
    private String noteColor = "#ffffff";
    private boolean isEditMode = false;
    private boolean isArchived = false;
    private boolean isPinned = false;
    private boolean isTrashed = false;

    private boolean isColourChanged = false;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        Log.e("Notification","open activity");
//       noteViewModel = new NoteViewModel(this);
         noteViewModel = new NoteViewModel();

    }

    @Override
    protected void onStart() {
        findViews();
        onClickBackspace();
        setButtonListeners();
        super.onStart();
    }

    private void findViews(){
//        mTextTitle = findViewById(R.id.et_title);
//        mTextDescription = findViewById(R.id.et_description);
        handleTvTitle();
        handleTvDescription();
        imgBtnArchive = findViewById(R.id.img_btn_archive);
        imgBtnReminder = findViewById(R.id.img_btn_reminder);
        cbPinned = findViewById(R.id.cb_pin);
        imgBtnArchive = findViewById(R.id.img_btn_archive);
        imgBtnReminder = findViewById(R.id.img_btn_reminder);
        imgBtnBackspace = findViewById(R.id.img_btn_backspace);
        rootViewGroup = findViewById(R.id.root_add_note_activity);
        radioGroup = findViewById(R.id.radio_group);
        cbPinned = findViewById(R.id.cb_pin);
    }

    private void handleTvTitle() {
        mTextTitle = findViewById(R.id.et_title);
        String title = getIntent().getStringExtra("Title");
        if(title != null) {
            mTextTitle.setText(title);
        }
    }

    private void handleTvDescription(){
        mTextDescription = findViewById(R.id.et_description);
        String description = getIntent().getStringExtra("Description");
        if(description != null){
            mTextDescription.setText(description);
        }
    }

    private void setButtonListeners() {
       // setPinnedButtonClickListener();
        setReminderButtonClickListener();
        setArchiveButtonClickListener();
    }

    private void setArchiveButtonClickListener() {
//        imgBtnArchive.setOnCheckedChangeListner(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
//                Toast.makeText(AddNoteActivity.this, "Pinned Button clicked :" + isChecked,
//                        Toast.LENGTH_SHORT).show();
//                if (isEditMode) {
//                    noteToEdit.setArchive(isChecked);
//                    //   apiNoteViewModel.pinUnpinToNote(noteToEdit);
//                }
//            }
//        });
        imgBtnArchive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AddNoteActivity.this, "Archive Button", Toast.LENGTH_SHORT).show();

//                String title = mTextTitle.getText().toString().trim();
//                String description = mTextDescription.getText().toString().trim();
////                 remainder = mReminder.getText().toString().trim() ;
//                if(mReminder != null && mReminder.getText() != null){
//                    remainder  = mReminder.getText().toString().trim();
//                }
//                else{
//                    remainder = " ";
//                }
//
//                String id = getIntent().getStringExtra("Id");
//                Note note = new Note();
//                note.setId(id);
//                note.setTitle(title);
//                note.setDescription(description);
//                note.setColor(noteColor);
//                note.setReminder(remainder);
//
//
//                if (ValidationHelper.validateTitle(title)) {
//                    if (ValidationHelper.validateDescription(description)) {
////                        addArchiveNote(note);
//
//                    } else {
//                        Toast.makeText(AddNoteActivity.this, "Enter Description",
//                                Toast.LENGTH_SHORT).show();
//                    }
//                } else {
//                    Toast.makeText(AddNoteActivity.this, "Enter Title",
//                            Toast.LENGTH_SHORT).show();
//                }
            }
        });
    }

//    private void setPinnedButtonClickListener() {
//        cbPinned.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
//                Toast.makeText(AddNoteActivity.this, "Pinned Button clicked :" + isChecked,
//                        Toast.LENGTH_SHORT).show();
//                if (isEditMode) {
//                    noteToEdit.setIsPinned(isChecked);
//                 //   apiNoteViewModel.pinUnpinToNote(noteToEdit);
//                }
//            }
//        });
//
//    }

    private void setReminderButtonClickListener() {
        imgBtnReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AddNoteActivity.this, "Reminder button clicked",
                        Toast.LENGTH_SHORT).show();
                showReminderDialogBox();
            }
        });

    }

    private void showReminderDialogBox() {
        final Dialog dialog = new Dialog(this);
        View dialogView = LayoutInflater.from(getApplicationContext())
                .inflate(R.layout.dialog_box_date_time_picker, null);
        dialog.setContentView(dialogView);
        dialog.setTitle("Date and Time Picker");
        dialog.show();
        setReminder(dialogView, dialog);
    }

    private void setReminder(View dialogView, Dialog dialog) {
        setDateTextViewClickListener(dialogView);
        setTimeTextViewClickListener(dialogView);
        setCancelReminderButtonClickListener(dialog);
        setSaveReminderButtonClickedListener(dialog);
    }

    private void setCancelReminderButtonClickListener(final Dialog dialog) {

        Button btnCancel = dialog.findViewById(R.id.btn_cancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AddNoteActivity.this, "Cancel Button Clicked", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
    }

    private void setSaveReminderButtonClickedListener(final Dialog dialog) {
        Button btnSave = dialog.findViewById(R.id.btn_save);
        mReminder = findViewById(R.id.remainder);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                String reminderDate = mTextDate.getText().toString();
                String reminderTime = mTextTime.getText().toString();
                String reminder = reminderDate + " " + reminderTime;
                mReminder.setText(reminder);
                scheduleJob(getApplicationContext(),reminder);
                dialog.dismiss();
            }
        });
    }

    private void setDateTextViewClickListener(View dialogView) {
        mTextDate = dialogView.findViewById(R.id.et_date);
        mTextDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment datePickerFragment = new DatePickerFragment(
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month,
                                                  int day) {
                                String reminderDate =  day + "/" + (month + 1) + "/" + year;
                                mTextDate.setText(reminderDate);
                                calendar.set(Calendar.DAY_OF_MONTH, day);
                                calendar.set(Calendar.MONTH, month);
                                calendar.set(Calendar.YEAR, year);
                                setDateTimeString( "yyyy-MM-dd", calendar.getTime());
//                                mTextDate.setText(calendar.getTime().toString());
                            }
                        });
                datePickerFragment.show(getSupportFragmentManager(), "date picker");
            }
        });
    }

    private void setTimeTextViewClickListener(View dialogView) {
        mTextTime = dialogView.findViewById(R.id.et_time);
        mTextTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment timePickerFragment = new TimePickerFragment(
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker timePicker, int hour, int min) {
                                String time = hour + ":" + min;
                                mTextTime.setText(time);
//                              to notify alarm
                                calendar.set(Calendar.HOUR_OF_DAY, hour);
                                calendar.set(Calendar.MINUTE, min);
                                calendar.set(Calendar.SECOND, 0);
                                setDateTimeString( "'T'HH:mm:ss.SSS'Z'", calendar.getTime());
//                                mTextTime.setText(calendar.getTime().toString());
                            }
                        });
                timePickerFragment.show(getSupportFragmentManager(), "time picker");
            }
        });
    }

    private void setDateTimeString(String pattern, Date date) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, Locale.ENGLISH);
        reminderStringBuilder.append(simpleDateFormat.format(date));

        Log.e(TAG, "setDateTimeString: " + reminderStringBuilder.toString() );
    }

    public void onClickBackspace(){
         imgBtnBackspace.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 String title = mTextTitle.getText().toString().trim();
                 String description = mTextDescription.getText().toString().trim();
//                 remainder = mReminder.getText().toString().trim() ;
                 if(mReminder != null && mReminder.getText() != null){
                     remainder  = mReminder.getText().toString().trim();
                 }
                 else{
                     remainder = " ";
                 }


                 String id = getIntent().getStringExtra("Id");
                 Note note = new Note(id,title,description,noteColor,remainder);
//                 Note note = new Note();
//                 note.setId(id);
//                 note.setTitle(title);
//                 note.setDescription(description);
//                 note.setColor(noteColor);
//                 note.setReminder(remainder);


                 if (ValidationHelper.validateTitle(title)) {
                     if (ValidationHelper.validateDescription(description)) {
                         if (getIntent().getStringExtra("Title") != null || getIntent().getStringExtra("Description") != null) {
                             updateNoteToDb(note);

                         } else{
                             addNoteToDb(note);
                         }
//                         else if(title != null && description != null){
//                             addNoteToDb(note);
//                         }

                     } else {
                         Toast.makeText(AddNoteActivity.this, "Enter Description",
                                 Toast.LENGTH_SHORT).show();
                     }
                 } else {
                     Toast.makeText(AddNoteActivity.this, "Enter Title",
                             Toast.LENGTH_SHORT).show();
                 }

             }
         });
    }
    protected void addNoteToDb(Note note) {
        boolean isNoteAdd = noteViewModel.addNote(note);
        if (isNoteAdd) {
            Toast.makeText(AddNoteActivity.this, "Note Successfully saved to db",
                    Toast.LENGTH_SHORT).show();
            Intent data = new Intent(AddNoteActivity.this, DashboardActivity.class);
            startActivity(data);
            finish();
        }
    }

//    protected void addArchiveNote(Note note){
//        boolean isNoteAdd = noteViewModel.addArchiveNote(note);
//        if (isNoteAdd) {
//            Toast.makeText(AddNoteActivity.this, "Note Successfully saved to db",
//                    Toast.LENGTH_SHORT).show();
//            Intent data = new Intent(AddNoteActivity.this, DashboardActivity.class);
//            startActivity(data);
//            finish();
//        }
//    }

    protected void updateNoteToDb(Note note){
        boolean isUpdateNote = noteViewModel.updateNote(note);
        if(isUpdateNote){
            Toast.makeText(AddNoteActivity.this, "Note Successfully updated to db",
                    Toast.LENGTH_SHORT).show();
            Intent data = new Intent(AddNoteActivity.this, DashboardActivity.class);
            startActivity(data);
            finish();
        }
    }

    private void setUpEditFields() {
        mTextTitle.setText(noteToEdit.getTitle());
        mTextDescription.setText(noteToEdit.getDescription());
        setColorRdButton(noteToEdit);
    }

    private void setColorRdButton(Note noteToEdit) {
        String noteColor = noteToEdit.getColor();
        String defaultColor = getString(R.string.none_hexCode);
        String whiteColor = getString(R.string.white_hexCode);
        String greenColor = getString(R.string.green_hexCode);
        String yellowColor = getString(R.string.yellow_hexCode);
        String pinkColor = getString(R.string.pink_hexCode);
        String silverColor = getString(R.string.silver_hexCode);
        String oliveColor = getString(R.string.olive_hexCode);
        String skyBlueColor = getString(R.string.skyBlue_hexCode);
        String purpleColor = getString(R.string.purple_hexCode);

        checkColorRadioBtn(defaultColor, noteColor, R.id.radio_none, R.color.colorDefault);
        checkColorRadioBtn(whiteColor, noteColor, R.id.radio_white, R.color.white);
        checkColorRadioBtn(greenColor, noteColor, R.id.radio_green, R.color.yellowGreen);
        checkColorRadioBtn(yellowColor, noteColor, R.id.radio_yellow, R.color.yellow);
        checkColorRadioBtn(pinkColor, noteColor, R.id.radio_pink, R.color.pink);
        checkColorRadioBtn(silverColor, noteColor, R.id.radio_silver, R.color.silver);
        checkColorRadioBtn(oliveColor, noteColor, R.id.radio_olive, R.color.olive);
        checkColorRadioBtn(skyBlueColor, noteColor, R.id.radio_skyBlue, R.color.skyBlue);
        checkColorRadioBtn(purpleColor, noteColor, R.id.radio_purple, R.color.purple);
    }

    private void checkColorRadioBtn(String defaultColor, final String noteColor, int viewResId,
                                    final int colorResId) {
        if (defaultColor.equals(noteColor)) {

            radioGroup.check(viewResId);
            RadioButton rdBtn = findViewById(viewResId);
            onRadioButtonClicked(rdBtn);
        }
    }

    public void onRadioButtonClicked(View radioButtonView) {
        boolean checked = ((RadioButton) radioButtonView).isChecked();
        switch (radioButtonView.getId()) {
            case R.id.radio_none:
                if (checked) {
                    rootViewGroup.setBackgroundResource(R.color.white);
                    noteColor = "#FFFFFF";
                    isColourChanged = true;
                }
                break;
            case R.id.radio_white:
                if (checked) {
                    rootViewGroup.setBackgroundResource(R.color.white);
                    noteColor = "#FFFFFF";
                    isColourChanged = true;
                }
                break;
            case R.id.radio_green:
                if (checked) {
                    rootViewGroup.setBackgroundResource(R.color.yellowGreen);
                    noteColor = "#9ACD32";
                    isColourChanged = true;
                }
                break;
            case R.id.radio_olive:
                if (checked) {
                    rootViewGroup.setBackgroundResource(R.color.olive);
                    noteColor = "#808000";
                    isColourChanged = true;
                }
                break;
            case R.id.radio_pink:
                if (checked) {
                    rootViewGroup.setBackgroundResource(R.color.pink);
                    noteColor = "#FFC0CB";
                    isColourChanged = true;
                }
                break;
            case R.id.radio_purple:
                if (checked) {
                    rootViewGroup.setBackgroundResource(R.color.purple);
                    noteColor = "#800080";
                    isColourChanged = true;
                }
                break;
            case R.id.radio_skyBlue:
                if (checked) {
                    rootViewGroup.setBackgroundResource(R.color.skyBlue);
                    noteColor = "#87CEEB";
                    isColourChanged = true;
                }
                break;
            case R.id.radio_silver:
                if (checked) {
                    rootViewGroup.setBackgroundResource(R.color.silver);
                    noteColor = "#C0C0C0";
                    isColourChanged = true;
                }
                break;
            case R.id.radio_yellow:
                if (checked) {
                    rootViewGroup.setBackgroundResource(R.color.yellow);
                    noteColor = "#FFFF00";
                    Log.e(TAG, "YELLOW ADDED");
                    isColourChanged = true;
                }
                break;
            default:
                noteColor = "#FFFFFF";
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public static void scheduleJob(Context context, String futureDate) {
        ComponentName serviceComponent = new ComponentName(context, NotificationJobService.class);
        JobInfo.Builder builder = new JobInfo.Builder(0, serviceComponent);

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date currentDate = new Date();
        long diff = 0;
        try {
            Date futureDate1 = formatter.parse(futureDate);
            diff = futureDate1.getTime() - currentDate.getTime();

        } catch (ParseException e) {
            e.printStackTrace();
        }

        builder.setMinimumLatency(diff); // wait at least
        builder.setOverrideDeadline(diff+5000); // maximum delay
        JobScheduler jobScheduler = context.getSystemService(JobScheduler.class);
        jobScheduler.schedule(builder.build());
    }
}
